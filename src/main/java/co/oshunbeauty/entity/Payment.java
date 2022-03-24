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
@Entity(name = "payments")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentId;
	
	@NotNull
	@Column(name = "payment_type", nullable = false)
	private String paymentType;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "creation_date", nullable = false)
	private ZonedDateTime creationDate;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public Payment(String paymentType, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
	               String creationUser, String lastModifiedUser) {
		this.paymentType = paymentType;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
}

