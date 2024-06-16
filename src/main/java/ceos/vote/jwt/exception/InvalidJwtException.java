package ceos.vote.jwt.exception;

import ceos.vote.common.exception.ExceptionCode;
import io.jsonwebtoken.JwtException;

public class InvalidJwtException extends JwtException {

    public InvalidJwtException(ExceptionCode code) {
        super(code.getMessage());
    }
}
