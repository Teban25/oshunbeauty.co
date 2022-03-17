package co.oshunbeauty.repository;

import co.oshunbeauty.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.barcode = :barCode LIMIT 1")
	Optional<Product> findProductByBarcode(@Param("barCode") String barCode);
	
	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.name LIKE '%' || :name || '%' ")
	List<Product> findProductsByName(@Param("name") String name);
	
	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.name = :name AND p.brand_id = :brandId LIMIT 1")
	Optional<Product> findProductByNameAndBrand(@Param("name") String name, @Param("brandId") Long brandId);
	
	// TODO - design a query for customize search including name, brand, category and description.
}
