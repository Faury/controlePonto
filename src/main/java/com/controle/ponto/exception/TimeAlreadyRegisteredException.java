package com.controle.ponto.exception;

import com.controle.ponto.util.MessageCodes;

public class TimeAlreadyRegisteredException extends RuntimeException {

    public TimeAlreadyRegisteredException() {
        super(MessageCodes.TIME_ALREADY_REGISTERED);
    }
}
