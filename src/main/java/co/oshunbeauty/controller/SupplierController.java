package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Supplier;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.SupplierService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/suppliers")
@Slf4j
public class SupplierController {
	
	private ValidationsService validationsService;
	private SupplierService supplierService;
	
	@Autowired
	public SupplierController(ValidationsService validationsService, SupplierService supplierService) {
		this.validationsService = validationsService;
		this.supplierService = supplierService;
	}
	
	@GetMapping("/")
	public List<Supplier> getAllSuppliers() {
		return supplierService.getAllSuppliers();
	}
	
	@GetMapping("/{id}")
	public Supplier getSupplierById(@PathVariable final Long id) {
		Optional<Supplier> supplierFound = supplierService.getSupplierById(id);
		
		if(supplierFound.isEmpty()) {
			log.error("The supplier with id {} was not found.", id);
			throw new ResourceNotFoundException(getMessageForSupplierNotFoundException(id));
		}
		
		return supplierFound.get();
	}
	
	@GetMapping("/names")
	public List<Supplier> getSuppliersByName(@RequestParam final String name) {
		return supplierService.getSuppliersByName(name);
	}
	
	@PostMapping
	public Supplier saveSupplier(@RequestBody final Supplier supplier) {
		validationsService.isSupplierValidToSave(supplier);
		
		log.info("Saving new supplier with name {} by the user {}", supplier.getName(), "oshun");
		return supplierService.saveSupplier(supplier, "oshun");
	}
	
	@PutMapping("/{id}")
	public Supplier updateSupplier(@PathVariable final Long id, @RequestBody final Supplier supplier) {
		validationsService.isSupplierValidToUpdate(supplier);
		Optional<Supplier> currentSupplierFound = supplierService.getSupplierById(id);
		validateSuppliersAreEqualsById(supplier, currentSupplierFound);
		
		log.info("Updating the supplier with name {} by the user {}", supplier.getName(), "oshun");
		return supplierService.updateSupplier(currentSupplierFound.get(), supplier, "oshun");
	}
	
	@DeleteMapping
	public void deleteSupplier(@PathVariable Long id) {
		Optional<Supplier> currentSupplierFound = supplierService.getSupplierById(id);
		if(currentSupplierFound.isEmpty()) {
			log.error("The supplier with id {} was not found.", id);
			throw new BadRequestException(getMessageForSupplierNotFoundException(id));
		}
		
		log.info("Deleting the supplier with name {} by the user {}", currentSupplierFound.get().getName(), "oshun");
		supplierService.deleteSupplier(currentSupplierFound.get());
	}
	
	private void validateSuppliersAreEqualsById(Supplier supplier, Optional<Supplier> currentSupplierFound) {
		if(currentSupplierFound.isEmpty() || currentSupplierFound.get().getSupplierId() != supplier.getSupplierId() ) {
			log.error("When trying to update supplier with id {}, the supplier sent had another id",
					supplier.getSupplierId());
			throw new BadRequestException(getErrorMessageSuppliersAreNotSame(supplier));
		}
	}
	
	private String getErrorMessageSuppliersAreNotSame(Supplier supplier) {
		return String.format("El proveedor con id %s no fue encontrado o no corresponde al " +
				"ingresado", supplier.getSupplierId());
	}
	
	private String getMessageForSupplierNotFoundException(Long id) {
		return String.format("El proveedor id %s no fue encontrado", id);
	}
}
