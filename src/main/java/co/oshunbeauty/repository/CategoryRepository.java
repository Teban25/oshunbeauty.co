package co.oshunbeauty.repository;

import co.oshunbeauty.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM categories c WHERE c.name LIKE '%' || :name || '%' ")
	List<Category> findCategoriesByName(@Param("name") String name);
}
