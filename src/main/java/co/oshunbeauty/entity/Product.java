package co.oshunbeauty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "barcode")
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

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
    
    public Product() {
    }
    
    public Product(Brand brand, String name, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
                   String creationUser, String lastModifiedUser) {
        this.brand = brand;
        this.name = name;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.creationUser = creationUser;
        this.lastModifiedUser = lastModifiedUser;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public Brand getBrand() {
        return brand;
    }
    
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    
    public List<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getCurrentAmount() {
        return currentAmount;
    }
    
    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }
    
    public Double getCurrentPrice() {
        return currentPrice;
    }
    
    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
    
    public List<Keyword> getKeywords() {
        return keywords;
    }
    
    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
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
        return "Product{" +
                "productId=" + productId +
                ", barcode='" + barcode + '\'' +
                ", brand=" + brand +
                ", categories=" + categories +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currentAmount=" + currentAmount +
                ", currentPrice=" + currentPrice +
                ", keywords=" + keywords +
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
        Product product = (Product) o;
        return productId.equals(product.productId) && Objects.equals(barcode, product.barcode) && brand.equals(product.brand) && Objects.equals(categories, product.categories) && name.equals(product.name) && Objects.equals(description, product.description) && Objects.equals(currentAmount, product.currentAmount) && Objects.equals(currentPrice, product.currentPrice) && Objects.equals(keywords, product.keywords) && creationDate.equals(product.creationDate) && lastModifiedDate.equals(product.lastModifiedDate) && creationUser.equals(product.creationUser) && lastModifiedUser.equals(product.lastModifiedUser);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(productId, barcode, brand, categories, name, description, currentAmount, currentPrice, keywords, creationDate, lastModifiedDate, creationUser, lastModifiedUser);
    }
}
