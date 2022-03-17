package co.oshunbeauty.repository;

import co.oshunbeauty.entity.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM suppliers s WHERE s.name LIKE '%' || :name || '%' ")
	List<Supplier> findSuppliersByName(@Param("name") String name);
	
	@Query(nativeQuery = true, value = "SELECT * FROM suppliers s WHERE s.name = :name ")
	List<Supplier> findSuppliersByNameFromExcel(@Param("name") String name);
}
