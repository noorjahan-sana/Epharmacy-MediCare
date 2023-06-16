package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Medicine;
@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Integer>{

	Iterable<Medicine> findByCategory(String category);



}
