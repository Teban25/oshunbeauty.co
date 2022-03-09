package co.oshunbeauty.service;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.repository.BrandRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;
import static co.oshunbeauty.constants.Constants.ServicesConstants.IGNORED_STANDARD_FIELDS;

@Service
public class BrandService {
	
	private Set<String> ignoredBrandFields = new HashSet<>(Arrays.asList("brandId"));
	private BrandRepository brandRepository;
	
	@Autowired
	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
		ignoredBrandFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public List<Brand> getAllBrands() {
		return brandRepository.findAll();
	}
	
	public Optional<Brand> getBrandById(Long id) {
		return brandRepository.findById(id);
	}
	
	public List<Brand> getBrandsByName(String name) {
		return brandRepository.findBrandsByName(name);
	}
	
	public Brand saveBrand(Brand brand, String user) {
		brand.setCreationDate(ZonedDateTime.now(ZONE_ID));
		brand.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		brand.setCreationUser(user);
		brand.setLastModifiedUser(user);
		
		return brandRepository.save(brand);
	}
	
	public Brand updateBrand(Brand currentBrand, Brand brandSent, String user) {
		BeanUtils.copyProperties(brandSent, currentBrand, ignoredBrandFields.stream().toArray(String[]::new));
		
		currentBrand.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentBrand.setLastModifiedUser(user);
		
		return brandRepository.save(currentBrand);
	}
	
	public void deleteBrand(Brand brand) {
		brandRepository.delete(brand);
	}
}
