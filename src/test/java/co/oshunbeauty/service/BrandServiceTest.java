package co.oshunbeauty.service;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.repository.BrandRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getBrand;
import static co.oshunbeauty.resources.EntitiesMocks.getBrands;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BrandServiceTest {
	
	@Mock
	private BrandRepository brandRepository = Mockito.mock(BrandRepository.class);
	
	private BrandService brandService = new BrandService(brandRepository);
	
	private static final String NAME = "athos";
	private static final String USER_TEST = "testOshun";
	private static final String NAME_TO_UPDATE = "athos nuevos";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetBrandByIdNotFound(){
		// Given
		Long brandId = 1L;
		// When
		Mockito.when(brandRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Optional<Brand> brandFound = brandService.getBrandById(brandId);
		// Then
		verify(brandRepository, times(1)).findById(any(Long.class));
		assertTrue(brandFound.isEmpty());
	}
	
	@Test
	public void testGetBrandById() {
		// Given
		Long brandId = 1L;
		Brand brandToFind = getBrand();
		brandToFind.setBrandId(brandId);
		// When
		Mockito.when(brandRepository.findById(any(Long.class))).thenReturn(Optional.of(brandToFind));
		Optional<Brand> brandFound = brandService.getBrandById(brandId);
		// Then
		verify(brandRepository, times(1)).findById(any(Long.class));
		assertAll(
				() -> assertTrue(brandFound.isPresent()),
				() -> assertEquals(brandId, brandFound.get().getBrandId()),
				() -> assertEquals(NAME, brandFound.get().getCompanyName())
		);
	}
	
	@Test
	public void testToGetAllBrands() {
		// Given
		List<Brand> brands = getBrands();
		// When
		Mockito.when(brandRepository.findAll()).thenReturn(brands);
		List<Brand> currentBrands = brandService.getAllBrands();
		// Then
		verify(brandRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentBrands),
				() -> assertEquals(4, currentBrands.size()),
				() -> assertEquals(NAME, currentBrands.get(0).getCompanyName())
		);
	}
	
	@Test
	public void testToGetBrandsByName() {
		// Given
		List<Brand> brands = getBrands().stream().filter(i -> i.getCompanyName().contains(NAME))
				.collect(Collectors.toList());
		// When
		Mockito.when(brandRepository.findBrandsByName(any(String.class))).thenReturn(brands);
		List<Brand> currentBrands = brandService.getBrandsByName(NAME);
		// Then
		verify(brandRepository, times(1)).findBrandsByName(any(String.class));
		assertAll(
				() -> assertNotNull(currentBrands),
				() -> assertEquals(1, currentBrands.size()),
				() -> assertEquals(NAME, currentBrands.get(0).getCompanyName())
		);
	}
	
	@Test
	public void testToUpdateBrand(){
		// Given
		Brand brandToUpdate = getBrand();
		brandToUpdate.setBrandId(1L);
		brandToUpdate.setCompanyName(NAME_TO_UPDATE);
		
		Brand currentBrand = getBrand();
		currentBrand.setBrandId(1L);
		
		Brand brandUpdated = BeanUtils.instantiateClass(Brand.class);
		BeanUtils.copyProperties(brandToUpdate, brandUpdated);
		brandUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(brandRepository.save(any(Brand.class))).thenReturn(brandUpdated);
		Brand currentBrandUpdated = brandService.updateBrand(currentBrand, brandToUpdate, USER_TEST);
		// Then
		verify(brandRepository, times(1)).save(any(Brand.class));
		assertAll(
				() -> assertNotNull(currentBrandUpdated),
				() -> assertEquals(USER_TEST, currentBrandUpdated.getLastModifiedUser()),
				() -> assertEquals(NAME_TO_UPDATE, currentBrandUpdated.getCompanyName())
		);
	}
	
	@Test
	public void testToSaveBrand(){
		// Given
		Brand brandToSave = getBrand();
		// When
		Mockito.when(brandRepository.save(any(Brand.class))).thenReturn(brandToSave);
		Brand currentBrand = brandService.saveBrand(brandToSave, USER_TEST);
		// Then
		verify(brandRepository, times(1)).save(any(Brand.class));
		assertAll(
				() -> assertNotNull(currentBrand),
				() -> assertEquals(USER_TEST, currentBrand.getCreationUser()),
				() -> assertEquals(NAME, currentBrand.getCompanyName())
		);
	}
}
