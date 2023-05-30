package com.controle.ponto.exception;

import com.controle.ponto.util.MessageCodes;

public class InvalidRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8826051525576107022L;

	private InvalidRequestException(String message) {
		super(message);
	}
	
	public static InvalidRequestException newInvalidDateTimeException() {
		return new InvalidRequestException(MessageCodes.INVALID_DATETIME);
	}
	
	public static InvalidRequestException newRequiredFieldsException() {
		return new InvalidRequestException(MessageCodes.REQUIRED_FIELD);
	}

}
