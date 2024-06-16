package ceos.vote.common.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ExceptionCode code) {
        super(code.getMessage());
    }
}
