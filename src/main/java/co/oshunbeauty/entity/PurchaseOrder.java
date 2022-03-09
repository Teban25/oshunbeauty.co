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

@Entity(name = "purchase_orders")
public class PurchaseOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_order_id")
	private Long purchaseOrderId;
	
	@Column(name = "purchase_order_number")
	private String purchaseOrderNumber;
	
	@Column(name = "total_purchase_order_price", nullable = false)
	private Double totalPurchaseOrderPrice;
	
	@Column(name = "purchase_order_date", nullable = false)
	private ZonedDateTime purchaseOrderDate;
	
	@ManyToOne
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public PurchaseOrder() {
	}
	
	public PurchaseOrder(Double totalPurchaseOrderPrice, ZonedDateTime purchaseOrderDate, Supplier supplier,
	                     Payment payment, ZonedDateTime lastModifiedDate, String creationUser, String lastModifiedUser) {
		this.totalPurchaseOrderPrice = totalPurchaseOrderPrice;
		this.purchaseOrderDate = purchaseOrderDate;
		this.supplier = supplier;
		this.payment = payment;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}
	
	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
	
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	
	public Double getTotalPurchaseOrderPrice() {
		return totalPurchaseOrderPrice;
	}
	
	public void setTotalPurchaseOrderPrice(Double totalPurchaseOrderPrice) {
		this.totalPurchaseOrderPrice = totalPurchaseOrderPrice;
	}
	
	public ZonedDateTime getPurchaseOrderDate() {
		return purchaseOrderDate;
	}
	
	public void setPurchaseOrderDate(ZonedDateTime purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
	
	@Override
	public String toString() {
		return "PurchaseOrder{" +
				"purchaseOrderId=" + purchaseOrderId +
				", purchaseOrderNumber='" + purchaseOrderNumber + '\'' +
				", totalPurchaseOrderPrice=" + totalPurchaseOrderPrice +
				", purchaseOrderDate=" + purchaseOrderDate +
				", supplier=" + supplier +
				", payment=" + payment +
				", lastModifiedDate=" + lastModifiedDate +
				", creationUser='" + creationUser + '\'' +
				", lastModifiedUser='" + lastModifiedUser + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		PurchaseOrder that = (PurchaseOrder) o;
		return purchaseOrderId.equals(that.purchaseOrderId) && purchaseOrderNumber.equals(that.purchaseOrderNumber) && totalPurchaseOrderPrice.equals(that.totalPurchaseOrderPrice) && purchaseOrderDate.equals(that.purchaseOrderDate) && supplier.equals(that.supplier) && payment.equals(that.payment) && lastModifiedDate.equals(that.lastModifiedDate) && creationUser.equals(that.creationUser) && lastModifiedUser.equals(that.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(purchaseOrderId, purchaseOrderNumber, totalPurchaseOrderPrice, purchaseOrderDate, supplier, payment, lastModifiedDate, creationUser, lastModifiedUser);
	}
}
