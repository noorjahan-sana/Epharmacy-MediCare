package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderedMedicine;
@Repository
public interface OrderedMedicineRepository extends CrudRepository<OrderedMedicine, Integer>{

}
