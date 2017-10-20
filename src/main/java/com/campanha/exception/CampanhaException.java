package com.campanha.exception;

public class CampanhaException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;
 
	public String getErrorMessage() {
		return errorMessage;
	}
	public CampanhaException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public CampanhaException() {
		super();
	}
}