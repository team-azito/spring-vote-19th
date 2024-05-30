package ceos.vote.user.exception;

import ceos.vote.common.exception.DomainException;
import ceos.vote.common.exception.ExceptionCode;

public class UserException extends DomainException {

    public UserException(ExceptionCode code) {
        super(code);
    }
}
