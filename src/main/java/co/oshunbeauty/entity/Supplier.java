package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "suppliers")
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private Long supplierId;
	
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "phone_number1")
	private String phoneNumber1;
	
	@Column(name = "phone_number2")
	private String phoneNumber2;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "creation_date")
	private ZonedDateTime creationDate;
	
	@Column(name = "last_modified_date")
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user")
	private String creationUser;
	
	@Column(name = "last_modified_user")
	private String lastModifiedUser;
	
	public Supplier(String name, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate, String creationUser,
	                String lastModifiedUser) {
		this.name = name;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
}
