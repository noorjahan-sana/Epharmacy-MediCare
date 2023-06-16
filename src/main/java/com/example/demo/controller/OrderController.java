package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.service.OrderService;
@RestController
@RequestMapping(value="/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@GetMapping(value = "/allorders/{email}")
	public ResponseEntity<List<OrderDTO>> viewOrders(@PathVariable String email) throws EpharmacyException{
		List<OrderDTO> orderDTO=orderService.viewOrders(email);
		
		return new ResponseEntity<List<OrderDTO>>(orderDTO, HttpStatus.OK);
		
	}

	@PostMapping(value = "/placeorder")
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) throws EpharmacyException{
		String str = orderService.placeOrder(orderDTO);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@PutMapping(value = "/updateorder/{orderId}/{reason}")
	public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId,@PathVariable String reason) throws EpharmacyException{
		String str = orderService.cancelOrder(orderId, reason);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}


}
