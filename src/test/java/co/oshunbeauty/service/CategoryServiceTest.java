package co.oshunbeauty.service;

import co.oshunbeauty.entity.Category;
import co.oshunbeauty.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getCategories;
import static co.oshunbeauty.resources.EntitiesMocks.getCategory;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CategoryServiceTest {
	
	@Mock
	private CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
	
	private CategoryService categoryService = new CategoryService(categoryRepository);
	
	private static final String NAME = "tinturas";
	private static final String USER_TEST = "testOshun";
	private static final String NAME_TO_UPDATE = "tinturas nuevas";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetCategoryByIdNotFound(){
		// Given
		Long categoryId = 1L;
		// When
		Mockito.when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Optional<Category> categoryFound = categoryService.getCategoryById(categoryId);
		// Then
		verify(categoryRepository, times(1)).findById(any(Long.class));
		assertTrue(categoryFound.isEmpty());
	}
	
	@Test
	public void testGetCategoryById() {
		// Given
		Long categoryId = 1L;
		Category categoryToFind = getCategory();
		categoryToFind.setCategoryId(categoryId);
		// When
		Mockito.when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryToFind));
		Optional<Category> categoryFound = categoryService.getCategoryById(categoryId);
		// Then
		verify(categoryRepository, times(1)).findById(any(Long.class));
		assertAll(
				() -> assertTrue(categoryFound.isPresent()),
				() -> assertEquals(categoryId, categoryFound.get().getCategoryId()),
				() -> assertEquals(NAME, categoryFound.get().getName())
		);
		assertTrue(categoryFound.isPresent());
	}
	
	@Test
	public void testToGetAllCategories() {
		// Given
		List<Category> categories = getCategories();
		// When
		Mockito.when(categoryRepository.findAll()).thenReturn(categories);
		List<Category> currentCategory = categoryService.getAllCategories();
		// Then
		verify(categoryRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentCategory),
				() -> assertEquals(4, currentCategory.size()),
				() -> assertEquals(NAME, currentCategory.get(0).getName())
		);
	}
	
	@Test
	public void testToGetCategoriesByName() {
		// Given
		List<Category> categories = getCategories().stream().filter(i -> i.getName().contains(NAME))
				.collect(Collectors.toList());
		// When
		Mockito.when(categoryRepository.findCategoriesByName(any(String.class))).thenReturn(categories);
		List<Category> currentCategory = categoryService.getCategoriesByName(NAME);
		// Then
		verify(categoryRepository, times(1)).findCategoriesByName(any(String.class));
		assertAll(
				() -> assertNotNull(currentCategory),
				() -> assertEquals(1, currentCategory.size()),
				() -> assertEquals(NAME, currentCategory.get(0).getName())
		);
	}
	
	@Test
	public void testToUpdateCategory(){
		// Given
		Category categoryToUpdate = getCategory();
		categoryToUpdate.setCategoryId(1L);
		categoryToUpdate.setName(NAME_TO_UPDATE);
		
		Category currentCategory = getCategory();
		currentCategory.setCategoryId(1L);
		
		Category categoryUpdated = BeanUtils.instantiateClass(Category.class);
		BeanUtils.copyProperties(categoryToUpdate, categoryUpdated);
		categoryUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(categoryUpdated);
		Category currentCategoryUpdated = categoryService.updateCategory(currentCategory, categoryToUpdate, USER_TEST);
		// Then
		verify(categoryRepository, times(1)).save(any(Category.class));
		assertAll(
				() -> assertNotNull(currentCategoryUpdated),
				() -> assertEquals(USER_TEST, currentCategoryUpdated.getLastModifiedUser()),
				() -> assertEquals(NAME_TO_UPDATE, currentCategoryUpdated.getName())
		);
	}
	
	@Test
	public void testToSaveCategory(){
		// Given
		Category categoryToSave = getCategory();
		// When
		Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(categoryToSave);
		Category currentCategory = categoryService.saveCategory(categoryToSave, USER_TEST);
		// Then
		verify(categoryRepository, times(1)).save(any(Category.class));
		assertAll(
				() -> assertNotNull(currentCategory),
				() -> assertEquals(USER_TEST, currentCategory.getCreationUser()),
				() -> assertEquals(NAME, currentCategory.getName())
		);
	}
}
