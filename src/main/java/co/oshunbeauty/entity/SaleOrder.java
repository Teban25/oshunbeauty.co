package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "sale_orders")
public class SaleOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_order_id")
	private Long saleOrderId;
	
	@Column(name = "sale_order_number")
	private String saleOrderNumber;
	
	@Column(name = "total_sale_order_price", nullable = false)
	private Double totalSaleOrderPrice;
	
	@Column(name = "sale_order_date", nullable = false)
	private ZonedDateTime saleOrderDate;
	
	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "customer_identification")
	private Customer customer;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public SaleOrder() {
	}
	
	public SaleOrder(Double totalSaleOrderPrice, ZonedDateTime saleOrderDate, Payment payment,
	                 ZonedDateTime lastModifiedDate, String creationUser, String lastModifiedUser) {
		this.totalSaleOrderPrice = totalSaleOrderPrice;
		this.saleOrderDate = saleOrderDate;
		this.payment = payment;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getSaleOrderId() {
		return saleOrderId;
	}
	
	public void setSaleOrderId(Long saleOrderId) {
		this.saleOrderId = saleOrderId;
	}
	
	public String getSaleOrderNumber() {
		return saleOrderNumber;
	}
	
	public void setSaleOrderNumber(String saleOrderNumber) {
		this.saleOrderNumber = saleOrderNumber;
	}
	
	public Double getTotalSaleOrderPrice() {
		return totalSaleOrderPrice;
	}
	
	public void setTotalSaleOrderPrice(Double totalSaleOrderPrice) {
		this.totalSaleOrderPrice = totalSaleOrderPrice;
	}
	
	public ZonedDateTime getSaleOrderDate() {
		return saleOrderDate;
	}
	
	public void setSaleOrderDate(ZonedDateTime saleOrderDate) {
		this.saleOrderDate = saleOrderDate;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
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
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String toString() {
		return "SaleOrder{" +
				"saleOrderId=" + saleOrderId +
				", saleOrderNumber='" + saleOrderNumber + '\'' +
				", totalSaleOrderPrice=" + totalSaleOrderPrice +
				", saleOrderDate=" + saleOrderDate +
				", payment=" + payment +
				", customer=" + customer +
				", lastModifiedDate=" + lastModifiedDate +
				", creationUser='" + creationUser + '\'' +
				", lastModifiedUser='" + lastModifiedUser + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		SaleOrder saleOrder = (SaleOrder) o;
		return Objects.equals(saleOrderId, saleOrder.saleOrderId) && Objects.equals(saleOrderNumber, saleOrder.saleOrderNumber) && Objects.equals(totalSaleOrderPrice, saleOrder.totalSaleOrderPrice) && Objects.equals(saleOrderDate, saleOrder.saleOrderDate) && Objects.equals(payment, saleOrder.payment) && Objects.equals(customer, saleOrder.customer) && Objects.equals(lastModifiedDate, saleOrder.lastModifiedDate) && Objects.equals(creationUser, saleOrder.creationUser) && Objects.equals(lastModifiedUser, saleOrder.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(saleOrderId, saleOrderNumber, totalSaleOrderPrice, saleOrderDate, payment, customer, lastModifiedDate, creationUser, lastModifiedUser);
	}
}