package co.oshunbeauty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

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
	
	public Keyword() {
	}
	
	public Keyword(String key, String value, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
	               String creationUser, String lastModifiedUser) {
		this.key = key;
		this.value = value;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getKeywordId() {
		return keywordId;
	}
	
	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public ZonedDateTime getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	public ZonedDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public String getCreationUser() {
		return creationUser;
	}
	
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	
	public String getLastModifiedUser() {
		return lastModifiedUser;
	}
	
	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	
	@Override
	public String toString() {
		return "Keyword{" +
				"keywordId=" + keywordId +
				", key='" + key + '\'' +
				", value='" + value + '\'' +
				", products=" + products +
				", creationDate=" + creationDate +
				", lastModifiedDate=" + lastModifiedDate +
				", creationUser='" + creationUser + '\'' +
				", lastModifiedUser='" + lastModifiedUser + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Keyword keyword = (Keyword) o;
		return keywordId.equals(keyword.keywordId) && key.equals(keyword.key) && value.equals(keyword.value) && Objects.equals(products, keyword.products) && creationDate.equals(keyword.creationDate) && lastModifiedDate.equals(keyword.lastModifiedDate) && creationUser.equals(keyword.creationUser) && lastModifiedUser.equals(keyword.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(keywordId, key, value, products, creationDate, lastModifiedDate, creationUser, lastModifiedUser);
	}
}
