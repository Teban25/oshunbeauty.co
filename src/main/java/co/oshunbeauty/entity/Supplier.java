package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "suppliers")
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private Long supplierId;
	
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
	
	public Supplier() {
	}
	
	public Supplier(String name, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate, String creationUser,
	                String lastModifiedUser) {
		this.name = name;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
		return "Supplier{" +
				"supplierId=" + supplierId +
				", name='" + name + '\'' +
				", phoneNumber1='" + phoneNumber1 + '\'' +
				", phoneNumber2='" + phoneNumber2 + '\'' +
				", address='" + address + '\'' +
				", description='" + description + '\'' +
				", email='" + email + '\'' +
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
		Supplier supplier = (Supplier) o;
		return supplierId.equals(supplier.supplierId) && name.equals(supplier.name) && Objects.equals(phoneNumber1, supplier.phoneNumber1) && Objects.equals(phoneNumber2, supplier.phoneNumber2) && Objects.equals(address, supplier.address) && Objects.equals(description, supplier.description) && Objects.equals(email, supplier.email) && creationDate.equals(supplier.creationDate) && lastModifiedDate.equals(supplier.lastModifiedDate) && creationUser.equals(supplier.creationUser) && lastModifiedUser.equals(supplier.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(supplierId, name, phoneNumber1, phoneNumber2, address, description, email, creationDate, lastModifiedDate, creationUser, lastModifiedUser);
	}
}
