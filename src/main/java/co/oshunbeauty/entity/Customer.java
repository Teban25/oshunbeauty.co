package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "customers")
public class Customer {

	@Id
	@Column(name = "identification")
	private String identification;
	
	@NotNull
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone_number1")
	private String phoneNumber1;
	
	@Column(name = "phone_number2")
	private String phoneNumber2;
	
	@Column(name = "creation_date", nullable = false)
	private ZonedDateTime creationDate;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public Customer() {
	}
	
	public Customer(String identification, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
	                String creationUser, String lastModifiedUser) {
		this.identification = identification;
		this.creationDate = creationDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public String getIdentification() {
		return identification;
	}
	
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
		return "Customer{" +
				"identification='" + identification + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", address1='" + address1 + '\'' +
				", address2='" + address2 + '\'' +
				", email='" + email + '\'' +
				", phoneNumber1='" + phoneNumber1 + '\'' +
				", phoneNumber2='" + phoneNumber2 + '\'' +
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
		Customer customer = (Customer) o;
		return identification.equals(customer.identification) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(address1, customer.address1) && Objects.equals(address2, customer.address2) && Objects.equals(email, customer.email) && Objects.equals(phoneNumber1, customer.phoneNumber1) && Objects.equals(phoneNumber2, customer.phoneNumber2) && Objects.equals(creationDate, customer.creationDate) && Objects.equals(lastModifiedDate, customer.lastModifiedDate) && Objects.equals(creationUser, customer.creationUser) && Objects.equals(lastModifiedUser, customer.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(identification, firstName, lastName, address1, address2, email, phoneNumber1, phoneNumber2, creationDate, lastModifiedDate, creationUser, lastModifiedUser);
	}
}
