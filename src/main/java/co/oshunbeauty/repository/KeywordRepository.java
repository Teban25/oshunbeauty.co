package co.oshunbeauty.repository;

import co.oshunbeauty.entity.Keyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
	
	// TODO - please add index in this column
	@Query(nativeQuery = true, value = "SELECT * FROM keywords k WHERE k.key like '%' || :key  || '%' ")
	List<Keyword> findKeywordsByKey(@Param("key") String key);
}
