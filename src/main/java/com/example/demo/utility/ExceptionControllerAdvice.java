package com.example.demo.utility;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.EpharmacyException;



@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception){
		
		ErrorInfo error =new ErrorInfo();
		error.setErrorMessage("CHECK AFTER SOMETIME");
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(EpharmacyException.class)
	public ResponseEntity<ErrorInfo> ExceptionHandler(EpharmacyException exception){
		
		ErrorInfo error =new ErrorInfo();
		error.setErrorMessage(exception.getMessage());
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST);
	}


}
