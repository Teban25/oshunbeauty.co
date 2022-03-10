package co.oshunbeauty.validation;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.entity.Customer;
import co.oshunbeauty.entity.Keyword;
import co.oshunbeauty.entity.Payment;
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
import static co.oshunbeauty.resources.EntitiesMocks.getKeyword;
import static co.oshunbeauty.resources.EntitiesMocks.getPayment;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class ValidationsServiceTest {
	
	@Mock
	private Validator validator = Mockito.mock(Validator.class);
	
	private ValidationsService validationsService = new ValidationsService(validator);
	
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
	public void shouldAcceptIsCustomerValidToUpdate() {
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
		ConstraintViolation<Object> keywordConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> keywordConstrains = Set.of(keywordConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(keywordConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(keywordConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(keywordConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isKeywordValidToSave(keyword));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsKeywordValidToUpdate() {
		// GIVEN
		Keyword keyword = getKeyword();
		keyword.setKeywordId(1L);
		keyword.setKey("color");
		keyword.setValue("naranja");
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
		ConstraintViolation<Object> customerConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> customerConstrains = Set.of(customerConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(customerConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(customerConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(customerConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isKeywordValidToUpdate(keyword));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	// payments
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
		ConstraintViolation<Object> paymentConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> paymentConstrains = Set.of(paymentConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(paymentConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(paymentConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(paymentConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isPaymentValidToSave(payment));
		// THEN
		verify(validator, times(1)).validate(any());
	}
	
	@Test
	public void shouldAcceptIsPaymentValidToUpdate() {
		// GIVEN
		Payment payment = getPayment();
		payment.setPaymentId(1L);
		payment.setPaymentType("efectivo");
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
		ConstraintViolation<Object> paymentConstraintViolationMock = Mockito.mock(ConstraintViolation.class);
		Set<ConstraintViolation<Object>> paymentConstrains = Set.of(paymentConstraintViolationMock);
		Path path = Mockito.mock(Path.class);
		// WHEN
		when(path.toString()).thenReturn("TestField");
		when(paymentConstraintViolationMock.getPropertyPath()).thenReturn(path);
		when(paymentConstraintViolationMock.getMessage()).thenReturn("TestMessage");
		when(validator.validate(any())).thenReturn(paymentConstrains);
		assertThrows(BadRequestException.class, () -> validationsService.isPaymentValidToUpdate(payment));
		// THEN
		verify(validator, times(1)).validate(any());
	}
}
