package com.controle.ponto.exception;

import com.controle.ponto.util.MessageCodes;

public class InvalidRegisterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8826051525576107022L;

	private InvalidRegisterException(String message) {
		super(message);
	}
	
	public static InvalidRegisterException newLunchMissingException() {
		return new InvalidRegisterException(MessageCodes.LUNCH_TIME_MISSING);
	}
	
	public static InvalidRegisterException newWeekendNotValidException() {
		return new InvalidRegisterException(MessageCodes.WEEKEND_INVALID);
	}
	
	public static InvalidRegisterException newMaximumRegistersPerDayException() {
		return new InvalidRegisterException(MessageCodes.MAXIMUM_REGISTERS_DAY);
	}
}
