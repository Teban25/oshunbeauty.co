package co.oshunbeauty.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "last_modified_date", nullable = false)
    private ZonedDateTime lastModifiedDate;

    @Column(name = "creation_user", nullable = false)
    private String creationUser;

    @Column(name = "last_modified_user", nullable = false)
    private String lastModifiedUser;
    
    public Brand(String companyName, ZonedDateTime creationDate, ZonedDateTime lastModifiedDate,
                 String creationUser, String lastModifiedUser) {
        this.companyName = companyName;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.creationUser = creationUser;
        this.lastModifiedUser = lastModifiedUser;
    }
    
    public Long getBrandId() {
        return brandId;
    }
    
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
}
