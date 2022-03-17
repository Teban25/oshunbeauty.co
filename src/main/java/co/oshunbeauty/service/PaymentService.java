package co.oshunbeauty.service;

import co.oshunbeauty.entity.Payment;
import co.oshunbeauty.repository.PaymentRepository;
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
public class PaymentService {
	
	private Set<String> ignoredPaymentFields = new HashSet<>(Arrays.asList("paymentId", "paymentType"));
	private PaymentRepository paymentRepository;

	@Autowired
	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
		ignoredPaymentFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}
	
	public Optional<Payment> getPaymentById(Long id) {
		return paymentRepository.findById(id);
	}
	
	public Payment savePayment(Payment payment, String user) {
		payment.setCreationDate(ZonedDateTime.now(ZONE_ID));
		payment.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		payment.setCreationUser(user);
		payment.setLastModifiedUser(user);
		
		return paymentRepository.save(payment);
	}
	
	public Payment updatePayment(Payment currentPayment, Payment paymentSent, String user) {
		BeanUtils.copyProperties(paymentSent, currentPayment, ignoredPaymentFields.stream().toArray(String[]::new));
		
		currentPayment.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentPayment.setLastModifiedUser(user);
		
		return paymentRepository.save(currentPayment);
	}
	
	public void deletePayment(Payment payment) {
		paymentRepository.delete(payment);
	}
	
	public Optional<Payment> getPaymentByNameFromExcel(String paymentName) {
		return paymentRepository.findPaymentByNameFromExcel(paymentName);
	}
}
