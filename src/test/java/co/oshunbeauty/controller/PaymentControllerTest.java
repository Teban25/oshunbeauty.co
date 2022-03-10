package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.PaymentService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getPayment;
import static co.oshunbeauty.resources.EntitiesMocks.getPayments;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
	
	@InjectMocks
	PaymentController paymentController;
	
	@Mock
	PaymentService paymentService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final Long PAYMENT_ID = 1L;
	private static final String NAME = "efectivo";
	private static final String USER_TEST = "oshunTest";
	
	@Test
	public void testThatGetPaymentById() {
		// GIVEN
		Payment paymentToFind = getPayment();
		paymentToFind.setPaymentId(PAYMENT_ID);
		// WHEN
		when(paymentService.getPaymentById(any(Long.class))).thenReturn(Optional.of(paymentToFind));
		Payment actualPayment = paymentController.getPaymentById(PAYMENT_ID);
		// THEN
		verify(paymentService, times(1)).getPaymentById(any(Long.class));
		assertAll(
				() -> assertNotNull(actualPayment),
				() -> assertEquals(PAYMENT_ID, actualPayment.getPaymentId()),
				() -> assertEquals(NAME, actualPayment.getPaymentType())
		);
	}
	
	@Test
	public void testThatNotFoundGetPaymentById() {
		// GIVEN
		
		// WHEN
		when(paymentService.getPaymentById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> paymentController.getPaymentById(PAYMENT_ID));
		verify(paymentService, times(1)).getPaymentById(any(Long.class));
	}
	
	@Test
	public void testThatGetAllPayments() {
		// GIVEN
		List<Payment> paymentsToFind = getPayments();
		// WHEN
		when(paymentService.getAllPayments()).thenReturn(paymentsToFind);
		List<Payment> actualPayments = paymentController.getAllPayments();
		// THEN
		verify(paymentService, times(1)).getAllPayments();
		assertAll(
				() -> assertNotNull(actualPayments),
				() -> assertEquals(4, actualPayments.size()),
				() -> assertEquals(NAME, actualPayments.get(0).getPaymentType())
		);
	}
	
	@Test
	public void testThatSavePayment() {
		// GIVEN
		Payment paymentToSave = getPayment();
		Payment paymentSaved = BeanUtils.instantiateClass(Payment.class);
		BeanUtils.copyProperties(paymentToSave, paymentSaved);
		paymentSaved.setPaymentId(PAYMENT_ID);
		// WHEN
		when(paymentService.savePayment(any(Payment.class), any(String.class)))
				.thenReturn(paymentSaved);
		Payment actualPayment = paymentController.savePayment(paymentToSave);
		// THEN
		verify(validationsService, times(1)).isPaymentValidToSave(any(Payment.class));
		verify(paymentService, times(1)).savePayment(any(Payment.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualPayment),
				() -> assertNotNull(actualPayment.getCreationDate()),
				() -> assertNotNull(actualPayment.getLastModifiedDate()),
				() -> assertEquals(PAYMENT_ID, actualPayment.getPaymentId()),
				() -> assertEquals(NAME, actualPayment.getPaymentType()),
				() -> assertEquals("test", actualPayment.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingPayment() {
		// GIVEN
		Payment paymentToSave = getPayment();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isPaymentValidToSave(any(Payment.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> paymentController.savePayment(paymentToSave));
		verify(validationsService,
				times(1)).isPaymentValidToSave(any(Payment.class));
		verifyNoInteractions(paymentService);
	}
	
	@Test
	public void testThatFailsUpdatingPaymentDueToValidations() {
		// GIVEN
		Payment paymentToSave = getPayment();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isPaymentValidToUpdate(any(Payment.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> paymentController.updatePayment(PAYMENT_ID, paymentToSave));
		verify(validationsService,
				times(1)).isPaymentValidToUpdate(any(Payment.class));
		verifyNoInteractions(paymentService);
	}
	
	@Test
	public void testThatFailsUpdatingPaymentDueToWrongIds() {
		// GIVEN
		Payment paymentToUpdate = getPayment();
		paymentToUpdate.setPaymentId(3L);
		
		Payment paymentFound = getPayment();
		paymentFound.setPaymentId(PAYMENT_ID);
		// WHEN
		when(paymentService.getPaymentById(any(Long.class))).thenReturn(Optional.of(paymentFound));
		// THEN
		assertThrows(BadRequestException.class, () -> paymentController.updatePayment(PAYMENT_ID, paymentToUpdate));
		verify(validationsService, times(1)).isPaymentValidToUpdate(any(Payment.class));
		verify(paymentService, times(1)).getPaymentById(any(Long.class));
		verifyNoMoreInteractions(paymentService);
	}
	
	@Test
	public void testThatUpdatePayment() {
		// GIVEN
		Payment paymentToUpdate = getPayment();
		paymentToUpdate.setPaymentId(PAYMENT_ID);
		paymentToUpdate.setActive(true);
		
		Payment paymentFound = getPayment();
		paymentFound.setPaymentId(PAYMENT_ID);
		paymentFound.setActive(false);
		
		Payment paymentUpdated = BeanUtils.instantiateClass(Payment.class);
		BeanUtils.copyProperties(paymentToUpdate, paymentUpdated);
		paymentUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(paymentService.getPaymentById(any(Long.class))).thenReturn(Optional.of(paymentFound));
		when(paymentService.updatePayment(any(Payment.class), any(Payment.class), any(String.class)))
				.thenReturn(paymentUpdated);
		Payment actualPayment = paymentController.updatePayment(PAYMENT_ID, paymentToUpdate);
		// THEN
		verify(validationsService, times(1)).isPaymentValidToUpdate(any(Payment.class));
		verify(paymentService, times(1)).getPaymentById(any(Long.class));
		verify(paymentService, times(1))
				.updatePayment(any(Payment.class), any(Payment.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualPayment),
				() -> assertNotNull(actualPayment.getCreationDate()),
				() -> assertNotNull(actualPayment.getLastModifiedDate()),
				() -> assertEquals(PAYMENT_ID, actualPayment.getPaymentId()),
				() -> assertTrue(actualPayment.getActive()),
				() -> assertEquals(USER_TEST, actualPayment.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeletePayment() {
		// GIVEN
		Payment paymentFound = getPayment();
		paymentFound.setPaymentId(PAYMENT_ID);
		
		// WHEN
		when(paymentService.getPaymentById(any(Long.class))).thenReturn(Optional.of(paymentFound));
		paymentController.deletePayment(PAYMENT_ID);
		// THEN
		verify(paymentService, times(1)).getPaymentById(any(Long.class));
		verify(paymentService, times(1)).deletePayment(any(Payment.class));
	}
	
	@Test
	public void testThatFailsDeletingPayment() {
		// GIVEN
		
		// WHEN
		when(paymentService.getPaymentById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> paymentController.deletePayment(PAYMENT_ID));
		verify(paymentService, times(1)).getPaymentById(any(Long.class));
		verifyNoMoreInteractions(paymentService);
	}
}
