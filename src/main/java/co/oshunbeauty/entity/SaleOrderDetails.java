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

@Entity(name = "sale_orders_details")
public class SaleOrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_order_detail_id")
	private Long saleOrderDetailId;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "sale_order_id", nullable = false)
	private SaleOrder saleOrder;
	
	@Column(name = "unit_price", nullable = false)
	private Double unitPrice;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
	@Column(name = "sale_order_detail_date", nullable = false)
	private ZonedDateTime saleOrderDetailDate;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public SaleOrderDetails() {
	}
	
	public SaleOrderDetails(Product product, SaleOrder saleOrder, Double unitPrice, Integer quantity, Double total,
	                        ZonedDateTime saleOrderDetailDate, ZonedDateTime lastModifiedDate, String creationUser,
	                        String lastModifiedUser) {
		this.product = product;
		this.saleOrder = saleOrder;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.total = total;
		this.saleOrderDetailDate = saleOrderDetailDate;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
	
	public Long getSaleOrderDetailId() {
		return saleOrderDetailId;
	}
	
	public void setSaleOrderDetailId(Long saleOrderDetailId) {
		this.saleOrderDetailId = saleOrderDetailId;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}
	
	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
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
	
	public ZonedDateTime getSaleOrderDetailDate() {
		return saleOrderDetailDate;
	}
	
	public void setSaleOrderDetailDate(ZonedDateTime saleOrderDetailDate) {
		this.saleOrderDetailDate = saleOrderDetailDate;
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
		return "SaleOrderDetails{" +
				"saleOrderDetailId=" + saleOrderDetailId +
				", product=" + product +
				", saleOrder=" + saleOrder +
				", unitPrice=" + unitPrice +
				", quantity=" + quantity +
				", total=" + total +
				", saleOrderDetailDate=" + saleOrderDetailDate +
				", lastModifiedDate=" + lastModifiedDate +
				", creationUser='" + creationUser + '\'' +
				", lastModifiedUser='" + lastModifiedUser + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		SaleOrderDetails that = (SaleOrderDetails) o;
		return saleOrderDetailId.equals(that.saleOrderDetailId) && product.equals(that.product) && saleOrder.equals(that.saleOrder) && unitPrice.equals(that.unitPrice) && quantity.equals(that.quantity) && total.equals(that.total) && saleOrderDetailDate.equals(that.saleOrderDetailDate) && lastModifiedDate.equals(that.lastModifiedDate) && creationUser.equals(that.creationUser) && lastModifiedUser.equals(that.lastModifiedUser);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(saleOrderDetailId, product, saleOrder, unitPrice, quantity, total, saleOrderDetailDate, lastModifiedDate, creationUser, lastModifiedUser);
	}
}
