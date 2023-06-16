package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.MedicineDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class CartServiceImp implements CartService{
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired 
	private MedicineService medicineService;
	@Override
	public String addMedicinesToCart(CartDTO cart) throws EpharmacyException {
		
		Set<Product> products = new HashSet<>();
		for(ProductDTO productDTO : cart.getProducts()) {
			Product product = new Product();
			product.setMedicineId(productDTO.getMedicineDTO().getMedicineId());
			product.setQuantity(productDTO.getQuantity());
			Integer id =productRepository.save(product).getProductId();
			product.setProductId(id);
			products.add(product);
		}
	
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(cart.getCustomerEmailId());
		if(optional.isEmpty()) {
			Cart newCart = new Cart();
			newCart.setCustomerEmailId(cart.getCustomerEmailId());
			newCart.setProducts(products);
			cartRepository.save(newCart);
		}
		else {
			Cart existing = optional.get();
			for(Product toadd: products) {
				Boolean alreadyPresent = false;
				for(Product existingProducts: existing.getProducts()) {
					if(existingProducts.getMedicineId().equals(toadd.getMedicineId())) {
						{
							
							existingProducts.setQuantity(toadd.getQuantity()+existingProducts.getQuantity());
							productRepository.delete(toadd);
							alreadyPresent = true;
						}
					}
				}
				if(alreadyPresent==false) {
					existing.getProducts().add(toadd);
				}
			}
		}
		return "Products are successfully added to the cart ";		
		
	}

	@Override
	public Set<ProductDTO> getMedicinesFromCart(String email) throws EpharmacyException {
		// TODO Auto-generated method stub
	
		// check for cart existance
		
				Optional<Cart> op = cartRepository.findByCustomerEmailId(email);
				
				Cart c = op.orElseThrow(()-> new EpharmacyException("Cart doesnt exists"));
				
				//check for product existance
				
				if(c.getProducts().isEmpty()) {
					
					throw new EpharmacyException("Products are not available");
				}
				
				 // output set
				
				Set<ProductDTO> response = new HashSet<>();
				
				// input set
				
				Set<Product> s = c.getProducts();
				
				for (Product prod : s) {
					
					ProductDTO d  = new ProductDTO();
					
					d.setProductId(prod.getProductId());
					
					d.setQuantity(prod.getQuantity());
					
					// u r calling medicine service to get the details about a medicine
					
					MedicineDTO m =medicineService.getMedicineById(prod.getMedicineId());
					
					d.setMedicineDTO(m);
					
					response.add(d);
					
					
				}
				
				return response;
	}

	@Override
	public String modifyQuantityOfMedicinesInCart(String email, Integer productId, Integer quantity)
			throws EpharmacyException {
		// TODO Auto-generated method stub
		// cart existance??
		
				Optional<Cart> c = cartRepository.findByCustomerEmailId(email);
				
				Cart cr = c.orElseThrow(()->new EpharmacyException("Cart does not exists"));
				
				// products existance??
				
				if(cr.getProducts().isEmpty()) {
					
					throw new EpharmacyException("Products doesnt exists");
				}
				
				Product required = null;
				
				Set<Product> p = cr.getProducts();
				
				for (Product product : p) {
					
					if(product.getProductId().equals(productId)) {
						
						required = product;
						
						break;
					}
				}
				
				if(required == null) {
					
					throw new EpharmacyException("No such product exists");
					
				}
				
				required.setQuantity(quantity);
		return "QUANTITY MODIFIED";
	}

	@Override
	public String deleteMedicineFromCart(String email, Integer productId) throws EpharmacyException {
		// TODO Auto-generated method stub
		// check for cart existance
		
		Optional<Cart> c = cartRepository.findByCustomerEmailId(email);
		
		Cart cart = c.orElseThrow(()->new EpharmacyException("NO CART FOUND"));
		
		// products availability
		
		// products = set
		if(cart.getProducts().isEmpty()) {
			
			throw new EpharmacyException("Products not available");
		}
		
		Product required = new Product();
		
		Set<Product> p = cart.getProducts(); // check in this set
		
		for (Product product : p) {
			
			if(productId.equals(product.getProductId())) {
				
				required = product;
				break;
				
			}
			
		}
		
		if(required == null) {
			
			throw new EpharmacyException("NO PRODUCT EXISTS");
			}

		
		// it is available 
		
		// 1. remove it from the cart
		
		// 2. remove it from the db
		
		cart.getProducts().remove(required);
		
		productRepository.delete(required);
		
		return "Product is deleted";
	}

	@Override
	public String deleteAllMedicinesFromCart(String email) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Cart> optional = cartRepository.findByCustomerEmailId(email);
		Cart cart = optional.orElseThrow(()->new EpharmacyException("NO_CART_AVAILABLE"));
		if(cart.getProducts().isEmpty()) {
			throw new EpharmacyException("NO_PRODUCTS_ADDED TO THE CART");
		}
		List<Integer> productIds = new ArrayList<>();
		//this is iterable if needed convert into dto - set then proceed with traditional way
		cart.getProducts().parallelStream().forEach(product ->{
			productIds.add(product.getProductId());
			cart.getProducts().remove(product);
		});
		for (Integer id: productIds) {
			productRepository.deleteById(id);
		} 
		
		
		return "All the products in the cart are deleted";
	}

}
