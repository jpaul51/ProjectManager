package com.jonas.suivi.views.descriptors;

public class InvalidAppDescriptException extends Exception {

	
	String message;
	
	public InvalidAppDescriptException(String message) {
		this.message = message;
	}

	
	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return message;
	}
}
