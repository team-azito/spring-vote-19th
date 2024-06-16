package ceos.vote.user.exception;

import ceos.vote.common.exception.BadRequestException;
import ceos.vote.common.exception.ExceptionCode;

public class AlreadyExistException extends BadRequestException {

    public AlreadyExistException(ExceptionCode code) {
        super(code);
    }
}
