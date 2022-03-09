package co.oshunbeauty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity(name = "categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
    
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Product> products;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "last_modified_date", nullable = false)
    private ZonedDateTime lastModifiedDate;

    @Column(name = "creation_user", nullable = false)
    private String creationUser;

    @Column(name = "last_modified_user", nullable = false)
    private String lastModifiedUser;
    
    public Category() {
    }
    
    public Category(String name, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate, String creationUser,
                    String lastModifiedUser) {
        this.name = name;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.creationUser = creationUser;
        this.lastModifiedUser = lastModifiedUser;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
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
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", products=" + products +
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
        Category category = (Category) o;
        return categoryId.equals(category.categoryId) && name.equals(category.name) && Objects.equals(description, category.description) && Objects.equals(products, category.products) && creationDate.equals(category.creationDate) && lastModifiedDate.equals(category.lastModifiedDate) && creationUser.equals(category.creationUser) && lastModifiedUser.equals(category.lastModifiedUser);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, description, products, creationDate, lastModifiedDate, creationUser, lastModifiedUser);
    }
}
