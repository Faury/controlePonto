package com.controle.ponto.exception;

import com.controle.ponto.util.MessageCodes;

public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8826051525576107022L;

	private NotFoundException(String message) {
		super(message);
	}

	public static NotFoundException newReportNotFoundException() {
		return new NotFoundException(MessageCodes.REPORT_NOT_FOUND);
	}

}
