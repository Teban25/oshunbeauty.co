package co.oshunbeauty.service;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.entity.Product;
import co.oshunbeauty.entity.PurchaseOrder;
import co.oshunbeauty.entity.PurchaseOrderDetail;
import co.oshunbeauty.entity.Supplier;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.helper.ExcelHelper;
import co.oshunbeauty.repository.PurchaseOrderDetailRepository;
import co.oshunbeauty.repository.PurchaseOrderRepository;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;
import static co.oshunbeauty.constants.Constants.ServicesConstants.ORDER_SHEET;
import static co.oshunbeauty.constants.Constants.ServicesConstants.PRODUCTS_SHEET;

@Service
public class PurchaseOrderService {
	
	private BrandService brandService;
	private CategoryService categoryService;
	private KeywordService keywordService;
	private ExcelHelper excelHelper;
	private ProductService productService;
	private PaymentService paymentService;
	private SupplierService supplierService;
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	public PurchaseOrderService(BrandService brandService, CategoryService categoryService,
	                            KeywordService keywordService, ExcelHelper excelHelper,
	                            ProductService productService, PaymentService paymentService,
	                            SupplierService supplierService, PurchaseOrderRepository purchaseOrderRepository) {
		this.brandService = brandService;
		this.categoryService = categoryService;
		this.keywordService = keywordService;
		this.excelHelper = excelHelper;
		this.productService = productService;
		this.paymentService = paymentService;
		this.supplierService = supplierService;
		this.purchaseOrderRepository = purchaseOrderRepository;
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public PurchaseOrder loadPurchaseOrderFromExcel(MultipartFile file, String user) {
		PurchaseOrder purchaseOrderLoaded;
		try {
			Workbook workbook = excelHelper.getWorkBookFromExcel(file.getInputStream());
			Sheet sheetProducts = workbook.getSheet(PRODUCTS_SHEET);
			Sheet sheetOrders = workbook.getSheet(ORDER_SHEET);
			List<Product> productsFromExcel = getProductsFromExcel(sheetProducts);
			purchaseOrderLoaded = getPurchaseOrderFromExcel(sheetOrders, productsFromExcel, user);
		} catch(IOException ex) {
			throw new BadRequestException("Hubo un error tratando de convertir el archivo.", ex);
		} catch(Exception ex) {
			throw new BadRequestException(ex);
		}
		return purchaseOrderLoaded;
	}
	
	private List<Product> getProductsFromExcel(Sheet sheet) {
		List<Product> productsFromExcel = new LinkedList<>();
		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();
		for(int i = firstRow; i <= lastRow; i++) {
			Row currentRow = sheet.getRow(i);
			productsFromExcel.add(getProductFromRow(currentRow));
		}
		
		return productsFromExcel;
	}
	
	private PurchaseOrder getPurchaseOrderFromExcel(Sheet sheet, List<Product> productsFromExcel, String user) {
		List<PurchaseOrderDetail> purchaseOrderDetails = new LinkedList<>();
		PurchaseOrder purchaseOrderToLoad = new PurchaseOrder();
		int firstRow = sheet.getFirstRowNum() + 1;
		int lastRow = sheet.getLastRowNum();
		int indexProductsFromExcel = 0;
		
		for(int i = firstRow; i <= lastRow; i++) {
			Row currentRow = sheet.getRow(i);
			Product productFromExcel = productsFromExcel.get(indexProductsFromExcel);
			Product productLoaded = loadProductFromExcelToDataBase(productFromExcel, currentRow, user);
			PurchaseOrderDetail purchaseOrderDetail = loadPurchaseDetailFromExcel(productLoaded, currentRow, user);
			purchaseOrderDetails.add(purchaseOrderDetail);
			
			indexProductsFromExcel++;
		}
		
		String supplierName = sheet.getRow(1).getCell(4).getStringCellValue();
		purchaseOrderToLoad.setSupplier(getSupplierResourceToAddPurchaseOrder(supplierName));
		
		String paymentName = sheet.getRow(1).getCell(5).getStringCellValue();
		purchaseOrderToLoad.setPayment(getPaymentResourceToAddPurchaseOrder(paymentName));
		
		purchaseOrderToLoad.setPurchaseOrderNumber(purchaseOrderRepository.getNextPurchaseOrderNumber());
		purchaseOrderToLoad.setPurchaseOrderDetails(purchaseOrderDetails);
		purchaseOrderToLoad.setTotalPurchaseOrderPrice(getTotalPurchaseOrder(purchaseOrderDetails));
		purchaseOrderToLoad.setPurchaseOrderDate(ZonedDateTime.now(ZONE_ID));
		purchaseOrderToLoad.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		purchaseOrderToLoad.setCreationUser(user);
		purchaseOrderToLoad.setLastModifiedUser(user);
		
		return purchaseOrderRepository.save(purchaseOrderToLoad);
	}
	
	private Double getTotalPurchaseOrder(List<PurchaseOrderDetail> purchaseOrderDetails) {
		Double totalPurchaseOrder = 0.0;
		for(PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetails) {
			totalPurchaseOrder += purchaseOrderDetail.getTotal();
		}
		
		return totalPurchaseOrder;
	}
	
	private Payment getPaymentResourceToAddPurchaseOrder(String paymentName) {
		Optional<Payment> possiblePayment = paymentService.getPaymentByNameFromExcel(paymentName);
		return possiblePayment.orElseGet(() ->  new Payment());
	}
	
	private Supplier getSupplierResourceToAddPurchaseOrder(String supplierName) {
		List<Supplier> possibleSuppliers = supplierService.getSuppliersByName(supplierName);
		Supplier supplierToAddPurchaseOrder;
		if(possibleSuppliers.isEmpty()) {
			Supplier supplierToSave = new Supplier();
			supplierToSave.setName(supplierName);
			supplierToAddPurchaseOrder = supplierService.saveSupplier(supplierToSave, "oshun");
		} else {
			supplierToAddPurchaseOrder = possibleSuppliers.get(0);
		}
		
		return supplierToAddPurchaseOrder;
	}
	
	private PurchaseOrderDetail loadPurchaseDetailFromExcel(Product productLoaded, Row currentRow, String user) {
		Integer quantity = Double.valueOf(currentRow.getCell(2).getNumericCellValue()).intValue();
		Double supplierPrice = currentRow.getCell(3).getNumericCellValue();
		
		PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
		purchaseOrderDetail.setProduct(productLoaded);
		purchaseOrderDetail.setQuantity(quantity);
		purchaseOrderDetail.setUnitPrice(supplierPrice);
		purchaseOrderDetail.setTotal(quantity*supplierPrice);
		purchaseOrderDetail.setPurchaseOrderDetailDate(ZonedDateTime.now(ZONE_ID));
		purchaseOrderDetail.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		purchaseOrderDetail.setCreationUser(user);
		purchaseOrderDetail.setLastModifiedUser(user);
		
		return purchaseOrderDetail;
	}
	
	private Product loadProductFromExcelToDataBase(Product productFromExcel, Row currentRow, String user) {
		Optional<Product> productOptFromDataBase = loadProductFromDataBase(productFromExcel);
		Product productSaved;
		
		if(productOptFromDataBase.isPresent()) {
			Product currentProductFromDataBase = productOptFromDataBase.get();
			Integer addAmountToInventory = currentProductFromDataBase.getCurrentAmount() + productFromExcel.getCurrentAmount();
			currentProductFromDataBase.setCurrentAmount(addAmountToInventory);
			
			if(currentProductFromDataBase.getCurrentPrice() != productFromExcel.getCurrentPrice()) {
				currentProductFromDataBase.setCurrentPrice(productFromExcel.getCurrentPrice());
			}
			currentProductFromDataBase.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
			currentProductFromDataBase.setLastModifiedUser(user);
			productSaved = currentProductFromDataBase;
			//productSaved = productService.updateProductFromExcel(currentProductFromDataBase, user);
		} else {
			String categoriesNames = currentRow.getCell(0).getStringCellValue();
			productFromExcel.setCategories(getCategoriesResourcesToAddProduct(categoriesNames));
			
			String keywords = currentRow.getCell(1).getStringCellValue();
			productFromExcel.setKeywords(getKeywordsResourcesToAddProduct(keywords));
			productFromExcel.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
			productFromExcel.setLastModifiedUser(user);
			productFromExcel.setCreationDate(ZonedDateTime.now(ZONE_ID));
			productFromExcel.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
			productFromExcel.setCreationUser(user);
			productFromExcel.setLastModifiedUser(user);
			productSaved = productFromExcel;
			//productSaved = productService.saveProduct(productFromExcel, user);
		}
		
		return productSaved;
	}
	
	private Optional<Product> loadProductFromDataBase(Product productFromExcel) {
		Optional<Product> productOptFromDataBase;
		if(productFromExcel.getBarcode() != null && !productFromExcel.getBarcode().isEmpty()) {
			productOptFromDataBase = productService.
					getProductByBarcode(productFromExcel.getBarcode());
		} else {
			productOptFromDataBase = productService.
					getProductByNameAndBrand(productFromExcel.getName(), productFromExcel.getBrand().getBrandId());
		}
		
		return productOptFromDataBase;
	}
	
	private Product getProductFromRow(Row row) {
		Product product = new Product();
		Iterator<Cell> cells = row.cellIterator();
		while(cells.hasNext()) {
			Cell currentCell = cells.next();
			setFieldOfProductFromCellSheetProducts(currentCell, product);
		}
		
		return product;
	}
	
	private void setFieldOfProductFromCellSheetProducts(Cell cell, Product product) {
		switch(cell.getColumnIndex()) {
			case 0:
				product.setBarcode(cell.getStringCellValue());
				break;
			
			case 1:
				String brandName = cell.getStringCellValue();
				product.setBrand(getBrandResourceToAddProduct(brandName));
				break;
				
			case 2:
				product.setName(cell.getStringCellValue());
				break;
			
			case 3:
				product.setDescription(cell.getStringCellValue());
				break;
			
			case 4:
				Integer amount = Double.valueOf(cell.getNumericCellValue()).intValue();
				product.setCurrentAmount(amount);
				break;
			
			case 5:
				Double currentPrice = cell.getNumericCellValue();
				product.setCurrentPrice(currentPrice);
				break;
			
			default:
				throw new BadRequestException("El archivo excel contiene columnas o celdas no permitidas, " +
						"por favor verifiquelo para volverlo a intentar.");
		}
		
	}
	
	private List<Keyword> getKeywordsResourcesToAddProduct(String keywords) {
		List<Keyword> keywordsToAddProduct = new ArrayList<>();
		Map<String, String> keywordsInExcelCell = getKeywordsFromExcelFile(keywords);
		for(Map.Entry<String, String> keywordInExcelCell : keywordsInExcelCell.entrySet()) {
			Optional<Keyword> keywordOptional = keywordService.getKeywordByKeyAndValue(keywordInExcelCell.getKey(), keywordInExcelCell.getValue());
			if(keywordOptional.isPresent()) {
				keywordsToAddProduct.add(keywordOptional.get());
			} else {
				Keyword keywordToSave = new Keyword();
				keywordToSave.setKey(keywordInExcelCell.getKey());
				keywordToSave.setValue(keywordInExcelCell.getValue());
				keywordsToAddProduct.add(keywordService.saveKeyword(keywordToSave, "oshun"));
			}
		}
		
		return keywordsToAddProduct;
	}
	
	private Map<String,String> getKeywordsFromExcelFile(String keywordsByComma) {
		Map<String, String> keywordsConverted = new HashMap<>();
		if(keywordsByComma != null && !keywordsByComma.isEmpty()) {
			String[] dividedByKeywords = keywordsByComma.split(",");
			for(int i=0; i < dividedByKeywords.length; i++) {
				String[] keyAndValue = dividedByKeywords[i].split(":");
				keywordsConverted.put(keyAndValue[0], keyAndValue[1]);
			}
		}
		
		return keywordsConverted;
	}
	
	private List<Category> getCategoriesResourcesToAddProduct(String categoriesNames) {
		List<Category> categoriesToAddProduct = new ArrayList<>();
		List<String> categoriesNamesConverted = getCategoriesNameFromExcel(categoriesNames);
		for(String categoryToValidate : categoriesNamesConverted) {
			List<Category> categoriesByName = categoryService.getCategoriesByName(categoryToValidate);
			if(categoriesByName.isEmpty()) {
				Category categoryToSave = new Category();
				categoryToSave.setName(categoryToValidate);
				categoriesToAddProduct.add(categoryService.saveCategory(categoryToSave, "oshun"));
			} else {
				categoriesToAddProduct.add(categoriesByName.get(0));
			}
		}
		
		return categoriesToAddProduct;
	}
	
	private List<String> getCategoriesNameFromExcel(String categoriesNamesFromFile) {
		List<String> categoriesForCurrentProduct = new ArrayList<>();
		if(categoriesNamesFromFile != null && !categoriesNamesFromFile.isEmpty()) {
			String[] categoriesDividedByComma = categoriesNamesFromFile.split(",");
			for(int i = 0; i < categoriesDividedByComma.length; i++) {
				categoriesForCurrentProduct.add(categoriesDividedByComma[i]);
			}
		}
		
		return categoriesForCurrentProduct;
	}
	
	private Brand getBrandResourceToAddProduct(String brandName) {
		List<Brand> possibleBrands = brandService.getBrandsByName(brandName);
		Brand brandToAddProduct;
		if(possibleBrands.isEmpty()) {
			Brand brandToSave = new Brand();
			brandToSave.setCompanyName(brandName);
			brandToAddProduct = brandService.saveBrand(brandToSave, "oshun");
		} else {
			brandToAddProduct = possibleBrands.get(0);
		}
		
		return brandToAddProduct;
	}
}
