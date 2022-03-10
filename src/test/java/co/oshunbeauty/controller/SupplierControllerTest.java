package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Supplier;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.SupplierService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getSupplier;
import static co.oshunbeauty.resources.EntitiesMocks.getSuppliers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierControllerTest {
	
	@InjectMocks
	SupplierController supplierController;
	
	@Mock
	SupplierService supplierService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final Long SUPPLIER_ID = 1L;
	private static final String NAME = "j&c";
	private static final String USER_TEST = "testOshun";
	
	@Test
	public void testThatGetSupplierById() {
		// GIVEN
		Supplier supplierToFind = getSupplier();
		supplierToFind.setSupplierId(SUPPLIER_ID);
		// WHEN
		when(supplierService.getSupplierById(any(Long.class))).thenReturn(Optional.of(supplierToFind));
		Supplier actualSupplier = supplierController.getSupplierById(SUPPLIER_ID);
		// THEN
		verify(supplierService, times(1)).getSupplierById(any(Long.class));
		assertAll(
				() -> assertNotNull(actualSupplier),
				() -> assertEquals(SUPPLIER_ID, actualSupplier.getSupplierId()),
				() -> assertEquals(NAME, actualSupplier.getName())
		);
	}
	
	@Test
	public void testThatNotFoundGetSupplierById() {
		// GIVEN
		
		// WHEN
		when(supplierService.getSupplierById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> supplierController.getSupplierById(SUPPLIER_ID));
		verify(supplierService, times(1)).getSupplierById(any(Long.class));
	}
	
	@Test
	public void testThatGetAllSuppliers() {
		// GIVEN
		List<Supplier> suppliersToFind = getSuppliers();
		// WHEN
		when(supplierService.getAllSuppliers()).thenReturn(suppliersToFind);
		List<Supplier> actualSuppliers = supplierController.getAllSuppliers();
		// THEN
		verify(supplierService, times(1)).getAllSuppliers();
		assertAll(
				() -> assertNotNull(actualSuppliers),
				() -> assertEquals(4, actualSuppliers.size()),
				() -> assertEquals(NAME, actualSuppliers.get(0).getName())
		);
	}
	
	@Test
	public void testThatGetSupplierByName() {
		// GIVEN
		List<Supplier> suppliersToFind = getSuppliers().stream().filter(i -> i.getName().contains(NAME))
				.collect(Collectors.toList());
		// WHEN
		when(supplierService.getSuppliersByName(any(String.class))).thenReturn(suppliersToFind);
		List<Supplier> actualSuppliers = supplierController.getSuppliersByName(NAME);
		// THEN
		verify(supplierService, times(1)).getSuppliersByName(any(String.class));
		assertAll(
				() -> assertNotNull(actualSuppliers),
				() -> assertEquals(1, actualSuppliers.size()),
				() -> assertEquals(NAME, actualSuppliers.get(0).getName())
		);
	}
	
	@Test
	public void testThatSaveSupplier() {
		// GIVEN
		Supplier supplierToSave = getSupplier();
		Supplier supplierSaved = BeanUtils.instantiateClass(Supplier.class);
		BeanUtils.copyProperties(supplierToSave, supplierSaved);
		supplierSaved.setSupplierId(SUPPLIER_ID);
		// WHEN
		when(supplierService.saveSupplier(any(Supplier.class), any(String.class)))
				.thenReturn(supplierSaved);
		Supplier actualSupplier = supplierController.saveSupplier(supplierToSave);
		// THEN
		verify(validationsService, times(1)).isSupplierValidToSave(any(Supplier.class));
		verify(supplierService, times(1)).saveSupplier(any(Supplier.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualSupplier),
				() -> assertNotNull(actualSupplier.getCreationDate()),
				() -> assertNotNull(actualSupplier.getLastModifiedDate()),
				() -> assertEquals(SUPPLIER_ID, actualSupplier.getSupplierId()),
				() -> assertEquals(NAME, actualSupplier.getName()),
				() -> assertEquals("test", actualSupplier.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingSupplier() {
		// GIVEN
		Supplier supplierToSave = getSupplier();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isSupplierValidToSave(any(Supplier.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> supplierController.saveSupplier(supplierToSave));
		verify(validationsService,
				times(1)).isSupplierValidToSave(any(Supplier.class));
		verifyNoInteractions(supplierService);
	}
	
	@Test
	public void testThatFailsUpdatingSupplierDueToValidations() {
		// GIVEN
		Supplier supplierToSave = getSupplier();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isSupplierValidToUpdate(any(Supplier.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> supplierController.updateSupplier(SUPPLIER_ID, supplierToSave));
		verify(validationsService,
				times(1)).isSupplierValidToUpdate(any(Supplier.class));
		verifyNoInteractions(supplierService);
	}
	
	@Test
	public void testThatFailsUpdatingSupplierDueToWrongIds() {
		// GIVEN
		Supplier supplierToUpdate = getSupplier();
		supplierToUpdate.setSupplierId(3L);
		
		Supplier supplierFound = getSupplier();
		supplierFound.setSupplierId(SUPPLIER_ID);
		// WHEN
		when(supplierService.getSupplierById(any(Long.class))).thenReturn(Optional.of(supplierFound));
		// THEN
		assertThrows(BadRequestException.class, () -> supplierController.updateSupplier(SUPPLIER_ID, supplierToUpdate));
		verify(validationsService, times(1)).isSupplierValidToUpdate(any(Supplier.class));
		verify(supplierService, times(1)).getSupplierById(any(Long.class));
		verifyNoMoreInteractions(supplierService);
	}
	
	@Test
	public void testThatUpdateSupplier() {
		// GIVEN
		Supplier supplierToUpdate = getSupplier();
		supplierToUpdate.setSupplierId(SUPPLIER_ID);
		supplierToUpdate.setName("cosmenales");
		
		Supplier supplierFound = getSupplier();
		supplierFound.setSupplierId(SUPPLIER_ID);
		
		Supplier supplierUpdated = BeanUtils.instantiateClass(Supplier.class);
		BeanUtils.copyProperties(supplierToUpdate, supplierUpdated);
		supplierUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(supplierService.getSupplierById(any(Long.class))).thenReturn(Optional.of(supplierFound));
		when(supplierService.updateSupplier(any(Supplier.class), any(Supplier.class), any(String.class)))
				.thenReturn(supplierUpdated);
		Supplier actualSupplier = supplierController.updateSupplier(SUPPLIER_ID, supplierToUpdate);
		// THEN
		verify(validationsService, times(1)).isSupplierValidToUpdate(any(Supplier.class));
		verify(supplierService, times(1)).getSupplierById(any(Long.class));
		verify(supplierService, times(1))
				.updateSupplier(any(Supplier.class), any(Supplier.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualSupplier),
				() -> assertNotNull(actualSupplier.getCreationDate()),
				() -> assertNotNull(actualSupplier.getLastModifiedDate()),
				() -> assertEquals(SUPPLIER_ID, actualSupplier.getSupplierId()),
				() -> assertEquals("cosmenales", actualSupplier.getName()),
				() -> assertEquals(USER_TEST, actualSupplier.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeleteSupplier() {
		// GIVEN
		Supplier supplierFound = getSupplier();
		supplierFound.setSupplierId(SUPPLIER_ID);
		
		// WHEN
		when(supplierService.getSupplierById(any(Long.class))).thenReturn(Optional.of(supplierFound));
		supplierController.deleteSupplier(SUPPLIER_ID);
		// THEN
		verify(supplierService, times(1)).getSupplierById(any(Long.class));
		verify(supplierService, times(1)).deleteSupplier(any(Supplier.class));
	}
	
	@Test
	public void testThatFailsDeletingSupplier() {
		// GIVEN
		
		// WHEN
		when(supplierService.getSupplierById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> supplierController.deleteSupplier(SUPPLIER_ID));
		verify(supplierService, times(1)).getSupplierById(any(Long.class));
		verifyNoMoreInteractions(supplierService);
	}
}
