package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerAddressDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.MedicineDTO;
import com.example.demo.dto.PrimePlansDTO;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.service.CustomerService;
@RestController
@RequestMapping(value="/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@GetMapping(value = "/authenticate/{emailId}/{password}")
	public ResponseEntity<String> authenticateCustomer(@PathVariable String emailId,@PathVariable String password) throws Exception{
		String str= customerService.authenticateCustomer(emailId, password);
		return new ResponseEntity<String>(str, HttpStatus.OK);
}

	@PostMapping(value = "/register")
	public ResponseEntity<String> registerNewCustomer( @RequestBody CustomerDTO customerDTO) throws Exception{
		String str=customerService.registerNewCustomer(customerDTO);
		return new ResponseEntity<String>(str,HttpStatus.OK);
	
	}


	@GetMapping(value = "/getcustomer/{email}")
	public ResponseEntity<CustomerDTO> viewCustomer(@PathVariable String email) throws EpharmacyException{
		CustomerDTO customerDTO=customerService.viewCustomer(email);
		return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
	
	}


	@PutMapping(value = "/upgrade")
	public ResponseEntity<LocalDate> upgradeCustomerToPrime( @RequestBody CustomerDTO customerDTO) throws EpharmacyException{
		LocalDate localdate=customerService.upgradeCustomerToPrime(customerDTO);
		return  new ResponseEntity<LocalDate>(localdate, HttpStatus.OK);
	
	}


	@GetMapping(value = "/getplan/{email}")
	public ResponseEntity<PrimePlansDTO>  getPlanById(@PathVariable String email)throws EpharmacyException{
		PrimePlansDTO primeplansdto=customerService.getPlan(email);
		return new ResponseEntity<PrimePlansDTO>(primeplansdto, HttpStatus.OK);
		}


	
	@DeleteMapping(value = "/delete/{addressId}")
	public ResponseEntity<String>  deleteAddress(@PathVariable Integer addressId) throws EpharmacyException{
		String str=customerService.deleteAddress(addressId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


	@PutMapping(value = "/addaddress/{email}")
	public ResponseEntity<String>  addCustomerAddress( @RequestBody CustomerAddressDTO caDTO,@PathVariable String email) throws EpharmacyException{
		String str = customerService.addCustomerAddress(caDTO, email);
		return new  ResponseEntity<String>(str,HttpStatus.OK);
	
	}


	@GetMapping(value = "/addresses/{email}")
	public ResponseEntity<List<CustomerAddressDTO>> viewAllAddress(@PathVariable String email) throws EpharmacyException{
		List<CustomerAddressDTO> customeradressdto=customerService.viewAllAddress(email);
		return new ResponseEntity<List<CustomerAddressDTO>>(customeradressdto, HttpStatus.OK);
	
}


}
