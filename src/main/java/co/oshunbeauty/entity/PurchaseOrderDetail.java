package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "purchase_orders_details")
public class PurchaseOrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_order_detail_id")
	private Long purchaseOrderDetailId;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "quantity_sold", nullable = false)
	private Integer quantitySold;
	
	@Column(name = "active_price", nullable = false)
	private Boolean activePrice;
	
	@Column(name = "unit_price", nullable = false)
	private Double unitPrice;
	
	@Column(name = "unit_sell_price", nullable = false)
	private Double unitSellPrice;
	
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
}
