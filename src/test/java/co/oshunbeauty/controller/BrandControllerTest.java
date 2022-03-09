package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Brand;
import co.oshunbeauty.entity.Category;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.BrandService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getBrand;
import static co.oshunbeauty.resources.EntitiesMocks.getBrands;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {
	
	@InjectMocks
	BrandController brandController;
	
	@Mock
	BrandService brandService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final Long BRAND_ID = 1L;
	private static final String NAME = "athos";
	private static final String USER_TEST = "oshunTest";
	
	@Test
	public void testThatGetBrandById() {
		// GIVEN
		Brand brandToFind = getBrand();
		brandToFind.setBrandId(BRAND_ID);
		// WHEN
		when(brandService.getBrandById(any(Long.class))).thenReturn(Optional.of(brandToFind));
		Brand actualBrand = brandController.getBrandById(BRAND_ID);
		// THEN
		verify(brandService, times(1)).getBrandById(any(Long.class));
		assertAll(
				() -> assertNotNull(actualBrand),
				() -> assertEquals(BRAND_ID, actualBrand.getBrandId()),
				() -> assertEquals(NAME, actualBrand.getCompanyName())
		);
	}
	
	@Test
	public void testThatNotFoundGetBrandById() {
		// GIVEN
		
		// WHEN
		when(brandService.getBrandById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> brandController.getBrandById(BRAND_ID));
		verify(brandService, times(1)).getBrandById(any(Long.class));
	}
	
	@Test
	public void testThatGetAllBrands() {
		// GIVEN
		List<Brand> brandsToFind = getBrands();
		// WHEN
		when(brandService.getAllBrands()).thenReturn(brandsToFind);
		List<Brand> actualBrand = brandController.getAllBrands();
		// THEN
		verify(brandService, times(1)).getAllBrands();
		assertAll(
				() -> assertNotNull(actualBrand),
				() -> assertEquals(4, actualBrand.size()),
				() -> assertEquals(NAME, actualBrand.get(0).getCompanyName())
		);
	}
	
	@Test
	public void testThatGetBrandByName() {
		// GIVEN
		List<Brand> brandsToFind = getBrands().stream().filter(i -> i.getCompanyName().contains(NAME))
				.collect(Collectors.toList());
		// WHEN
		when(brandService.getBrandsByName(any(String.class))).thenReturn(brandsToFind);
		List<Brand> actualBrands = brandController.getBrandsByName(NAME);
		// THEN
		verify(brandService, times(1)).getBrandsByName(any(String.class));
		assertAll(
				() -> assertNotNull(actualBrands),
				() -> assertEquals(1, actualBrands.size()),
				() -> assertEquals(NAME, actualBrands.get(0).getCompanyName())
		);
	}
	
	@Test
	public void testThatSaveBrand() {
		// GIVEN
		Brand brandToSave = getBrand();
		Brand brandSaved = BeanUtils.instantiateClass(Brand.class);
		BeanUtils.copyProperties(brandToSave, brandSaved);
		brandSaved.setBrandId(BRAND_ID);
		// WHEN
		when(brandService.saveBrand(any(Brand.class), any(String.class)))
				.thenReturn(brandSaved);
		Brand actualBrand = brandController.saveBrand(brandToSave);
		// THEN
		verify(validationsService, times(1)).isBrandValidToSave(any(Brand.class));
		verify(brandService, times(1)).saveBrand(any(Brand.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualBrand),
				() -> assertNotNull(actualBrand.getCreationDate()),
				() -> assertNotNull(actualBrand.getLastModifiedDate()),
				() -> assertEquals(BRAND_ID, actualBrand.getBrandId()),
				() -> assertEquals(NAME, actualBrand.getCompanyName()),
				() -> assertEquals("test", actualBrand.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingBrand() {
		// GIVEN
		Brand brandToSave = getBrand();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isBrandValidToSave(any(Brand.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> brandController.saveBrand(brandToSave));
		verify(validationsService,
				times(1)).isBrandValidToSave(any(Brand.class));
		verifyNoInteractions(brandService);
	}
	
	@Test
	public void testThatFailsUpdatingBrandDueToValidations() {
		// GIVEN
		Brand brandToSave = getBrand();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isBrandValidToUpdate(any(Brand.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> brandController.updateBrand(BRAND_ID, brandToSave));
		verify(validationsService,
				times(1)).isBrandValidToUpdate(any(Brand.class));
		verifyNoInteractions(brandService);
	}
	
	@Test
	public void testThatFailsUpdatingBrandDueToWrongIds() {
		// GIVEN
		Brand brandToUpdate = getBrand();
		brandToUpdate.setBrandId(3L);
		
		Brand brandFound = getBrand();
		brandFound.setBrandId(BRAND_ID);
		// WHEN
		when(brandService.getBrandById(any(Long.class))).thenReturn(Optional.of(brandFound));
		// THEN
		assertThrows(BadRequestException.class, () -> brandController.updateBrand(BRAND_ID, brandToUpdate));
		verify(validationsService, times(1)).isBrandValidToUpdate(any(Brand.class));
		verify(brandService, times(1)).getBrandById(any(Long.class));
		verifyNoMoreInteractions(brandService);
	}
	
	@Test
	public void testThatUpdateBrand() {
		// GIVEN
		Brand brandToUpdate = getBrand();
		brandToUpdate.setBrandId(BRAND_ID);
		
		Brand brandFound = getBrand();
		brandFound.setBrandId(BRAND_ID);
		
		Brand brandUpdated = BeanUtils.instantiateClass(Brand.class);
		BeanUtils.copyProperties(brandToUpdate, brandUpdated);
		brandUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(brandService.getBrandById(any(Long.class))).thenReturn(Optional.of(brandFound));
		when(brandService.updateBrand(any(Brand.class), any(Brand.class), any(String.class)))
				.thenReturn(brandUpdated);
		Brand actualBrand = brandController.updateBrand(BRAND_ID, brandToUpdate);
		// THEN
		verify(validationsService, times(1)).isBrandValidToUpdate(any(Brand.class));
		verify(brandService, times(1)).getBrandById(any(Long.class));
		verify(brandService, times(1))
				.updateBrand(any(Brand.class), any(Brand.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualBrand),
				() -> assertNotNull(actualBrand.getCreationDate()),
				() -> assertNotNull(actualBrand.getLastModifiedDate()),
				() -> assertEquals(BRAND_ID, actualBrand.getBrandId()),
				() -> assertEquals(NAME, actualBrand.getCompanyName()),
				() -> assertEquals(USER_TEST, actualBrand.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeleteBrand() {
		// GIVEN
		Brand brandFound = getBrand();
		brandFound.setBrandId(BRAND_ID);
		
		// WHEN
		when(brandService.getBrandById(any(Long.class))).thenReturn(Optional.of(brandFound));
		brandController.deleteBrand(BRAND_ID);
		// THEN
		verify(brandService, times(1)).getBrandById(any(Long.class));
		verify(brandService, times(1)).deleteBrand(any(Brand.class));
	}
	
	@Test
	public void testThatFailsDeletingBrand() {
		// GIVEN
		
		// WHEN
		when(brandService.getBrandById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> brandController.deleteBrand(BRAND_ID));
		verify(brandService, times(1)).getBrandById(any(Long.class));
		verifyNoMoreInteractions(brandService);
	}
}
