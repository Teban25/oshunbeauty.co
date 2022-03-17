package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "purchase_orders_details")
public class PurchaseOrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_order_detail_id")
	private Long purchaseOrderDetailId;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "unit_price", nullable = false)
	private Double unitPrice;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
	@Column(name = "purchase_order_detail_date", nullable = false)
	private ZonedDateTime purchaseOrderDetailDate;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public PurchaseOrderDetail() {
	}
	
	public PurchaseOrderDetail(Product product, Double unitPrice, Integer quantity,
	                           Double total, ZonedDateTime purchaseOrderDetailDate, ZonedDateTime lastModifiedDate, String creationUser, String lastModifiedUser) {
		this.product = product;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.total = total;
		this.purchaseOrderDetailDate = purchaseOrderDetailDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getPurchaseOrderDetailId() {
		return purchaseOrderDetailId;
	}
	
	public void setPurchaseOrderDetailId(Long purchaseOrderDetailId) {
		this.purchaseOrderDetailId = purchaseOrderDetailId;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public ZonedDateTime getPurchaseOrderDetailDate() {
		return purchaseOrderDetailDate;
	}
	
	public void setPurchaseOrderDetailDate(ZonedDateTime purchaseOrderDetailDate) {
		this.purchaseOrderDetailDate = purchaseOrderDetailDate;
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
		return "PurchaseOrderDetail{" +
				"purchaseOrderDetailId=" + purchaseOrderDetailId +
				", product=" + product +
				", unitPrice=" + unitPrice +
				", quantity=" + quantity +
				", total=" + total +
				", purchaseOrderDetailDate=" + purchaseOrderDetailDate +
				", lastModifiedDate=" + lastModifiedDate +
				", creationUser='" + creationUser + '\'' +
				", lastModifiedUser='" + lastModifiedUser + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		PurchaseOrderDetail that = (PurchaseOrderDetail) o;
		return Objects.equals(purchaseOrderDetailId, that.purchaseOrderDetailId) && Objects.equals(product, that.product) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(quantity, that.quantity) && Objects.equals(total, that.total) && Objects.equals(purchaseOrderDetailDate, that.purchaseOrderDetailDate) && Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(creationUser, that.creationUser) && Objects.equals(lastModifiedUser, that.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(purchaseOrderDetailId, product, unitPrice, quantity, total, purchaseOrderDetailDate, lastModifiedDate, creationUser, lastModifiedUser);
	}
}
