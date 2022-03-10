package co.oshunbeauty.validation;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.exception.BadRequestException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static co.oshunbeauty.resources.EntitiesMocks.getBrand;
import static co.oshunbeauty.resources.EntitiesMocks.getCategory;
import static co.oshunbeauty.resources.EntitiesMocks.getCustomer;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ValidationsServiceTest {
	
	@Mock
	private Validator validator = Mockito.mock(Validator.class);
	
	private ValidationsService validationsService = new ValidationsService(validator);
	
	@Test
	public void testThatValidateIsCategoryValidToSave() {
		// GIVEN
		Category category = getCategory();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCategoryValidToSave(category);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationCategoryToSaveDueToConstrains() {
		// GIVEN
		Category category = getCategory();
		ConstraintViolation<Object> categoryConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> categoryConstrains = Set.of(categoryConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(categoryConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(categoryConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(categoryConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToSave(category));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatValidateIsCategoryValidToUpdate() {
		// GIVEN
		Category category = getCategory();
		category.setCategoryId(1L);
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCategoryValidToUpdate(category);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationCategoryToUpdateDueToConstrains() {
		// GIVEN
		Category category = getCategory();
		category.setCategoryId(1L);
		ConstraintViolation<Object> categoryConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> categoryConstrains = Set.of(categoryConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(categoryConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(categoryConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(categoryConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToUpdate(category));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatValidateIsBrandValidToSave() {
		// GIVEN
		Brand brand = getBrand();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isBrandValidToSave(brand);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationBrandToSaveDueToConstrains() {
		// GIVEN
		Brand brand = getBrand();
		ConstraintViolation<Object> brandConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> brandConstrains = Set.of(brandConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(brandConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(brandConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(brandConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isBrandValidToSave(brand));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatValidateIsBrandValidToUpdate() {
		// GIVEN
		Brand brand = getBrand();
		brand.setBrandId(1L);
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isBrandValidToUpdate(brand);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationBrandToUpdateDueToConstrains() {
		// GIVEN
		Brand brand = getBrand();
		brand.setBrandId(1L);
		ConstraintViolation<Object> brandConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> brandConstrains = Set.of(brandConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(brandConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(brandConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(brandConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isBrandValidToUpdate(brand));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	//-------------------- Customers
	@Test
	public void testThatValidateIsCustomerValidToSave() {
		// GIVEN
		Customer customer = getCustomer();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCustomerValidToSave(customer);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationCustomerToSaveDueToConstrains() {
		// GIVEN
		Customer customer = getCustomer();
		ConstraintViolation<Object> customerConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> customerConstrains = Set.of(customerConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(customerConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(customerConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(customerConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCustomerValidToSave(customer));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatValidateIsCustomerValidToUpdate() {
		// GIVEN
		Customer customer = getCustomer();
		customer.setIdentification("1017217617");
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCustomerValidToUpdate(customer);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void testThatFailsValidationCustomerToUpdateDueToConstrains() {
		// GIVEN
		Customer customer = getCustomer();
		customer.setIdentification("1017217617");
		ConstraintViolation<Object> customerConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> customerConstrains = Set.of(customerConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(customerConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(customerConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(customerConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCustomerValidToUpdate(customer));
		// THEN
		verify(validator, times(1)).validate(any());
	}
}
