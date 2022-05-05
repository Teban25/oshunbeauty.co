package co.oshunbeauty.controller;

import co.oshunbeauty.entity.SaleOrder;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.BusinessSaleOrderException;
import co.oshunbeauty.service.SaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/sales")
@Slf4j
public class SaleOrderController {
	
	private SaleOrderService saleOrderService;
	
	@Autowired
	public SaleOrderController(SaleOrderService saleOrderService) {
		this.saleOrderService = saleOrderService;
	}
	
	@PostMapping
	public ResponseEntity<SaleOrder> registerSaleOrder(@RequestBody SaleOrder saleOrder) {
		ResponseEntity responseSaleOrder;
		try {
			SaleOrder saleOrderAdded = saleOrderService.registerSaleOrder(saleOrder);
			responseSaleOrder = ResponseEntity.status(HttpStatus.CREATED).body(saleOrderAdded);
		} catch(BusinessSaleOrderException e) {
			log.error("Error creating the sale order: {}", e.getMessage(), e);
			throw new BadRequestException("Error creando la venta: " + e.getMessage());
		}
		
		return responseSaleOrder;
	}
	
	@GetMapping("current-status")
	public ResponseEntity<Integer> getSalesOfCurrentDay() {
		Integer salesOfCurrentDay = saleOrderService.getSalesOfTheCurrentDay();
		return ResponseEntity.status(HttpStatus.OK).body(salesOfCurrentDay);
	}
}
