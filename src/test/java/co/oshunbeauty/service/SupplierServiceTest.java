package co.oshunbeauty.service;

import co.oshunbeauty.entity.Supplier;
import co.oshunbeauty.repository.SupplierRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getSupplier;
import static co.oshunbeauty.resources.EntitiesMocks.getSuppliers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SupplierServiceTest {
	
	@Mock
	private SupplierRepository supplierRepository = Mockito.mock(SupplierRepository.class);
	
	private SupplierService supplierService = new SupplierService(supplierRepository);
	
	private static final String NAME = "j&c";
	private static final String USER_TEST = "testOshun";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetSupplierByIdNotFound(){
		// Given
		Long supplierId = 1L;
		// When
		Mockito.when(supplierRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Optional<Supplier> supplierFound = supplierService.getSupplierById(supplierId);
		// Then
		verify(supplierRepository, times(1)).findById(any(Long.class));
		assertTrue(supplierFound.isEmpty());
	}
	
	@Test
	public void testGetSupplierById() {
		// Given
		Long supplierId = 1L;
		Supplier supplierToFind = getSupplier();
		supplierToFind.setSupplierId(supplierId);
		// When
		Mockito.when(supplierRepository.findById(any(Long.class))).thenReturn(Optional.of(supplierToFind));
		Optional<Supplier> supplierFound = supplierService.getSupplierById(supplierId);
		// Then
		verify(supplierRepository, times(1)).findById(any(Long.class));
		assertAll(
				() -> assertTrue(supplierFound.isPresent()),
				() -> assertEquals(supplierId, supplierFound.get().getSupplierId()),
				() -> assertEquals(NAME, supplierFound.get().getName())
		);
	}
	
	@Test
	public void testToGetAllSuppliers() {
		// Given
		List<Supplier> suppliers = getSuppliers();
		// When
		Mockito.when(supplierRepository.findAll()).thenReturn(suppliers);
		List<Supplier> currentSuppliers = supplierService.getAllSuppliers();
		// Then
		verify(supplierRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentSuppliers),
				() -> assertEquals(4, currentSuppliers.size()),
				() -> assertEquals(NAME, currentSuppliers.get(0).getName())
		);
	}
	
	@Test
	public void testToGetSuppliersByName() {
		// Given
		List<Supplier> suppliers = getSuppliers().stream().filter(i -> i.getName().contains(NAME))
				.collect(Collectors.toList());
		// When
		Mockito.when(supplierRepository.findSuppliersByName(any(String.class))).thenReturn(suppliers);
		List<Supplier> currentSuppliers = supplierService.getSuppliersByName(NAME);
		// Then
		verify(supplierRepository, times(1)).findSuppliersByName(any(String.class));
		assertAll(
				() -> assertNotNull(currentSuppliers),
				() -> assertEquals(1, currentSuppliers.size()),
				() -> assertEquals(NAME, currentSuppliers.get(0).getName())
		);
	}
	
	@Test
	public void testToUpdateSupplier(){
		// Given
		Supplier supplierToUpdate = getSupplier();
		supplierToUpdate.setSupplierId(1L);
		supplierToUpdate.setName("cosmenales");
		
		Supplier currentSupplier = getSupplier();
		currentSupplier.setSupplierId(1L);
		
		Supplier supplierUpdated = BeanUtils.instantiateClass(Supplier.class);
		BeanUtils.copyProperties(supplierToUpdate, supplierUpdated);
		supplierUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(supplierRepository.save(any(Supplier.class))).thenReturn(supplierUpdated);
		Supplier currentSupplierUpdated = supplierService.updateSupplier(currentSupplier, supplierToUpdate, USER_TEST);
		// Then
		verify(supplierRepository, times(1)).save(any(Supplier.class));
		assertAll(
				() -> assertNotNull(currentSupplierUpdated),
				() -> assertEquals(USER_TEST, currentSupplierUpdated.getLastModifiedUser()),
				() -> assertEquals("cosmenales", currentSupplierUpdated.getName())
		);
	}
	
	@Test
	public void testToSaveSupplier(){
		// Given
		Supplier supplierToSave = getSupplier();
		// When
		Mockito.when(supplierRepository.save(any(Supplier.class))).thenReturn(supplierToSave);
		Supplier currentSupplier = supplierService.saveSupplier(supplierToSave, USER_TEST);
		// Then
		verify(supplierRepository, times(1)).save(any(Supplier.class));
		assertAll(
				() -> assertNotNull(currentSupplier),
				() -> assertEquals(USER_TEST, currentSupplier.getCreationUser()),
				() -> assertEquals(NAME, currentSupplier.getName())
		);
	}
}