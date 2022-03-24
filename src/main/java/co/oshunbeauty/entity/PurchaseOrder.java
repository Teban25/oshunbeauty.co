package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "purchase_orders")
@DynamicInsert
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
	
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "purchase_order_id")
	private List<PurchaseOrderDetail> purchaseOrderDetails;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;

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
}
