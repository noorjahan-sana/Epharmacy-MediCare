package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.CustomerAddressDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.PrimePlansDTO;
import com.example.demo.exception.EpharmacyException;

public interface CustomerService {
	String authenticateCustomer(String emailId, String password) throws Exception;

	String registerNewCustomer(CustomerDTO customerDTO) throws Exception;

	CustomerDTO viewCustomer(String email) throws EpharmacyException;

	LocalDate upgradeCustomerToPrime(CustomerDTO customerDTO) throws EpharmacyException;
	
	PrimePlansDTO getPlan(String email)throws EpharmacyException;
	
	String deleteAddress(Integer addressId) throws EpharmacyException;
	
	String addCustomerAddress(CustomerAddressDTO caDTO, String email) throws EpharmacyException;
	
	List<CustomerAddressDTO> viewAllAddress(String email) throws EpharmacyException;
	
	CustomerAddressDTO getAddress(Integer deliveryId) throws EpharmacyException;


}
