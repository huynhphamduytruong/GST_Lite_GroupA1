package com.gstlite.mobilestore.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.gstlite.mobilestore.entites.*;
import com.gstlite.mobilestore.repositories.*;
import com.gstlite.mobilestore.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/list")
	public List<Product> getAllUsers() {
		return productRepository.findAll();
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long productId) throws ResourceNotFoundException {
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("ProductId not found: " + productId));
		
		return ResponseEntity.ok().body(product);
	}
	
	@PostMapping("/add")
	public Product create(@Validated @RequestBody Product product) {
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
