package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomerAddressDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.MedicineDTO;
import com.example.demo.dto.PrimePlansDTO;
import com.example.demo.entity.Card;
import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerAddress;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.PrimePlans;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.repository.CustomerAddressRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PrimePlansRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class CustomerServiceImp implements CustomerService{
	@Autowired
	private CustomerAddressRepository customerAddress;
	@Autowired
	private CustomerRepository customer;
	@Autowired
	private PrimePlansRepository primePlans;
	@Override
	public String authenticateCustomer(String emailId, String password) throws Exception {
		
		Optional<Customer> optional=customer.findByCustomerEmailId(emailId);
		Customer customerdata =optional.orElseThrow(()->new EpharmacyException("Wrong username"));	
		if (customerdata.getPassword().equals(password))
		{
			return "SignIn";
		}
		else {
			return "Wrong Password";
		}
		
	}

	@Override
	public String registerNewCustomer(CustomerDTO customerDTO) throws Exception {
//		Optional<Customer> optional=customer.findById(customerDTO.getCustomerId());
//		if(optional.isPresent()) {
//			throw new EpharmacyException("USER_ALREADY_EXISTS");
//		}
		Customer c=new Customer();
		//no need to set customer id because it is auto generated (auto increment)
		c.setContactNumber(customerDTO.getContactNumber());
		c.setCustomerEmailId(customerDTO.getCustomerEmailId());
		c.setCustomerName(customerDTO.getCustomerName());
		c.setDateOfBirth(customerDTO.getDateOfBirth());
		c.setGender(customerDTO.getGender());
		c.setPassword(customerDTO.getPassword());
		//Add that customer
		customer.save(c);
		return "Signup successfully";
		
	}

	@Override
	public CustomerDTO viewCustomer(String email) throws EpharmacyException {
		Optional<Customer> optional=customer.findByCustomerEmailId(email);
		Customer customerdata =optional.orElseThrow(()->new EpharmacyException("Email Not found"));
		CustomerDTO cusdto=new CustomerDTO();
		List<CustomerAddress> ca=customerdata.getAddressList();
		List<CustomerAddressDTO> cadto=new ArrayList<>();
		for(CustomerAddress temp:ca)
		{
			CustomerAddressDTO cad=new CustomerAddressDTO();
			cad.setAddressId(temp.getAddressId());
			cad.setAddressLine1(temp.getAddressLine1());
			cad.setAddressLine2(temp.getAddressLine2());
			cad.setAddressName(temp.getAddressName());
			cad.setArea(temp.getArea());
			cad.setCity(temp.getCity());
			cad.setPincode(temp.getPincode());
			cad.setState(temp.getState());
			cadto.add(cad);
		}
		cusdto.setAddressList(cadto);
		
		if(!(customerdata.getPlan()==null)) {
		PrimePlans plans=customerdata.getPlan();
		PrimePlansDTO planDTO = new PrimePlansDTO();
		planDTO.setPlanDescription(plans.getPlanDescription());
		planDTO.setPlanId(plans.getPlanId());
		planDTO.setPlanName(plans.getPlanName());
		cusdto.setPlan(planDTO);
		}
		cusdto.setPlanExpiryDate(customerdata.getPlanExpiryDate());
		cusdto.setContactNumber(customerdata.getContactNumber());
		cusdto.setCustomerEmailId(customerdata.getCustomerEmailId());
		//when we are modifying entity we need no to set id because it is primary and auto increment but in dto we need to set
		cusdto.setCustomerId(customerdata.getCustomerId());
		cusdto.setCustomerName(customerdata.getCustomerName());
		cusdto.setDateOfBirth(customerdata.getDateOfBirth());
		cusdto.setGender(customerdata.getGender());
		cusdto.setPassword(customerdata.getPassword());
		return cusdto;
	}

	@Override
	public LocalDate upgradeCustomerToPrime(CustomerDTO customerDTO) throws EpharmacyException {
		// TODO Auto-generated method stub
				Optional<Customer> optionalc=customer.findByCustomerEmailId(customerDTO.getCustomerEmailId());
				Customer custom =optionalc.orElseThrow(()->new EpharmacyException("Email Not found"));
				Optional<PrimePlans> optional =primePlans.findByPlanName(customerDTO.getPlan().getPlanName());
				PrimePlans plans = optional.get();//???
				
				
				
				custom.setPlan(plans);
				if(plans.getPlanName().equals("YEARLY")) custom.setPlanExpiryDate (LocalDate.now().plusYears(1));

				else if(plans.getPlanName().equals("QUARTERLY")) custom.setPlanExpiryDate (LocalDate.now().plusMonths(3));

				else if (plans.getPlanName().equals("MONTHLY")) custom.setPlanExpiryDate (LocalDate.now().plusMonths (1));

				return custom.getPlanExpiryDate();
	}

	@Override
	public PrimePlansDTO getPlan(String email) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Customer> optional	=customer.findByCustomerEmailId(email);
		Customer customer =optional.orElseThrow(()->new EpharmacyException("Email Not found"));
		PrimePlansDTO primePlanDTO= new PrimePlansDTO();
		if(!(customer.getPlan()==null)) {
		PrimePlans plan=customer.getPlan();
		primePlanDTO.setPlanDescription(plan.getPlanDescription());
		primePlanDTO.setPlanId(plan.getPlanId());
		primePlanDTO.setPlanName(plan.getPlanName());
		}
		return primePlanDTO;
	
		
		
	}

	@Override
	public String deleteAddress(Integer addressId) throws EpharmacyException {
		Optional<CustomerAddress> optional=customerAddress.findById(addressId);
		CustomerAddress customeraddress =optional.orElseThrow(()->new EpharmacyException("Address not found"));
		customerAddress.delete(customeraddress);
		return "Delete Successfull";
	}

	@Override
	public String addCustomerAddress(CustomerAddressDTO caDTO, String email) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Customer> optional=customer.findByCustomerEmailId(email);
		Customer customer =optional.orElseThrow(()->new EpharmacyException("CUSTOMER NOT FOUND"));
		CustomerAddress ca=new CustomerAddress();
		//converting customeradressDTO to customer address entity
		//should not set address id because its auto increment 
		ca.setAddressLine1(caDTO.getAddressLine1());
		ca.setAddressLine2(caDTO.getAddressLine2());
		ca.setAddressName(caDTO.getAddressName());
		ca.setArea(caDTO.getArea());
		ca.setCity(caDTO.getCity());
		ca.setPincode(caDTO.getPincode());
		ca.setState(caDTO.getState());
		List<CustomerAddress> adressList=customer.getAddressList();
		//adding another address object to the address list of customers
		adressList.add(ca);
		return "Success";
	}

	@Override
	public List<CustomerAddressDTO> viewAllAddress(String email) throws EpharmacyException {
		Optional<Customer> optional = customer.findByCustomerEmailId(email);
		Customer customer = optional.orElseThrow(()->new EpharmacyException("Not Found"));
		List<CustomerAddress> list= customer.getAddressList();
		List<CustomerAddressDTO> customeraddressDTOS = new ArrayList<>();
		for (CustomerAddress DTO : list) {
			CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO();
			customerAddressDTO.setAddressId(DTO.getAddressId());
			customerAddressDTO.setAddressLine1(DTO.getAddressLine1());
			customerAddressDTO.setAddressLine2(DTO.getAddressLine2());
			customerAddressDTO.setAddressName(DTO.getAddressName());
			customerAddressDTO.setArea(DTO.getArea());
			customerAddressDTO.setCity(DTO.getCity());
			customerAddressDTO.setPincode(DTO.getPincode());
			customerAddressDTO.setState(DTO.getState());
			customeraddressDTOS.add(customerAddressDTO);
		}
		
		return customeraddressDTOS;
	}

	@Override
	public CustomerAddressDTO getAddress(Integer deliveryId) throws EpharmacyException {
		Optional<CustomerAddress> optional	= customerAddress.findById(deliveryId);
		CustomerAddress  address = optional.orElseThrow(()->new EpharmacyException("DELIVERY ID NOT FOUND"));
		CustomerAddressDTO customerAdressDTO = new CustomerAddressDTO();
		customerAdressDTO.setAddressId(address.getAddressId());
		customerAdressDTO.setAddressLine1(address.getAddressLine1());
		customerAdressDTO.setAddressLine1(address.getAddressLine2());
		customerAdressDTO.setAddressName(address.getAddressName());
		customerAdressDTO.setArea(address.getArea());
		customerAdressDTO.setCity(address.getCity());
		customerAdressDTO.setPincode(address.getPincode());
		customerAdressDTO.setState(address.getState());
		return customerAdressDTO;
	}
	

}
