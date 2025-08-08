package com.order.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.dto.ProductDto;

@FeignClient(name = "PRODUCT-SERVICE", fallback = ProductClientFallback.class)
public interface ProductClient {

	    @GetMapping("/api/products/view/{id}")
	    ProductDto getProductByIdForFeign(@PathVariable("id") Long id);
	}

