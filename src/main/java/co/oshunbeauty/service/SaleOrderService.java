package co.oshunbeauty.service;

import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.entity.Product;
import co.oshunbeauty.entity.PurchaseOrderDetail;
import co.oshunbeauty.entity.SaleOrder;
import co.oshunbeauty.exception.BusinessSaleOrderException;
import co.oshunbeauty.repository.PurchaseOrderDetailRepository;
import co.oshunbeauty.repository.SaleOrderRepository;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;

@Service
public class SaleOrderService {
	
	private SaleOrderRepository saleOrderRepository;
	private ProductService productService;
	private PaymentService paymentService;
	private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
	
	@Autowired
	public SaleOrderService(SaleOrderRepository saleOrderRepository, ProductService productService,
	                        PaymentService paymentService, PurchaseOrderDetailRepository purchaseOrderDetailRepository) {
		this.saleOrderRepository = saleOrderRepository;
		this.productService = productService;
		this.paymentService = paymentService;
		this.purchaseOrderDetailRepository = purchaseOrderDetailRepository;
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public SaleOrder registerSaleOrder(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		validateSaleOrderToAdd(saleOrderToRegister);
		SaleOrder saleOrderAdded = saveSaleOrder(saleOrderToRegister);
		updateActivePurchaseOrderForInventory(saleOrderAdded);
		return saleOrderAdded;
	}
	
	public Integer getSalesOfTheCurrentDay() {
		return saleOrderRepository.getSalesOfTheDay(LocalDate.now(ZONE_ID));
	}
	
	private void updateActivePurchaseOrderForInventory(SaleOrder saleOrderAdded) {
		CompletableFuture.runAsync(() -> saleOrderAdded.getSaleOrderDetails().forEach(orderDetail -> {
			Product currentProduct = orderDetail.getProduct();
			Optional<PurchaseOrderDetail> activePurchaseOrderDetail =
					purchaseOrderDetailRepository.findOrderByProductId(currentProduct.getProductId());
			
			activePurchaseOrderDetail.ifPresent(activePurchase -> {
				activePurchase.setQuantitySold(activePurchase.getQuantitySold() + orderDetail.getQuantity());
				if(activePurchase.getQuantitySold() == activePurchase.getQuantity()) {
					activePurchase.setActivePrice(false);
					Optional<PurchaseOrderDetail> nextPurchaseOrderDetailToActive =
							purchaseOrderDetailRepository
									.findNextOrderPurchaseDetailToActive(orderDetail.getProduct().getProductId());
					
					nextPurchaseOrderDetailToActive.ifPresent(nextActivePurchase -> {
						nextActivePurchase.setActivePrice(true);
						purchaseOrderDetailRepository.save(nextActivePurchase);
						Product productToUpdatePrice = activePurchase.getProduct();
						
						if(nextActivePurchase.getUnitSellPrice() != productToUpdatePrice.getCurrentPrice()) {
							productToUpdatePrice.setCurrentPrice(nextActivePurchase.getUnitSellPrice());
						}
						productToUpdatePrice.setCurrentAmount(nextActivePurchase.getQuantity());
					});
				}
				purchaseOrderDetailRepository.save(activePurchase);
			});
		}));
	}
	
	private SaleOrder saveSaleOrder(SaleOrder saleOrderToRegister) {
		String nextSaleOrderNumber = saleOrderRepository.getNextSaleOrderNumber();
		ZonedDateTime saleOrderDate = ZonedDateTime.now(ZONE_ID);
		String creationUser = "oshun";
		
		saleOrderToRegister.setSaleOrderNumber(nextSaleOrderNumber);
		saleOrderToRegister.setSaleOrderDate(saleOrderDate);
		saleOrderToRegister.setLastModifiedDate(saleOrderDate);
		saleOrderToRegister.setCreationUser(creationUser);
		saleOrderToRegister.setLastModifiedUser(creationUser);
		
		updateStockForProductsInOrder(saleOrderToRegister, saleOrderDate, creationUser);
		return saleOrderRepository.save(saleOrderToRegister);
	}
	
	private void updateStockForProductsInOrder(SaleOrder saleOrderToRegister,
	                                           ZonedDateTime saleOrderDate, String creationUser) {
		
		saleOrderToRegister.getSaleOrderDetails().forEach(orderDetail -> {
			
			Product currentProduct = orderDetail.getProduct();
			currentProduct.setCurrentAmount(currentProduct.getCurrentAmount() - orderDetail.getQuantity());
			productService.updateProduct(currentProduct, creationUser);
			orderDetail.setSaleOrderDetailDate(saleOrderDate);
			orderDetail.setLastModifiedDate(saleOrderDate);
			orderDetail.setCreationUser(creationUser);
			orderDetail.setLastModifiedUser(creationUser);
		});
	}
	
	private void validateSaleOrderToAdd(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		validateEmptyOrders(saleOrderToRegister);
		validateTypeOfPayment(saleOrderToRegister);
		validateStockInSaleOrder(saleOrderToRegister);
		validatePricesInSaleOrder(saleOrderToRegister);
	}
	
	private void validateEmptyOrders(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		if (saleOrderToRegister == null) {
			throw new BusinessSaleOrderException("La orden de venta no puede estar vacía");
		}
		
		if(saleOrderToRegister.getSaleOrderDetails() == null || saleOrderToRegister.getSaleOrderDetails().isEmpty()) {
			throw new BusinessSaleOrderException("La orden no contiene productos para vender");
		}
	}
	
	private void validateTypeOfPayment(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		if(saleOrderToRegister.getPayment() == null || saleOrderToRegister.getPayment().getPaymentId() == null) {
			throw new BusinessSaleOrderException("Se debe seleccionar un tipo de pago");
		}
		
		Optional<Payment> paymentById = paymentService.getPaymentById(saleOrderToRegister.getPayment().getPaymentId());
		if(paymentById.isEmpty()) {
			throw new BusinessSaleOrderException("El tipo de pago seleccionado no existe");
		}
	}
	
	private void validatePricesInSaleOrder(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		String businessErrorMessages = "";
		if(isCustomerPaymentHigherThanTotalSaleOrderPrice(saleOrderToRegister)) {
			businessErrorMessages += "* El valor pagado por el usuario es menor que el total de la compra *";
		}
		if(isCustomerPaymentAndBackEqualsToTotalSaleOrderPrice(saleOrderToRegister)) {
			businessErrorMessages += "* El valor pagado por el usuario y su devuelta no corresponse al valor total de la compra *";
		}
		if(!businessErrorMessages.isEmpty()) {
			throw new BusinessSaleOrderException(businessErrorMessages);
		}
		validateTotalOrderSalePriceAgainstEachOrderDetail(saleOrderToRegister);
	}
	
	private void validateTotalOrderSalePriceAgainstEachOrderDetail(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		Double totalOrderSalePrice = saleOrderToRegister.getTotalSaleOrderPrice();
		AtomicReference<Double> totalPriceByOrderDetail = new AtomicReference<>(0.0);
		try {
			saleOrderToRegister.getSaleOrderDetails().forEach(order -> {
				if(order.getTotal() != order.getUnitPrice() * order.getQuantity()) {
					throw new RuntimeException("El valor unitario por la cantidad no corresponde al total añadido " +
							"para el producto: " + order.getProduct().getName());
				}
				totalPriceByOrderDetail.updateAndGet(v -> v + order.getTotal());
			});
			
			if(totalOrderSalePrice.doubleValue() != totalPriceByOrderDetail.get().doubleValue()) {
				throw new RuntimeException("El desglose de totales por producto de la orden no corresponde al" +
						" total asignado.");
			}
		} catch(RuntimeException ex) {
			throw new BusinessSaleOrderException(ex.getMessage());
		}
	}
	
	private boolean isCustomerPaymentAndBackEqualsToTotalSaleOrderPrice(SaleOrder saleOrderToRegister) {
		Double customerPayment = saleOrderToRegister.getCustomerPayment() - saleOrderToRegister.getCustomerPaymentBack();
		return customerPayment.doubleValue() != saleOrderToRegister.getTotalSaleOrderPrice().doubleValue();
	}
	
	private boolean isCustomerPaymentHigherThanTotalSaleOrderPrice(SaleOrder saleOrderToRegister) {
		return saleOrderToRegister.getCustomerPayment() < saleOrderToRegister.getTotalSaleOrderPrice();
	}
	
	private void validateStockInSaleOrder(SaleOrder saleOrderToRegister) throws BusinessSaleOrderException {
		try {
			saleOrderToRegister.getSaleOrderDetails().forEach(order -> {
				Optional<Product> currentProductInOrderOptional = productService.getProductById(order.getProduct().getProductId());
				currentProductInOrderOptional.ifPresentOrElse(currentProduct -> {
					order.setProduct(currentProduct);
					Optional<PurchaseOrderDetail> activePurchaseOrderDetail =
							purchaseOrderDetailRepository.findOrderByProductId(currentProduct.getProductId());
					activePurchaseOrderDetail.ifPresentOrElse(activePurchase -> {
						order.setAssociatePurchaseOrderDetail(activePurchase);
					}, ()-> {
						throw new RuntimeException("No hay una orden de compra asociada para el producto, por favor" +
								" verificar");
					});
					
					if(order.getQuantity() > currentProduct.getCurrentAmount()) {
						throw new RuntimeException("la cantidad del producto a vender en la orden no puede superar la cantidad " +
								"en Stock");
					}
				}, () -> {
					throw new RuntimeException("el producto a vender no se encuentra registrado");
				});
			});
		} catch(RuntimeException exception) {
			String businessError = exception.getMessage();
			throw new BusinessSaleOrderException("Por favor verifica, " + businessError);
		}
	}
}
