package ceos.vote.jwt.exception;

import ceos.vote.common.exception.BadRequestException;
import ceos.vote.common.exception.ExceptionCode;

public class LoginInfoParsingException extends BadRequestException {

    public LoginInfoParsingException(ExceptionCode code) {
        super(code);
    }
}
