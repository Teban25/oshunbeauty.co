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
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "sale_orders")
@DynamicInsert
@DynamicUpdate
public class SaleOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_order_id")
	private Long saleOrderId;
	
	@Column(name = "sale_order_number")
	private String saleOrderNumber;
	
	@Column(name = "total_sale_order_price", nullable = false)
	private Double totalSaleOrderPrice;
	
	@Column(name = "customer_payment")
	private Double customerPayment;
	
	@Column(name = "customer_payment_back")
	private Double customerPaymentBack;
	
	@Column(name = "sale_order_date", nullable = false)
	private ZonedDateTime saleOrderDate;
	
	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "customer_identification")
	private Customer customer;
	
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "sale_order_id")
	private List<SaleOrderDetails> saleOrderDetails;
	
	@Column(name = "last_modified_date", nullable = false)
	private ZonedDateTime lastModifiedDate;
	
	@Column(name = "creation_user", nullable = false)
	private String creationUser;
	
	@Column(name = "last_modified_user", nullable = false)
	private String lastModifiedUser;
	
	public SaleOrder(Double totalSaleOrderPrice, ZonedDateTime saleOrderDate, Payment payment,
	                 ZonedDateTime lastModifiedDate, String creationUser, String lastModifiedUser) {
		this.totalSaleOrderPrice = totalSaleOrderPrice;
		this.saleOrderDate = saleOrderDate;
		this.payment = payment;
		this.lastModifiedDate = lastModifiedDate;
		this.creationUser = creationUser;
		this.lastModifiedUser = lastModifiedUser;
	}
}