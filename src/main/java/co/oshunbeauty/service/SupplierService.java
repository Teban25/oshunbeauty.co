package co.oshunbeauty.service;

import co.oshunbeauty.entity.Supplier;
import co.oshunbeauty.repository.SupplierRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;
import static co.oshunbeauty.constants.Constants.ServicesConstants.IGNORED_STANDARD_FIELDS;

@Service
public class SupplierService {
	
	private Set<String> ignoredSupplierFields = new HashSet<>(Arrays.asList("supplierId"));
	private SupplierRepository supplierRepository;
	
	@Autowired
	public SupplierService(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
		ignoredSupplierFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public List<Supplier> getAllSuppliers() {
		return supplierRepository.findAll();
	}
	
	public Optional<Supplier> getSupplierById(Long id) {
		return supplierRepository.findById(id);
	}
	
	public List<Supplier> getSuppliersByName(String name) {
		return supplierRepository.findSuppliersByName(name);
	}
	
	public Supplier saveSupplier(Supplier supplier, String user) {
		supplier.setCreationDate(ZonedDateTime.now(ZONE_ID));
		supplier.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		supplier.setCreationUser(user);
		supplier.setLastModifiedUser(user);
		
		return supplierRepository.save(supplier);
	}
	
	public Supplier updateSupplier(Supplier currentSupplier, Supplier supplierSent, String user) {
		BeanUtils.copyProperties(supplierSent, currentSupplier, ignoredSupplierFields.stream().toArray(String[]::new));
		
		currentSupplier.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentSupplier.setLastModifiedUser(user);
		
		return supplierRepository.save(currentSupplier);
	}
	
	public void deleteSupplier(Supplier supplier) {
		supplierRepository.delete(supplier);
	}
}
