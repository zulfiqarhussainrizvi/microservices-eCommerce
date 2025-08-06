package com.product.service;

import java.util.List;
import java.util.Optional;

import com.product.entity.Product;

public interface ProductService {
	Product saveProduct(Product product);
    Optional<Product> getProductById(Long id);
    Product updateProduct(Product product);
    Product updateProduct(Long id, Product updatedProduct);
    List<Product> getAllProducts();
    Boolean deleteProduct(Long id);
    
}

