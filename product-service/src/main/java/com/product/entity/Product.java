package com.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @NotBlank(message = "Product name must not be empty")
	    private String name;
	    
	    @Positive(message = "Product price must be greater than zero")
	    private double price;
	    
	    @NotBlank(message = "Product Category not null")
	    private String category;
	    
	    @Lob	    
	    @Column(length = 500) // sets max length to 500 characters
	    private String description;
	    
	

}
