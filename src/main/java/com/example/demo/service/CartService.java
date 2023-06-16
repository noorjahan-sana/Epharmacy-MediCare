package com.example.demo.service;

import java.util.Set;

import com.example.demo.dto.CartDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.EpharmacyException;

public interface CartService {
	String addMedicinesToCart(CartDTO cart) throws EpharmacyException;

	Set<ProductDTO> getMedicinesFromCart(String email) throws EpharmacyException;

	String modifyQuantityOfMedicinesInCart(String email, Integer productId , Integer quantity)
	throws EpharmacyException;

	String deleteMedicineFromCart(String email, Integer productId) throws EpharmacyException;

	String deleteAllMedicinesFromCart(String email) throws EpharmacyException;

}
