package co.oshunbeauty.controller;

import co.oshunbeauty.entity.Product;
import co.oshunbeauty.exception.BadRequestException;
import co.oshunbeauty.exception.ResourceNotFoundException;
import co.oshunbeauty.service.ProductService;
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
@RequestMapping("/rs/products")
@Slf4j
public class ProductController {
	
	private ValidationsService validationsService;
	private ProductService productService;
	
	@Autowired
	public ProductController(ValidationsService validationsService, ProductService productService) {
		this.validationsService = validationsService;
		this.productService = productService;
	}
	
	@GetMapping("/")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable final Long id) {
		Optional<Product> productFound = productService.getProductById(id);
		
		if(productFound.isEmpty()) {
			log.error("The product with id {} was not found.", id);
			throw new ResourceNotFoundException(getMessageForProductNotFoundException(id));
		}
		
		return productFound.get();
	}
	
	@GetMapping("/barcodes")
	public Product getProductByBarCode(@RequestParam final String barcode) {
		Optional<Product> productFound = productService.getProductByBarcode(barcode);
		
		if(productFound.isEmpty()) {
			log.error("The product with barcode {} was not found.", barcode);
			throw new ResourceNotFoundException(getMessageForProductNotFoundByBarcodeException(barcode));
		}
		
		return productFound.get();
	}
	
	@GetMapping("/names")
	public List<Product> getProductsByName(@RequestParam final String name) {
		return productService.getProductsByName(name);
	}
	
	@PostMapping
	public Product saveProduct(@RequestBody final Product product) {
		validationsService.isProductValidToSave(product);
		
		log.info("Saving new product with name {} and barcode {} by the user ", product.getName(), product.getBarcode(), "oshun");
		return productService.saveProduct(product, "oshun");
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable final Long id, @RequestBody final Product product) {
		validationsService.isProductValidToUpdate(product);
		Optional<Product> currentProductFound = productService.getProductById(id);
		validateProductsAreEqualsById(product, currentProductFound);
		
		log.info("Updating the product with name {} and barcode {} by the user {}", product.getName(), product.getBarcode(), "oshun");
		return productService.updateProduct(currentProductFound.get(), product, "oshun");
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable final Long id) {
		Optional<Product> currentProductFound = productService.getProductById(id);
		if(currentProductFound.isEmpty()) {
			log.error("The product with id {} was not found.", id);
			throw new BadRequestException(getMessageForProductNotFoundException(id));
		}
		
		Product currentProduct = currentProductFound.get();
		
		log.info("Deleting the product with barcode {} and name {}", currentProduct.getBarcode(), currentProduct.getName());
		productService.deleteProduct(currentProduct);
	}
	
	private void validateProductsAreEqualsById(Product product, Optional<Product> currentProductFound) {
		if(currentProductFound.isEmpty() || currentProductFound.get().getProductId() != product.getProductId() ) {
			log.error("When trying to update the product with id {}, the product sent had another id",
					product.getProductId());
			throw new BadRequestException(getErrorMessageBrandsAreNotSame(product));
		}
	}
	
	private String getErrorMessageBrandsAreNotSame(Product product) {
		return String.format("El producto con id {} y barcode {} que se intentó actualizar no corresponde con el especificado o no fue encontrado",
				product.getProductId(), product.getBarcode());
	}
	
	private String getMessageForProductNotFoundException(Long id) {
		return String.format("El producto con id %s no fue encontrado", id);
	}
	
	private String getMessageForProductNotFoundByBarcodeException(String barCode) {
		return String.format("El producto con el código de barras %s no fue encontrado", barCode);
	}
}
