package co.oshunbeauty.repository;

import co.oshunbeauty.entity.Brand;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM brands b WHERE b.company_name LIKE '%'|| :companyName ||'%' ")
	List<Brand> findBrandsByName(@Param("companyName") String companyName);
}
