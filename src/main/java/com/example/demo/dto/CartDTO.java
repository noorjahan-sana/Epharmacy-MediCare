package com.example.demo.dto;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Product;
@Component
public class CartDTO {
	private Integer cartId;
	private String customerEmailId;
	private Set<ProductDTO> products;
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public Set<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductDTO> products) {
		this.products = products;
	}
	
}
