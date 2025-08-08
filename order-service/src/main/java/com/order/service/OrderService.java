package com.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dto.ProductDto;
import com.order.entity.Order;
import com.order.feignclient.ProductClient;
import com.order.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductClient productClient;
	
	
	@Retry(name = "PRODUCT-SERVICE")
    @CircuitBreaker(name = "PRODUCT-SERVICE", fallbackMethod = "fallbackProduct")
	public Order placeOrder(Long productId, Order order) {		
		ProductDto productDto=productClient.getProductByIdForFeign(productId);
		double price = productDto.getPrice();		
		order.setProductId(productId);
		order.setQuantity(order.getQuantity());
		order.setTotalPrice(price*order.getQuantity());
		return orderRepository.save(order);
		
	}
	
	public Order fallbackProduct(Long productId, Order order, Throwable t) {
        // Log the error if needed
        System.out.println("Fallback called due to: " + t.getMessage());

        // Optionally save a dummy order or return null
        order.setProductId(productId);
        order.setTotalPrice(0.0);        
        return order;
    }
	

}
