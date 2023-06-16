package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Card;
import com.example.demo.entity.Cart;

@Repository
public interface CartRepository  extends CrudRepository<Cart, Integer>{

	Optional<Cart> findByCustomerEmailId(String customerEmailId);

	

}
