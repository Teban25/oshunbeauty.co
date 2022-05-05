package co.oshunbeauty.repository;

import co.oshunbeauty.entity.SaleOrder;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
	
	@Query(nativeQuery = true, value = "SELECT 'VENTA-' || NEXTVAL('seq_sale_order_pk')")
	String getNextSaleOrderNumber();
	
	@Query(nativeQuery = true,
			value = "SELECT COUNT(*) FROM SALE_ORDERS S WHERE S.SALE_ORDER_DATE >= :currentDay")
	Integer getSalesOfTheDay(@Param("currentDay") LocalDate currentDay);
}
