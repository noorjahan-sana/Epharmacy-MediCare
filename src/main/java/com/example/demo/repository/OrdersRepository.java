package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Orders;
@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {

	Iterable<Orders> findByCustomerEmailId(String email);

}
