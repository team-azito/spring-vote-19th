package ceos.vote.common.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(ExceptionCode code) {
        super(code.getMessage());
    }
}
