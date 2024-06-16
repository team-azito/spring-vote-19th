package ceos.vote.common.exception;

public class DomainException extends BadRequestException {

    public DomainException(ExceptionCode code) {
        super(code);
    }
}
