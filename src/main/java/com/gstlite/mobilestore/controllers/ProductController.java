package com.gstlite.mobilestore.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.gstlite.mobilestore.entites.*;
import com.gstlite.mobilestore.repositories.*;
import com.gstlite.mobilestore.service.FileStorageService;
import com.gstlite.mobilestore.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;
    
	@GetMapping("/list")
	public List<Product> getAllUsers() {
		return productRepository.findAll();
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long productId) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("ProductId not found: " + productId));
		
		return ResponseEntity.ok().body(product);
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST,
	        consumes = {"multipart/form-data", "application/json"})
	public Product create(@Validated @ModelAttribute Product product, @RequestPart(value="image", required = true) final MultipartFile image) {
		String fileName = fileStorageService.storeFile(image);
		UriComponents file = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/")
                .path(fileName)
                .build();
		product.setImageFile(file.toUriString()); // Change to full URL instead of relative path for remote usage
		return productRepository.save(product);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> update(@PathVariable(value = "id") Long productId,
			@Validated @RequestBody Product productDetails) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("ProductId not found:" + productId));

		product.setProductName(productDetails.getProductName());
		product.setUnitPrice(productDetails.getUnitPrice());
		product.setUnitInStock(productDetails.getUnitInStock());
		product.setDescription(productDetails.getDescription());
		product.setManufacture(productDetails.getManufacture());
		product.setCategory(productDetails.getCategory());
		product.setCondition(productDetails.getCondition());		
		product.setImageFile(productDetails.getImageFile());
		
		final Product updateProduct = productRepository.save(product);
		return ResponseEntity.ok(updateProduct);
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> delete(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("ProductId not found:" + productId));
		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
