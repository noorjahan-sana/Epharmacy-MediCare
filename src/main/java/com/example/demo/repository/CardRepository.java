package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Card;



@Repository
public interface CardRepository extends CrudRepository<Card, String> {

	Iterable<Card> findByCustomerEmailId(String email);

	

}
