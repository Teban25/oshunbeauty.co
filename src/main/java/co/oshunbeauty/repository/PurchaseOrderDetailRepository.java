package co.oshunbeauty.repository;

import co.oshunbeauty.entity.PurchaseOrderDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM PURCHASE_ORDERS_DETAILS P WHERE P.PRODUCT_ID = :productId AND P.ACTIVE_PRICE = true")
	Optional<PurchaseOrderDetail> findOrderByProductId(@Param("productId") Long productId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM PURCHASE_ORDERS_DETAILS P WHERE P.PRODUCT_ID = :productId " +
			"AND P.ACTIVE_PRICE = false AND P.QUANTITY_SOLD = 0 ORDER BY purchase_order_detail_date ASC LIMIT 1 ")
	Optional<PurchaseOrderDetail> findNextOrderPurchaseDetailToActive(@Param("productId") Long productId);
}
