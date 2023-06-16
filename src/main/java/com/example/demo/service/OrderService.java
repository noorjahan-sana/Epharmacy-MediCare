package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.exception.EpharmacyException;

import jakarta.transaction.Transactional;

public interface OrderService {
	List<OrderDTO> viewOrders(String email) throws EpharmacyException;
	public String placeOrder(OrderDTO orderDTO) throws EpharmacyException;
	public String cancelOrder(Integer orderId,String reason) throws EpharmacyException;

}
