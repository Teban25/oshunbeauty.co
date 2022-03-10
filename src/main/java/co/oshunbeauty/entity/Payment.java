package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
	
	public Payment() {
	}
	
	public Payment(String paymentType, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
	               String creationUser, String lastModifiedUser) {
		this.paymentType = paymentType;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
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
		return "Payment{" +
				"paymentId=" + paymentId +
				", paymentType='" + paymentType + '\'' +
				", active=" + active +
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
		Payment payment = (Payment) o;
		return paymentId.equals(payment.paymentId) && paymentType.equals(payment.paymentType) && Objects.equals(active, payment.active) && creationDate.equals(payment.creationDate) && lastModifiedDate.equals(payment.lastModifiedDate) && creationUser.equals(payment.creationUser) && lastModifiedUser.equals(payment.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(paymentId, paymentType, active, creationDate, lastModifiedDate, creationUser, lastModifiedUser);
	}
}

