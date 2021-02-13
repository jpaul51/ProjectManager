package com.jonas.suivi.views.descriptors;

public class InvalidActionDescriptorException extends Exception {
	

		
		String message;
		
		public InvalidActionDescriptorException(String message) {
			this.message = message;
		}

		
		@Override
		public String getLocalizedMessage() {
			// TODO Auto-generated method stub
			return message;
		}
}
