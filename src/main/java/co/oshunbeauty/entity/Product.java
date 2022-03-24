package co.oshunbeauty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "barcode")
    private String barcode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
    
    @Column(name = "current_amount")
    private Integer currentAmount;
    
    @Column(name = "current_price")
    private Double currentPrice;

    @ManyToMany
    @JoinTable(name = "product_keywords",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "last_modified_date", nullable = false)
    private ZonedDateTime lastModifiedDate;

    @Column(name = "creation_user", nullable = false)
    private String creationUser;

    @Column(name = "last_modified_user", nullable = false)
    private String lastModifiedUser;
    
    public Product(Brand brand, String name, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
                   String creationUser, String lastModifiedUser) {
        this.brand = brand;
        this.name = name;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.creationUser = creationUser;
        this.lastModifiedUser = lastModifiedUser;
    }
}
