package com.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.ProductDto;
import com.order.entity.Order;
import com.order.feignclient.ProductClient;
import java.util.logging.Logger;
import com.order.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	static Logger log = Logger.getLogger(OrderController.class.getName());

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductClient productClient;

	
	//If you want to avoid calling productClient in controller, let OrderService handle all FeignClient communication
	@PostMapping
	@Retry(name = "PRODUCT-SERVICE")
	@CircuitBreaker(name = "PRODUCT-SERVICE", fallbackMethod = "fallbackProduct")
	public String createOrder(@RequestBody Order order) {
		ProductDto product = productClient.getProductByIdForFeign(order.getProductId());
		orderService.placeOrder(product.getId(), order);
		return "Order placed for: " + product.getName();
	}

	// ✅ Fallback method MUST match parameters of createOrder + Throwable
	public String fallbackProduct(Order order, Throwable t) {
		log.warning("Fallback triggered: " + t.getMessage());
		return "Product service is down. Order not placed.";
	}

}
