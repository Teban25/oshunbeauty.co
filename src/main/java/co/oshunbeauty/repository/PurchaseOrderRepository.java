package co.oshunbeauty.repository;

import co.oshunbeauty.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

	@Query(nativeQuery = true, value = "SELECT 'COMPRA-' || NEXTVAL('seq_purchase_order_pk')")
	String getNextPurchaseOrderNumber();
}
