package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.BrandService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/brands")
@Slf4j
public class BrandController {
	
	private ValidationsService validationsService;
	private BrandService brandService;
	
	@Autowired
	public BrandController(ValidationsService validationsService, BrandService brandService) {
		this.validationsService = validationsService;
		this.brandService = brandService;
	}
	
	@GetMapping("/")
	public List<Brand> getAllBrands() {
		return brandService.getAllBrands();
	}
	
	@GetMapping("/{id}")
	public Brand getBrandById(@PathVariable final Long id) {
		Optional<Brand> brandFound = brandService.getBrandById(id);
		
		if(brandFound.isEmpty()) {
			log.error("The brand with id {} was not found.", id);
			throw new ResourceNotFoundException(getMessageForBrandNotFoundException(id));
		}
		
		return brandFound.get();
	}
	
	@GetMapping("/names")
	public List<Brand> getBrandsByName(@RequestParam final String name) {
		return brandService.getBrandsByName(name);
	}
	
	@PostMapping
	public Brand saveBrand(@RequestBody final Brand brand) {
		validationsService.isBrandValidToSave(brand);
		
		log.info("Saving new brand with name {} by the user {}", brand.getCompanyName(), "oshun");
		return brandService.saveBrand(brand, "oshun");
	}
	
	@PutMapping("/{id}")
	public Brand updateBrand(@PathVariable final Long id, @RequestBody final Brand brand) {
		validationsService.isBrandValidToUpdate(brand);
		Optional<Brand> currentBrandFound = brandService.getBrandById(id);
		validateBrandsAreEqualsById(brand, currentBrandFound);
		
		log.info("Updating the brand with name {} by the user {}", brand.getCompanyName(), "oshun");
		return brandService.updateBrand(currentBrandFound.get(), brand, "oshun");
	}
	
	@DeleteMapping("/{id}")
	public void deleteBrand(@PathVariable final Long id) {
		Optional<Brand> currentBrandFound = brandService.getBrandById(id);
		if(currentBrandFound.isEmpty()) {
			log.error("The brand with id {} was not found.", id);
			throw new BadRequestException(getMessageForBrandNotFoundException(id));
		}
		
		log.info("Deleting the brand with name {} by the user {}", currentBrandFound.get().getCompanyName(), "oshun");
		brandService.deleteBrand(currentBrandFound.get());
	}
	
	private void validateBrandsAreEqualsById(Brand brand, Optional<Brand> currentBrandFound) {
		if(currentBrandFound.isEmpty() || currentBrandFound.get().getBrandId() != brand.getBrandId() ) {
			log.error("When trying to update brand with id {}, the brand sent had another id",
					brand.getBrandId());
			throw new BadRequestException(getErrorMessageBrandsAreNotSame(brand));
		}
	}
	
	private String getErrorMessageBrandsAreNotSame(Brand brand) {
		return String.format("La marca con id %s no fue encontrada o no corresponde a la " +
				"ingresada", brand.getBrandId());
	}
	
	private String getMessageForBrandNotFoundException(Long id) {
		return String.format("La marca con id %s no fue encontrada", id);
	}
}
