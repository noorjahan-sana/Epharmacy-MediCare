package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CardDTO;
import com.example.demo.dto.PaymentDTO;
import com.example.demo.entity.Card;
import com.example.demo.entity.Payment;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.utility.HashingUtility;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class PaymentServiceImp implements PaymentService{
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private CardRepository cardRepository;
	@Override
	public Integer makePayment(String cardId, Double amountToPay) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Card> optional	= cardRepository.findById(cardId);
		Card card = optional.orElseThrow(()->new EpharmacyException("CARD DOES NOT EXSIST"));
		Payment payment = new Payment();
		payment.setCustomerEmailId(card.getCustomerEmailId());
		payment.setAmount(amountToPay);
		payment.setPaymentTime(LocalDateTime.now());
		payment.setCardId(card.getCardId());
		paymentRepository.save(payment);
		return payment.getPaymentId();
		
	}

	@Override
	public String addCard(CardDTO cardDTO) throws Exception {
		// TODO Auto-generated method stub
		
		Optional<Card> optional=cardRepository.findById(cardDTO.getCardId());
		if(optional.isPresent()) {
			throw new EpharmacyException("CARD_ALREADY_EXISTS");
		}
		Card card = new Card();
		card.setCardId(cardDTO.getCardId());
		card.setCardType(cardDTO.getCardType());
		card.setCustomerEmailId(cardDTO.getCustomerEmailId());
		//cvv should be  cannot be saved directly it should be hashed and set
		card.setCvv(HashingUtility.getHashValue(cardDTO.getCvv()));
		card.setExpiryDate(cardDTO.getExpiryDate());
		card.setNameOnCard(cardDTO.getNameOnCard());
		cardRepository.save(card);
		
		return "CARD ADDED SUCCESSFULLY";
	}

	@Override
	public String deleteCard(String cardId) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Card> optional = cardRepository.findById(cardId);
		Card card  = optional.orElseThrow(()->new EpharmacyException("CARD DOES NOT EXSIST"));
		cardRepository.delete(card);
		return "CARD DELETED";
	}

	@Override
	public List<CardDTO> viewCards(String email) throws EpharmacyException {
		// TODO Auto-generated method stub
		Iterable<Card> iterable=cardRepository.findByCustomerEmailId(email);
		List<CardDTO> cardDTOS = new ArrayList<>();
		for (Card card : iterable) {
			CardDTO dto = new CardDTO();
			dto.setCardId(card.getCardId());
			dto.setCardType(card.getCardType());
			dto.setCustomerEmailId(card.getCustomerEmailId());
			dto.setCvv(card.getCvv());
			dto.setExpiryDate(card.getExpiryDate());
			dto.setNameOnCard(card.getNameOnCard());
			cardDTOS.add(dto);
			
		}
		return cardDTOS;
	}

	@Override
	public PaymentDTO getPaymentDetails(Integer paymentId) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Payment> optional=paymentRepository.findById(paymentId);
		Payment payment= optional.orElseThrow(()->new EpharmacyException("PAYMENT ID DOES NOT EXSIST"));
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setAmount(payment.getAmount());
		paymentDTO.setCustomerEmailId(payment.getCustomerEmailId());
		paymentDTO.setPaymentId(payment.getPaymentId());
		paymentDTO.setPaymentTime(payment.getPaymentTime());
		paymentDTO.setCard(getCardDetails(payment.getCardId()));
		
		return paymentDTO;
		
	}

	@Override
	public CardDTO getCardDetails(String cardId) throws EpharmacyException {
		// TODO Auto-generated method stub
		Optional<Card> optional = cardRepository.findById(cardId);
		Card card  = optional.orElseThrow(()->new EpharmacyException("CARD DOES NOT EXSIST"));
		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardId(card.getCardId());
		cardDTO.setCardType(card.getCardType());
		cardDTO.setCustomerEmailId(card.getCustomerEmailId());
		cardDTO.setCvv(card.getCvv());
		cardDTO.setExpiryDate(card.getExpiryDate());
		cardDTO.setNameOnCard(card.getNameOnCard());
		return cardDTO;
	}
	

}
