package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Product;
import com.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productService.getProducts();

		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);

		} else {

			return new ResponseEntity<List<Product>>(products, HttpStatus.ACCEPTED);
		}
	}

	// Get a product
	@GetMapping("{productid}")
	public ResponseEntity<Product> getProduct(@PathVariable("productid") Integer productId) {
		if (productService.isProductExist(productId)) {
			return new ResponseEntity<Product>(productService.getProduct(productId), HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
		}

	}

	// Search by Product Name
	@GetMapping("search/{productName}")
	public ResponseEntity<List<Product>> searchByProductName(@PathVariable("productName") String productName) {
		List<Product> products = productService.searchByProductName(productName);

		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);

		} else {

			return new ResponseEntity<List<Product>>(products, HttpStatus.ACCEPTED);
		}
	}

	// Search by greater Price
	@GetMapping("greaterprice/{price}")
	public ResponseEntity<List<Product>> searchPriceGreaterThan(@PathVariable("price") Integer price) {
		List<Product> products = productService.searchPriceGreaterThan(price);

		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);

		} else {

			return new ResponseEntity<List<Product>>(products, HttpStatus.ACCEPTED);
		}

	}

	// Search by less Price
	@GetMapping("lessprice/{price}")
	public ResponseEntity<List<Product>> searchPriceLessThan(@PathVariable("price") Integer price) {

		List<Product> products = productService.searchPriceLessThan(price);

		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);

		} else {

			return new ResponseEntity<List<Product>>(products, HttpStatus.ACCEPTED);
		}

		// return productService.searchPriceLessThan(price);
	}

	// search between price
	@GetMapping("between/{lprice}/{hprice}")
	public ResponseEntity<List<Product>> searchPriceBetween(@PathVariable("lprice") Integer lprice,
			@PathVariable("hprice") Integer hprice) {
		List<Product> products = productService.searchPriceBetween(lprice, hprice);
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);

		} else {

			return new ResponseEntity<List<Product>>(products, HttpStatus.ACCEPTED);
		}
	}

	// Delete a Product
	@DeleteMapping("{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) {
		if (productService.isProductExist(productId)) {
			productService.deleteProduct(productId);
			return new ResponseEntity<String>("Product Deleted Successfully  !!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product not Found !!", HttpStatus.NO_CONTENT);
		}

	}

	// Update the Product
	@PutMapping
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {

		if (productService.isProductExist(product.getProductId())) {
			productService.updateProduct(product);
			return new ResponseEntity<String>("Product Updated Successfully !!", HttpStatus.FOUND);
		} else {

			return new ResponseEntity<String>("Product Not Updated Successfully !!", HttpStatus.NOT_MODIFIED);
		}

	}

	// Saving a single Product
	@PostMapping
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		// System.out.println("Save Product Recieved: " + product);
		if (productService.isProductExist(product.getProductId())) {
			return new ResponseEntity<String>("Product Already Exist", HttpStatus.CONFLICT);
		} else {

			productService.saveProduct(product);
			return new ResponseEntity<String>("Product Saved Successfully", HttpStatus.CREATED);
		}
	}

}
