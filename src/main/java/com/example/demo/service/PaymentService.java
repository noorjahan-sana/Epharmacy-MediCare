package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CardDTO;
import com.example.demo.dto.PaymentDTO;
import com.example.demo.exception.EpharmacyException;

public interface PaymentService {
	public Integer makePayment(String cardId,Double amountToPay) throws EpharmacyException;

	public String addCard(CardDTO cardDTO) throws Exception;

	public String deleteCard(String cardId) throws EpharmacyException;

	public List<CardDTO> viewCards(String email) throws EpharmacyException;

	PaymentDTO getPaymentDetails(Integer paymentId) throws EpharmacyException;

	CardDTO getCardDetails(String cardId) throws EpharmacyException;

}
