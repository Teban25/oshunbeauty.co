package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.PaymentService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/payments")
@Slf4j
public class PaymentController {
	
	private ValidationsService validationsService;
	private PaymentService paymentService;
	
	@Autowired
	public PaymentController(ValidationsService validationsService, PaymentService paymentService) {
		this.validationsService = validationsService;
		this.paymentService = paymentService;
	}
	
	@GetMapping("/")
	public List<Payment> getAllPayments() {
		return paymentService.getAllPayments();
	}
	
	@GetMapping("/{id}")
	public Payment getPaymentById(@PathVariable final Long id) {
		Optional<Payment> paymentFound = paymentService.getPaymentById(id);
		
		if(paymentFound.isEmpty()) {
			log.error("The payment with id {} was not found.", id);
			throw new ResourceNotFoundException(getMessageForPaymentNotFoundException(id));
		}
		
		return paymentFound.get();
	}
	
	@PostMapping
	public Payment savePayment(@RequestBody final Payment payment) {
		validationsService.isPaymentValidToSave(payment);
		
		log.info("Saving new payment with name {} by the user {}", payment.getPaymentType(), "oshun");
		return paymentService.savePayment(payment, "oshun");
	}
	
	@PutMapping("/{id}")
	public Payment updatePayment(@PathVariable final Long id, @RequestBody final Payment payment) {
		validationsService.isPaymentValidToUpdate(payment);
		Optional<Payment> currentPaymentFound = paymentService.getPaymentById(id);
		validatePaymentsAreEqualsById(payment, currentPaymentFound);
		
		log.info("Updating the payment with name {} by the user {}", payment.getPaymentType(), "oshun");
		return paymentService.updatePayment(currentPaymentFound.get(), payment, "oshun");
	}
	
	@DeleteMapping("/{id}")
	public void deletePayment(@PathVariable final Long id) {
		Optional<Payment> currentPaymentFound = paymentService.getPaymentById(id);
		if(currentPaymentFound.isEmpty()) {
			log.error("The payment with id {} was not found.", id);
			throw new BadRequestException(getMessageForPaymentNotFoundException(id));
		}
		
		log.info("Deleting the payment with name {} by the user {}", currentPaymentFound.get().getPaymentType(), "oshun");
		paymentService.deletePayment(currentPaymentFound.get());
	}
	
	private void validatePaymentsAreEqualsById(Payment payment, Optional<Payment> currentPaymentFound) {
		if(currentPaymentFound.isEmpty() || currentPaymentFound.get().getPaymentId() != payment.getPaymentId() ) {
			log.error("When trying to update the payment with id {}, the payment sent had another id",
					payment.getPaymentId());
			throw new BadRequestException(getErrorMessagePaymentsAreNotSame(payment));
		}
	}
	
	private String getErrorMessagePaymentsAreNotSame(Payment payment) {
		return String.format("El tipo de pago con id %s no fue encontrada o no corresponde a la " +
				"ingresado", payment.getPaymentId());
	}
	
	private String getMessageForPaymentNotFoundException(Long id) {
		return String.format("El tipo de pago con id %s no fue encontrada", id);
	}
}
