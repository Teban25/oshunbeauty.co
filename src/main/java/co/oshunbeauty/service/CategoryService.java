package co.oshunbeauty.service;

import co.oshunbeauty.entity.Category;
import co.oshunbeauty.repository.CategoryRepository;
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
public class CategoryService {
	
	private Set<String> ignoredCategoryFields = new HashSet<>(Arrays.asList("categoryId", "products"));
	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		ignoredCategoryFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public Category saveCategory(Category category, String user) {
		category.setCreationDate(ZonedDateTime.now(ZONE_ID));
		category.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		category.setCreationUser(user);
		category.setLastModifiedUser(user);
		
		return categoryRepository.save(category);
	}
	
	public Category updateCategory(Category currentCategory, Category categorySent, String user) {
		BeanUtils.copyProperties(categorySent, currentCategory, ignoredCategoryFields.stream().toArray(String[]::new));
		
		currentCategory.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentCategory.setLastModifiedUser(user);
		
		return categoryRepository.save(currentCategory);
	}
	
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}
	
	public Optional<Category> getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}
	
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	public List<Category> getCategoriesByName(String name) {
		return categoryRepository.findCategoriesByName(name.toUpperCase());
	}
}
