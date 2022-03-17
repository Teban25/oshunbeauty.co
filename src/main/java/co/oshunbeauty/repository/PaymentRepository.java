package co.oshunbeauty.repository;

import co.oshunbeauty.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM payments p WHERE p.payment_type = :paymentName ")
	Optional<Payment> findPaymentByNameFromExcel(@Param("paymentName") String paymentName);
}
