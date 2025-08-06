package com.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AmazonServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonServerApplication.class, args);
		System.out.println("Eureka Server is running...");
		
	}

}
