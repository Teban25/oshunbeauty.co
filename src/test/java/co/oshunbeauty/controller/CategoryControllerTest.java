package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Category;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.CategoryService;
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

import static co.oshunbeauty.resources.EntitiesMocks.getCategories;
import static co.oshunbeauty.resources.EntitiesMocks.getCategory;
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
public class CategoryControllerTest {
	
	@InjectMocks
	CategoryController categoryController;
	
	@Mock
	CategoryService categoryService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final Long CATEGORY_ID = 1L;
	private static final String NAME = "tinturas";
	private static final String USER_TEST = "oshunTest";
	
	@Test
	public void testThatGetCategoryById() {
		// GIVEN
		Category categoryToFind = getCategory();
		categoryToFind.setCategoryId(CATEGORY_ID);
		// WHEN
		when(categoryService.getCategoryById(any(Long.class))).thenReturn(Optional.of(categoryToFind));
		Category actualCategory = categoryController.getCategoryById(CATEGORY_ID);
		// THEN
		verify(categoryService, times(1)).getCategoryById(any(Long.class));
		assertAll(
				() -> assertNotNull(actualCategory),
				() -> assertEquals(CATEGORY_ID, actualCategory.getCategoryId()),
				() -> assertEquals(NAME, actualCategory.getName())
		);
	}
	
	@Test
	public void testThatNotFoundGetCategoryById() {
		// GIVEN

		// WHEN
		when(categoryService.getCategoryById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> categoryController.getCategoryById(CATEGORY_ID));
		verify(categoryService, times(1)).getCategoryById(any(Long.class));
	}
	
	@Test
	public void testThatGetAllCategories() {
		// GIVEN
		List<Category> categoriesToFind = getCategories();
		// WHEN
		when(categoryService.getAllCategories()).thenReturn(categoriesToFind);
		List<Category> actualCategories = categoryController.getAllCategories();
		// THEN
		verify(categoryService, times(1)).getAllCategories();
		assertAll(
				() -> assertNotNull(actualCategories),
				() -> assertEquals(4, actualCategories.size()),
				() -> assertEquals(NAME, actualCategories.get(0).getName())
		);
	}
	
	@Test
	public void testThatGetCategoriesByName() {
		// GIVEN
		List<Category> categoriesToFind = getCategories().stream().filter(i -> i.getName().contains(NAME))
				.collect(Collectors.toList());;
		// WHEN
		when(categoryService.getCategoriesByName(any(String.class))).thenReturn(categoriesToFind);
		List<Category> actualCategories = categoryController.getCategoriesByName(NAME);
		// THEN
		verify(categoryService, times(1)).getCategoriesByName(any(String.class));
		assertAll(
				() -> assertNotNull(actualCategories),
				() -> assertEquals(1, actualCategories.size()),
				() -> assertEquals(NAME, actualCategories.get(0).getName())
		);
	}
	
	@Test
	public void testThatSaveCategory() {
		// GIVEN
		Category categoryToSave = getCategory();
		Category categorySaved = BeanUtils.instantiateClass(Category.class);
		BeanUtils.copyProperties(categoryToSave, categorySaved);
		categorySaved.setCategoryId(CATEGORY_ID);
		// WHEN
		when(categoryService.saveCategory(any(Category.class), any(String.class)))
				.thenReturn(categorySaved);
		Category actualCategory = categoryController.saveCategory(categoryToSave);
		// THEN
		verify(validationsService, times(1)).isCategoryValidToSave(any(Category.class));
		verify(categoryService, times(1)).saveCategory(any(Category.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualCategory),
				() -> assertNotNull(actualCategory.getCreationDate()),
				() -> assertNotNull(actualCategory.getLastModifiedDate()),
				() -> assertEquals(CATEGORY_ID, actualCategory.getCategoryId()),
				() -> assertEquals(NAME, actualCategory.getName()),
				() -> assertEquals("test", actualCategory.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingCategory() {
		// GIVEN
		Category categoryToSave = getCategory();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isCategoryValidToSave(any(Category.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> categoryController.saveCategory(categoryToSave));
		verify(validationsService,
				times(1)).isCategoryValidToSave(any(Category.class));
		verifyNoInteractions(categoryService);
	}
	
	@Test
	public void testThatFailsUpdatingCategoryDueToValidations() {
		// GIVEN
		Category categoryToSave = getCategory();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isCategoryValidToUpdate(any(Category.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> categoryController.updateCategory(CATEGORY_ID, categoryToSave));
		verify(validationsService,
				times(1)).isCategoryValidToUpdate(any(Category.class));
		verifyNoInteractions(categoryService);
	}
	
	@Test
	public void testThatFailsUpdatingCategoryDueToWrongIds() {
		// GIVEN
		Category categoryToUpdate = getCategory();
		categoryToUpdate.setCategoryId(3L);
		
		Category categoryFound = getCategory();
		categoryFound.setCategoryId(CATEGORY_ID);
		// WHEN
		when(categoryService.getCategoryById(any(Long.class))).thenReturn(Optional.of(categoryFound));
		// THEN
		assertThrows(BadRequestException.class, () -> categoryController.updateCategory(CATEGORY_ID, categoryToUpdate));
		verify(validationsService, times(1)).isCategoryValidToUpdate(any(Category.class));
		verify(categoryService, times(1)).getCategoryById(any(Long.class));
		verifyNoMoreInteractions(categoryService);
	}
	
	@Test
	public void testThatUpdateCategory() {
		// GIVEN
		Category categoryToUpdate = getCategory();
		categoryToUpdate.setCategoryId(CATEGORY_ID);
		
		Category categoryFound = getCategory();
		categoryFound.setCategoryId(CATEGORY_ID);
		
		Category categoryUpdated = BeanUtils.instantiateClass(Category.class);
		BeanUtils.copyProperties(categoryToUpdate, categoryUpdated);
		categoryUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(categoryService.getCategoryById(any(Long.class))).thenReturn(Optional.of(categoryFound));
		when(categoryService.updateCategory(any(Category.class), any(Category.class), any(String.class)))
				.thenReturn(categoryUpdated);
		Category actualCategory = categoryController.updateCategory(CATEGORY_ID, categoryToUpdate);
		// THEN
		verify(validationsService, times(1)).isCategoryValidToUpdate(any(Category.class));
		verify(categoryService, times(1)).getCategoryById(any(Long.class));
		verify(categoryService, times(1))
				.updateCategory(any(Category.class), any(Category.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualCategory),
				() -> assertNotNull(actualCategory.getCreationDate()),
				() -> assertNotNull(actualCategory.getLastModifiedDate()),
				() -> assertEquals(CATEGORY_ID, actualCategory.getCategoryId()),
				() -> assertEquals(NAME, actualCategory.getName()),
				() -> assertEquals(USER_TEST, actualCategory.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeleteCategory() {
		// GIVEN
		Category categoryFound = getCategory();
		categoryFound.setCategoryId(CATEGORY_ID);
		
		// WHEN
		when(categoryService.getCategoryById(any(Long.class))).thenReturn(Optional.of(categoryFound));
		categoryController.deleteCategory(CATEGORY_ID);
		// THEN
		verify(categoryService, times(1)).getCategoryById(any(Long.class));
		verify(categoryService, times(1)).deleteCategory(any(Category.class));
	}
	
	@Test
	public void testThatFailsDeletingCategory() {
		// GIVEN
		
		// WHEN
		when(categoryService.getCategoryById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> categoryController.deleteCategory(CATEGORY_ID));
		verify(categoryService, times(1)).getCategoryById(any(Long.class));
		verifyNoMoreInteractions(categoryService);
	}
}
