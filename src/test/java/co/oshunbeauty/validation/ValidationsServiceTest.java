package co.oshunbeauty.validation;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.entity.Supplier;
import co.oshunbeauty.exception.BadRequestException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static co.oshunbeauty.resources.EntitiesMocks.getBrand;
import static co.oshunbeauty.resources.EntitiesMocks.getCategory;
import static co.oshunbeauty.resources.EntitiesMocks.getCustomer;
import static co.oshunbeauty.resources.EntitiesMocks.getKeyword;
import static co.oshunbeauty.resources.EntitiesMocks.getPayment;
import static co.oshunbeauty.resources.EntitiesMocks.getSupplier;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationsServiceTest {
	
	@Mock
	private Validator validator;
	
	@Mock
	ConstraintViolation<Object> constraintViolationMock;
	
	@Mock
	Path path;
	
	private Set<ConstraintViolation<Object>> constrains;
	private ValidationsService validationsService;
	
	@BeforeEach
	void setUp() {
		constrains = Set.of(constraintViolationMock);
		validationsService = new ValidationsService(validator);
	}
	
	@Test
	public void shouldAcceptIsCategoryValidToSave() {
		// GIVEN
		Category category = getCategory();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCategoryValidToSave(category);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsCategoryValidToSaveWhenItHasNotNullId() {
		// GIVEN
		Category category = getCategory();
		category.setCategoryId(1L);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToSave(category));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsCategoryValidToSaveDueToConstrains() {
		// GIVEN
		Category category = getCategory();
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToSave(category));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsCategoryValidToUpdate() {
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
	public void shouldRejectIsCategoryValidToUpdateWhenItHasNullId() {
		// GIVEN
		Category category = getCategory();
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToUpdate(category));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectValidationCategoryToUpdateDueToConstrains() {
		// GIVEN
		Category category = getCategory();
		category.setCategoryId(1L);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCategoryValidToUpdate(category));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsBrandValidToSave() {
		// GIVEN
		Brand brand = getBrand();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isBrandValidToSave(brand);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsBrandValidToSaveWhenItHasNotNullId() {
		// GIVEN
		Brand brand = getBrand();
		brand.setBrandId(1L);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isBrandValidToSave(brand));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsBrandValidToSaveDueToConstrains() {
		// GIVEN
		Brand brand = getBrand();
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isBrandValidToSave(brand));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsBrandValidToUpdate() {
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
	public void shouldRejectIsBrandValidToUpdateWhenItHasNullId() {
		// GIVEN
		Brand brand = getBrand();
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isBrandValidToUpdate(brand));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsBrandValidToUpdateDueToConstrains() {
		// GIVEN
		Brand brand = getBrand();
		brand.setBrandId(1L);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isBrandValidToUpdate(brand));
		// THEN
		verify(validator, times(1)).validate(any());
	}

	@Test
	public void shouldAcceptIsCustomerValidToSave() {
		// GIVEN
		Customer customer = getCustomer();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCustomerValidToSave(customer);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsCustomerValidToSaveWhenItHasNullId() {
		// GIVEN
		Customer customer = getCustomer();
		customer.setIdentification(null);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isCustomerValidToSave(customer));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsCustomerValidToSaveDueToConstrains() {
		// GIVEN
		Customer customer = getCustomer();
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCustomerValidToSave(customer));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsCustomerValidToUpdate() {
		// GIVEN
		Customer customer = getCustomer();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isCustomerValidToUpdate(customer);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsCustomerValidToUpdateWhenItHasNullId() {
		// GIVEN
		Customer customer = getCustomer();
		customer.setIdentification(null);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isCustomerValidToUpdate(customer));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsCustomerValidToUpdateDueToConstrains() {
		// GIVEN
		Customer customer = getCustomer();
		customer.setIdentification("1017217617");
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isCustomerValidToUpdate(customer));
		// THEN
		verify(validator, times(1)).validate(any());
	}

	@Test
	public void shouldAcceptIsKeywordValidToSave() {
		// GIVEN
		Keyword keyword = getKeyword();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isKeywordValidToSave(keyword);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsKeywordValidToSaveWhenItHasNotNullId() {
		// GIVEN
		Keyword keyword = getKeyword();
		keyword.setKeywordId(1L);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isKeywordValidToSave(keyword));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsKeywordValidToSaveDueToConstrains() {
		// GIVEN
		Keyword keyword = getKeyword();
		keyword.setKey(null);
		keyword.setValue(null);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isKeywordValidToSave(keyword));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsKeywordValidToUpdate() {
		// GIVEN
		Keyword keyword = getKeyword();
		keyword.setKeywordId(1L);
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isKeywordValidToUpdate(keyword);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsKeywordValidToUpdateWhenItHasNullId() {
		// GIVEN
		Keyword keyword = getKeyword();
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isKeywordValidToUpdate(keyword));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsKeywordValidToUpdateDueToConstrains() {
		// GIVEN
		Keyword keyword = getKeyword();
		keyword.setKeywordId(1L);
		keyword.setKey(null);
		keyword.setValue(null);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isKeywordValidToUpdate(keyword));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsPaymentValidToSave() {
		// GIVEN
		Payment payment = getPayment();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isPaymentValidToSave(payment);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsPaymentValidToSaveWhenItHasNotNullId() {
		// GIVEN
		Payment payment = getPayment();
		payment.setPaymentId(1L);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isPaymentValidToSave(payment));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsPaymentValidToSaveDueToConstrains() {
		// GIVEN
		Payment payment = getPayment();
		payment.setPaymentType(null);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isPaymentValidToSave(payment));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsPaymentValidToUpdate() {
		// GIVEN
		Payment payment = getPayment();
		payment.setPaymentId(1L);
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isPaymentValidToUpdate(payment);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsPaymentValidToUpdateWhenItHasNullId() {
		// GIVEN
		Payment payment = getPayment();
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isPaymentValidToUpdate(payment));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsPaymentValidToUpdateDueToConstrains() {
		// GIVEN
		Payment payment = getPayment();
		payment.setPaymentId(1L);
		payment.setPaymentType(null);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isPaymentValidToUpdate(payment));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	// suppliers
	
	@Test
	public void shouldAcceptIsSupplierValidToSave() {
		// GIVEN
		Supplier supplier = getSupplier();
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isSupplierValidToSave(supplier);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsSupplierValidToSaveWhenItHasNotNullId() {
		// GIVEN
		Supplier supplier = getSupplier();
		supplier.setSupplierId(1L);
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isSupplierValidToSave(supplier));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsSupplierValidToSaveDueToConstrains() {
		// GIVEN
		Supplier supplier = getSupplier();
		supplier.setName(null);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isSupplierValidToSave(supplier));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsSupplierValidToUpdate() {
		// GIVEN
		Supplier supplier = getSupplier();
		supplier.setSupplierId(1L);
		// WHEN
		when(validator.validate(any())).thenReturn(new HashSet<>());
		validationsService.isSupplierValidToUpdate(supplier);
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldRejectIsSupplierValidToUpdateWhenItHasNullId() {
		// GIVEN
		Supplier supplier = getSupplier();
		// WHEN
		assertThrows(BadRequestException.class, () -> validationsService.isSupplierValidToUpdate(supplier));
		// THEN
		verifyNoInteractions(validator);
	}
	
	@Test
	public void shouldRejectIsSupplierValidToUpdateDueToConstrains() {
		// GIVEN
		Supplier supplier = getSupplier();
		supplier.setSupplierId(1L);
		supplier.setName(null);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(constraintViolationMock.getPropertyPath()).thenReturn(path);
		when(constraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(constrains);
		assertThrows(BadRequestException.class, () -> validationsService.isSupplierValidToUpdate(supplier));
		// THEN
		verify(validator, times(1)).validate(any());
	}
}
