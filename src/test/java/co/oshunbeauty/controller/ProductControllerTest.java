package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Product;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.ProductService;
import co.oshunbeauty.validation.ValidationsService;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import static co.oshunbeauty.resources.EntitiesMocks.getProduct;
import static co.oshunbeauty.resources.EntitiesMocks.getProducts;
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
public class ProductControllerTest {
	
	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductService productService;
	
	@Mock
	ValidationsService validationsService;
	
	private static final Long PRODUCT_ID = 1L;
	private static final String NAME = "agua de rosas 250 ml";
	private static final String USER_TEST = "oshunTest";
	private static final String BAR_CODE = "barCode test";
	
	@Test
	public void testThatGetProductById() {
		// GIVEN
		Product productToFind = getProduct();
		productToFind.setProductId(PRODUCT_ID);
		// WHEN
		when(productService.getProductById(any(Long.class))).thenReturn(Optional.of(productToFind));
		Product actualProduct = productController.getProductById(PRODUCT_ID);
		// THEN
		verify(productService, times(1)).getProductById(any(Long.class));
		assertAll(
				() -> assertNotNull(actualProduct),
				() -> assertEquals(PRODUCT_ID, actualProduct.getProductId()),
				() -> assertEquals(NAME, actualProduct.getName())
		);
	}
	
	@Test
	public void testThatNotFoundGetProductById() {
		// GIVEN
		
		// WHEN
		when(productService.getProductById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> productController.getProductById(PRODUCT_ID));
		verify(productService, times(1)).getProductById(any(Long.class));
	}
	
	@Test
	public void testThatGetAllProducts() {
		// GIVEN
		List<Product> productsToFind = getProducts();
		// WHEN
		when(productService.getAllProducts()).thenReturn(productsToFind);
		List<Product> actualProducts = productController.getAllProducts();
		// THEN
		verify(productService, times(1)).getAllProducts();
		assertAll(
				() -> assertNotNull(actualProducts),
				() -> assertEquals(4, actualProducts.size()),
				() -> assertEquals(NAME, actualProducts.get(0).getName())
		);
	}
	
	@Test
	public void testThatGetProductsByName() {
		// GIVEN
		List<Product> productsToFind = getProducts().stream().filter(i -> i.getName().contains(NAME))
				.collect(Collectors.toList());
		// WHEN
		when(productService.getProductsByName(any(String.class))).thenReturn(productsToFind);
		List<Product> actualProducts = productController.getProductsByName(NAME);
		// THEN
		verify(productService, times(1)).getProductsByName(any(String.class));
		assertAll(
				() -> assertNotNull(actualProducts),
				() -> assertEquals(1, actualProducts.size()),
				() -> assertEquals(NAME, actualProducts.get(0).getName())
		);
	}
	
	@Test
	public void testThatGetProductByBarCode() {
		// GIVEN
		String barCode = BAR_CODE;
		Product productToFind = getProduct();
		productToFind.setBarcode(BAR_CODE);
		List<Product> productsToFind = new LinkedList<>();
		productsToFind.add(productToFind);
		// WHEN
		when(productService.getProductByBarcode(any(String.class))).thenReturn(productsToFind);
		List<Product> actualProduct = productController.getProductByBarCode(barCode);
		// THEN
		verify(productService, times(1)).getProductByBarcode(any(String.class));
		assertAll(
				() -> assertNotNull(actualProduct),
				() -> assertEquals(barCode, actualProduct.get(0).getBarcode())
		);
	}
	
	@Test
	public void testThatNotFoundGetProductByBarCode() {
		// GIVEN
		
		// WHEN
		when(productService.getProductByBarcode(any(String.class))).thenReturn(new LinkedList<>());
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> productController.getProductByBarCode(BAR_CODE));
		verify(productService, times(1)).getProductByBarcode(any(String.class));
	}
	
	@Test
	public void testThatSaveProduct() {
		// GIVEN
		Product productToSave = getProduct();
		Product productSaved = BeanUtils.instantiateClass(Product.class);
		BeanUtils.copyProperties(productToSave, productSaved);
		productSaved.setProductId(PRODUCT_ID);
		// WHEN
		when(productService.saveProduct(any(Product.class), any(String.class)))
				.thenReturn(productSaved);
		Product actualProduct = productController.saveProduct(productToSave);
		// THEN
		verify(validationsService, times(1)).isProductValidToSave(any(Product.class));
		verify(productService, times(1)).saveProduct(any(Product.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualProduct),
				() -> assertNotNull(actualProduct.getCreationDate()),
				() -> assertNotNull(actualProduct.getLastModifiedDate()),
				() -> assertEquals(PRODUCT_ID, actualProduct.getProductId()),
				() -> assertEquals(NAME, actualProduct.getName()),
				() -> assertEquals("test", actualProduct.getCreationUser())
		);
	}
	
	@Test
	public void testThatFailsSavingProduct() {
		// GIVEN
		Product productToSave = getProduct();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isProductValidToSave(any(Product.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> productController.saveProduct(productToSave));
		verify(validationsService,
				times(1)).isProductValidToSave(any(Product.class));
		verifyNoInteractions(productService);
	}
	
	@Test
	public void testThatFailsUpdatingProductDueToValidations() {
		// GIVEN
		Product productToSave = getProduct();
		// WHEN
		doThrow(BadRequestException.class).when(validationsService).isProductValidToUpdate(any(Product.class));
		// THEN
		assertThrows(BadRequestException.class,
				() -> productController.updateProduct(PRODUCT_ID, productToSave));
		verify(validationsService,
				times(1)).isProductValidToUpdate(any(Product.class));
		verifyNoInteractions(productService);
	}
	
	@Test
	public void testThatFailsUpdatingProductDueToWrongIds() {
		// GIVEN
		Product productToUpdate = getProduct();
		productToUpdate.setProductId(3L);
		
		Product productFound = getProduct();
		productFound.setProductId(PRODUCT_ID);
		// WHEN
		when(productService.getProductById(any(Long.class))).thenReturn(Optional.of(productFound));
		// THEN
		assertThrows(BadRequestException.class, () -> productController.updateProduct(PRODUCT_ID, productToUpdate));
		verify(validationsService, times(1)).isProductValidToUpdate(any(Product.class));
		verify(productService, times(1)).getProductById(any(Long.class));
		verifyNoMoreInteractions(productService);
	}
	
	@Test
	public void testThatUpdateProduct() {
		// GIVEN
		Product productToUpdate = getProduct();
		productToUpdate.setProductId(PRODUCT_ID);
		productToUpdate.setName("agua de rosas 500 ml");
		
		Product productFound = getProduct();
		productFound.setProductId(PRODUCT_ID);
		
		Product productUpdated = BeanUtils.instantiateClass(Product.class);
		BeanUtils.copyProperties(productToUpdate, productUpdated);
		productUpdated.setLastModifiedUser(USER_TEST);
		// WHEN
		when(productService.getProductById(any(Long.class))).thenReturn(Optional.of(productFound));
		when(productService.updateProduct(any(Product.class), any(Product.class), any(String.class)))
				.thenReturn(productUpdated);
		Product actualProduct = productController.updateProduct(PRODUCT_ID, productToUpdate);
		// THEN
		verify(validationsService, times(1)).isProductValidToUpdate(any(Product.class));
		verify(productService, times(1)).getProductById(any(Long.class));
		verify(productService, times(1))
				.updateProduct(any(Product.class), any(Product.class), any(String.class));
		assertAll(
				() -> assertNotNull(actualProduct),
				() -> assertNotNull(actualProduct.getCreationDate()),
				() -> assertNotNull(actualProduct.getLastModifiedDate()),
				() -> assertEquals(PRODUCT_ID, actualProduct.getProductId()),
				() -> assertEquals("agua de rosas 500 ml", actualProduct.getName()),
				() -> assertEquals(USER_TEST, actualProduct.getLastModifiedUser())
		);
	}
	
	@Test
	public void testThatDeleteProduct() {
		// GIVEN
		Product productFound = getProduct();
		productFound.setProductId(PRODUCT_ID);
		
		// WHEN
		when(productService.getProductById(any(Long.class))).thenReturn(Optional.of(productFound));
		productController.deleteProduct(PRODUCT_ID);
		// THEN
		verify(productService, times(1)).getProductById(any(Long.class));
		verify(productService, times(1)).deleteProduct(any(Product.class));
	}
	
	@Test
	public void testThatFailsDeletingProduct() {
		// GIVEN
		
		// WHEN
		when(productService.getProductById(any(Long.class))).thenReturn(Optional.empty());
		// THEN
		assertThrows(BadRequestException.class, () -> productController.deleteProduct(PRODUCT_ID));
		verify(productService, times(1)).getProductById(any(Long.class));
		verifyNoMoreInteractions(productService);
	}
}
