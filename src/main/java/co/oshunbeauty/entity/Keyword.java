package co.oshunbeauty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "keywords")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Keyword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "keyword_id")
	private Long keywordId;
	
	@NotNull
	@Column(name = "key", nullable = false)
	private String key;
	
	@NotNull
	@Column(name = "value", nullable = false)
	private String value;
	
	@ManyToMany(mappedBy = "keywords")
	@JsonIgnore
	List<Product> products;
	
	@Column(name = "creation_date", nullable = false)
	private ZonedDateTime creationDate;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public Keyword(String key, String value, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
	               String creationUser, String lastModifiedUser) {
		this.key = key;
		this.value = value;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
}
