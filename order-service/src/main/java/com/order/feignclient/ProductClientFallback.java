package com.order.feignclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.order.dto.ProductDto;

@Component
public class ProductClientFallback implements ProductClient {
	
	private static final Logger log = LoggerFactory.getLogger(ProductClientFallback.class);
	@Override
    public ProductDto getProductByIdForFeign(Long id) {
        log.error("Fallback triggered: Unable to fetch product with ID {}", id);
        return new ProductDto(id, "Product Not Available", 0.0,"Unavailable","Unavailable");
    }
}    