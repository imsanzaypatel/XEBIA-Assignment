package com.xebia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(ArticleException.class)
	public ResponseEntity<ArticalErr> mapException(ArticleException ex) {
		
		ArticalErr articalErr = new ArticalErr(HttpStatus.NOT_FOUND.value(),ex.getMessage());
		return new ResponseEntity<ArticalErr>(articalErr,HttpStatus.NOT_FOUND);
		
	}
	
}
