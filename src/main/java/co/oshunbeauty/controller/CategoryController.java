package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Category;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.CategoryService;
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
@RequestMapping("/rs/categories")
@Slf4j
public class CategoryController {
	
	private ValidationsService validationsService;
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(ValidationsService validationsService, CategoryService categoryService) {
		this.validationsService = validationsService;
		this.categoryService = categoryService;
	}
	
	@GetMapping("/")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	
	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable final Long id) {
		Optional<Category> categoryFound = categoryService.getCategoryById(id);
		
		if(categoryFound.isEmpty()) {
			throw new ResourceNotFoundException(getMessageForCategoryNotFoundException(id));
		}
		
		return categoryFound.get();
	}
	
	@GetMapping("/names")
	public List<Category> getCategoriesByName(@RequestParam final String name) {
		return categoryService.getCategoriesByName(name);
	}
	
	@PostMapping
	public Category saveCategory(@RequestBody final Category category) {
		validationsService.isCategoryValidToSave(category);
		
		log.info("Saving new category with name {} by the user {}", category.getName(), "oshun");
		return categoryService.saveCategory(category, "oshun");
	}
	
	@PutMapping("/{id}")
	public Category updateCategory(@PathVariable final Long id, @RequestBody final Category category) {
		validationsService.isCategoryValidToUpdate(category);
		
		Optional<Category> currentCategoryFound = categoryService.getCategoryById(id);
		validateCategoriesAreEqualsById(category, currentCategoryFound);
		
		log.info("Updating the category with name {} by the user {}", category.getName(), "oshun");
		return categoryService.updateCategory(currentCategoryFound.get(), category, "oshun");
	}
	
	@DeleteMapping
	public void deleteCategory(@PathVariable Long id) {
		Optional<Category> currentCategory = categoryService.getCategoryById(id);
		
		if(currentCategory.isEmpty()) {
			log.error("The category with id {} was not found.", id);
			throw new BadRequestException(getMessageForCategoryNotFoundException(id));
		}
		
		log.info("Deleting the category with name {} by the user {}", currentCategory.get().getName(), "oshun");
		categoryService.deleteCategory(currentCategory.get());
	}
	
	private void validateCategoriesAreEqualsById(Category category, Optional<Category> currentCategoryFound) {
		if(currentCategoryFound.isEmpty() || currentCategoryFound.get().getCategoryId() != category.getCategoryId() ) {
			log.error("When trying to update category with id {}, the category sent had another id",
					category.getCategoryId());
			throw new BadRequestException(getErrorMessageCategoriesAreNotSame(category));
		}
	}
	
	private String getErrorMessageCategoriesAreNotSame(Category category) {
		return String.format("La categoria con id %s no fue encontrada o no corresponde a la " +
				"ingresada", category.getCategoryId());
	}
	
	private String getMessageForCategoryNotFoundException(Long id) {
		return String.format("La categoria con id %s no fue encontrada", id);
	}
}
