package com.example.demo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.CartDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.service.CartService;
@RestController
@RequestMapping(value="/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	
	@PostMapping(value ="/addcart")
	public ResponseEntity<String> addMedicinesToCart( @RequestBody CartDTO customerCartDTO)
	throws EpharmacyException {
		String str=cartService.addMedicinesToCart(customerCartDTO);
		return new ResponseEntity<String>(str, HttpStatus.OK);
		
	}

	@GetMapping(value = "/getproducts/{email}")
	public ResponseEntity<Set<ProductDTO>> getMedicinesFromCart(@PathVariable String email)
	throws EpharmacyException {
		Set<ProductDTO> productDTO=cartService.getMedicinesFromCart(email);
		return new ResponseEntity<Set<ProductDTO>>(productDTO, HttpStatus.OK);
		
	}

	@PutMapping(value = "/modify/{email}/{productId}/{quantity}")
	public ResponseEntity<String> modifyQuantityOfMedicineInCart(@PathVariable String email,@PathVariable Integer productId , @PathVariable Integer quantity) throws EpharmacyException {
		String str=cartService.modifyQuantityOfMedicinesInCart(email, productId, quantity);
		return new ResponseEntity<String>(str, HttpStatus.OK);

	}



	@DeleteMapping(value = "/delete/{email}/{productId}")
	public ResponseEntity<String> deleteMedicineFromCart(@PathVariable String email,@PathVariable Integer productId) throws EpharmacyException {
		String str=cartService.deleteMedicineFromCart(email, productId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteall/{email}")
	public ResponseEntity<String> deleteAllMedicinesFromCart(@PathVariable String email)
	throws EpharmacyException {
		String str=cartService.deleteAllMedicinesFromCart(email);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


}
