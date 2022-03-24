package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
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
}
