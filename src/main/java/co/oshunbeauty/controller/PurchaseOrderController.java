package co.oshunbeauty.controller;

import co.oshunbeauty.entity.PurchaseOrder;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rs/purchases")
@Slf4j
public class PurchaseOrderController {
	
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
		this.purchaseOrderService = purchaseOrderService;
	}
	
	@PostMapping("/excel")
	public ResponseEntity<PurchaseOrder> loadProductsFromPurchaseExcel(@RequestParam("file") MultipartFile excelFile) {
		PurchaseOrder purchaseOrderLoaded;
		try {
			purchaseOrderLoaded = purchaseOrderService.loadPurchaseOrderFromExcel(excelFile, "oshun");
		} catch(Exception ex) {
			String userMessage = "Por favor verificar el archivo excel, hubo error intentando procesarlo: ";
			log.error("There was an error processing the excel file, reason: {}", ex.getMessage(), ex);
			
			if(ex.getCause() != null) {
				userMessage +=  ". mensaje tecnico: " + ex.getCause().getMessage();
			}
			
			throw new BadRequestException(userMessage);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrderLoaded);
	}
}
