package co.oshunbeauty.service;

import co.oshunbeauty.entity.Product;
import co.oshunbeauty.repository.ProductRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getProduct;
import static co.oshunbeauty.resources.EntitiesMocks.getProducts;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
	
	private ProductService productService = new ProductService(productRepository);
	
	private static final String NAME = "agua de rosas 250 ml";
	private static final String USER_TEST = "testOshun";
	
	@BeforeEach
	public void setUp() {
	}
	
	@Test
	public void testGetProductByIdNotFound(){
		// Given
		Long productId = 1L;
		// When
		Mockito.when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		Optional<Product> productFound = productService.getProductById(productId);
		// Then
		verify(productRepository, times(1)).findById(any(Long.class));
		assertTrue(productFound.isEmpty());
	}
	
	@Test
	public void testGetProductById() {
		// Given
		Long productId = 1L;
		Product productToFind = getProduct();
		productToFind.setProductId(productId);
		// When
		Mockito.when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productToFind));
		Optional<Product> productFound = productService.getProductById(productId);
		// Then
		verify(productRepository, times(1)).findById(any(Long.class));
		assertAll(
				() -> assertTrue(productFound.isPresent()),
				() -> assertEquals(productId, productFound.get().getProductId()),
				() -> assertEquals(NAME, productFound.get().getName())
		);
	}
	
	@Test
	public void testToGetAllProducts() {
		// Given
		List<Product> products = getProducts();
		// When
		Mockito.when(productRepository.findAll()).thenReturn(products);
		List<Product> currentProducts = productService.getAllProducts();
		// Then
		verify(productRepository, times(1)).findAll();
		assertAll(
				() -> assertNotNull(currentProducts),
				() -> assertEquals(4, currentProducts.size()),
				() -> assertEquals(NAME, currentProducts.get(0).getName())
		);
	}
	
	@Test
	public void testToGetProductsByBarcode() {
		// Given
		String barCode = "barCode test";
		Long productId = 1L;
		Product productToFind = getProduct();
		productToFind.setProductId(productId);
		productToFind.setBarcode(barCode);
		List<Product> productsToFind = new LinkedList<>();
		productsToFind.add(productToFind);
		// When
		Mockito.when(productRepository.findProductByBarcode(any(String.class))).thenReturn(productsToFind);
		List<Product> currentProduct = productService.getProductByBarcode(barCode);
		// Then
		verify(productRepository, times(1)).findProductByBarcode(any(String.class));
		assertAll(
				() -> assertTrue(!currentProduct.isEmpty()),
				() -> assertEquals(NAME, currentProduct.get(0).getName()),
				() -> assertEquals("barCode test", currentProduct.get(0).getBarcode())
		);
	}
	
	@Test
	public void testToGetProductsByName() {
		// Given
		List<Product> products = getProducts().stream().filter(i -> i.getName().contains(NAME))
				.collect(Collectors.toList());
		// When
		Mockito.when(productRepository.findProductsByName(any(String.class))).thenReturn(products);
		List<Product> currentProducts = productService.getProductsByName(NAME);
		// Then
		verify(productRepository, times(1)).findProductsByName(any(String.class));
		assertAll(
				() -> assertNotNull(currentProducts),
				() -> assertEquals(1, currentProducts.size()),
				() -> assertEquals(NAME, currentProducts.get(0).getName())
		);
	}
	
	@Test
	public void testToUpdateProduct(){
		// Given
		Product productToUpdate = getProduct();
		productToUpdate.setProductId(1L);
		productToUpdate.setName("agua de rosas 500 ml");
		
		Product currentProduct = getProduct();
		currentProduct.setProductId(1L);
		
		Product productUpdated = BeanUtils.instantiateClass(Product.class);
		BeanUtils.copyProperties(productToUpdate, productUpdated);
		productUpdated.setLastModifiedUser(USER_TEST);
		
		// When
		Mockito.when(productRepository.save(any(Product.class))).thenReturn(productUpdated);
		Product currentProductUpdated = productService.updateProduct(currentProduct, productToUpdate, USER_TEST);
		// Then
		verify(productRepository, times(1)).save(any(Product.class));
		assertAll(
				() -> assertNotNull(currentProductUpdated),
				() -> assertEquals(USER_TEST, currentProductUpdated.getLastModifiedUser()),
				() -> assertEquals("agua de rosas 500 ml", currentProductUpdated.getName())
		);
	}
	
	@Test
	public void testToSaveProduct(){
		// Given
		Product productToSave = getProduct();
		// When
		Mockito.when(productRepository.save(any(Product.class))).thenReturn(productToSave);
		Product currentProduct = productService.saveProduct(productToSave, USER_TEST);
		// Then
		verify(productRepository, times(1)).save(any(Product.class));
		assertAll(
				() -> assertNotNull(currentProduct),
				() -> assertEquals(USER_TEST, currentProduct.getCreationUser()),
				() -> assertEquals(NAME, currentProduct.getName())
		);
	}
}
