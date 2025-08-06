package com.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.entity.Product;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

	@Override
	public Product saveProduct(Product product) {
		return repository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		
		return repository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return repository.findAll();
	}

	@Override
	public Product updateProduct(Long id, Product updatedProduct) {
		Product existingProduct = repository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Product not found"));

		    existingProduct.setName(updatedProduct.getName());
		    existingProduct.setPrice(updatedProduct.getPrice());		    

		    return repository.save(existingProduct);
	}

	@Override
    public Boolean deleteProduct(Long id) {
        Optional<Product> productOpt = repository.findById(id);
        if (productOpt.isPresent()) {
        	repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}