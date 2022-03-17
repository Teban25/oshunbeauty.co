package co.oshunbeauty.service;

import co.oshunbeauty.entity.Product;
import co.oshunbeauty.repository.ProductRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static co.oshunbeauty.constants.Constants.DateConstants.ZONE_ID;
import static co.oshunbeauty.constants.Constants.ServicesConstants.IGNORED_STANDARD_FIELDS;

@Service
@Slf4j
public class ProductService {
	
	private Set<String> ignoredProductFields = new HashSet<>(Arrays.asList("productId"));
	private ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
		ignoredProductFields.addAll(IGNORED_STANDARD_FIELDS);
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}
	
	public List<Product> getProductsByName(String name) {
		return productRepository.findProductsByName(name);
	}
	
	public Optional<Product> getProductByBarcode(String barCode) {
		return productRepository.findProductByBarcode(barCode);
	}
	
	public Product saveProduct(Product product, String user) {
		product.setCreationDate(ZonedDateTime.now(ZONE_ID));
		product.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		product.setCreationUser(user);
		product.setLastModifiedUser(user);
		
		return productRepository.save(product);
	}
	
	public Product updateProduct(Product currentProduct, Product productSent, String user) {
		BeanUtils.copyProperties(productSent, currentProduct, ignoredProductFields.stream().toArray(String[]::new));
		
		currentProduct.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentProduct.setLastModifiedUser(user);
		
		return productRepository.save(currentProduct);
	}
	
	public Product updateProductFromExcel(Product currentProduct, String user) {
		currentProduct.setLastModifiedDate(ZonedDateTime.now(ZONE_ID));
		currentProduct.setLastModifiedUser(user);
		
		return productRepository.save(currentProduct);
	}
	
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}
	
	public Optional<Product> getProductByNameAndBrand(String name, Long brandId) {
		return productRepository.findProductByNameAndBrand(name, brandId);
	}
}
