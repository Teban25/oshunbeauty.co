package co.oshunbeauty.service;

import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.repository.PaymentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getPayment;
import static co.oshunbeauty.resources.EntitiesMocks.getPayments;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PaymentServiceTest {
	
	@Mock
	private PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
	
	private PaymentService paymentService = new PaymentService(paymentRepository);
	
	private static final String NAME = "efectivo";
	private static final String USER_TEST = "testOshun";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetPaymentByIdNotFound(){
		// Given
		Long paymentId = 1L;
		// When
		Mockito.when(paymentRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Optional<Payment> paymentFound = paymentService.getPaymentById(paymentId);
		// Then
		verify(paymentRepository, times(1)).findById(any(Long.class));
		assertTrue(paymentFound.isEmpty());
	}
	
	@Test
	public void testGetPaymentById() {
		// Given
		Long paymentId = 1L;
		Payment paymentToFind = getPayment();
		paymentToFind.setPaymentId(paymentId);
		// When
		Mockito.when(paymentRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentToFind));
		Optional<Payment> paymentFound = paymentService.getPaymentById(paymentId);
		// Then
		verify(paymentRepository, times(1)).findById(any(Long.class));
		assertAll(
				() -> assertTrue(paymentFound.isPresent()),
				() -> assertEquals(paymentId, paymentFound.get().getPaymentId()),
				() -> assertEquals(NAME, paymentFound.get().getPaymentType())
		);
	}
	
	@Test
	public void testToGetAllPayments() {
		// Given
		List<Payment> payments = getPayments();
		// When
		Mockito.when(paymentRepository.findAll()).thenReturn(payments);
		List<Payment> currentPayments = paymentService.getAllPayments();
		// Then
		verify(paymentRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentPayments),
				() -> assertEquals(4, currentPayments.size()),
				() -> assertEquals(NAME, currentPayments.get(0).getPaymentType())
		);
	}
	
	@Test
	public void testToUpdatePayment(){
		// Given
		Payment paymentToUpdate = getPayment();
		paymentToUpdate.setPaymentId(1L);
		paymentToUpdate.setActive(true);
		
		Payment currentPayment = getPayment();
		currentPayment.setPaymentId(1L);
		currentPayment.setActive(false);
		
		Payment paymentUpdated = BeanUtils.instantiateClass(Payment.class);
		BeanUtils.copyProperties(paymentToUpdate, paymentUpdated);
		paymentUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(paymentUpdated);
		Payment currentPaymentUpdated = paymentService.updatePayment(currentPayment, paymentToUpdate, USER_TEST);
		// Then
		verify(paymentRepository, times(1)).save(any(Payment.class));
		assertAll(
				() -> assertNotNull(currentPaymentUpdated),
				() -> assertEquals(USER_TEST, currentPaymentUpdated.getLastModifiedUser()),
				() -> assertTrue(currentPayment.getActive())
		);
	}
	
	@Test
	public void testToSavePayment(){
		// Given
		Payment paymentToSave = getPayment();
		// When
		Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(paymentToSave);
		Payment currentPayment = paymentService.savePayment(paymentToSave, USER_TEST);
		// Then
		verify(paymentRepository, times(1)).save(any(Payment.class));
		assertAll(
				() -> assertNotNull(currentPayment),
				() -> assertEquals(USER_TEST, currentPayment.getCreationUser()),
				() -> assertEquals(NAME, currentPayment.getPaymentType())
		);
	}
}
