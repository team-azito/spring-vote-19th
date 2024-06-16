package ceos.vote.user.domain;

import java.util.Arrays;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.exception.UserException;
import lombok.Getter;

@Getter
public enum Part {

    FRONT_END("프론트엔드"),
    BACK_END("백엔드");

    private final String value;

    Part(String value) {
        this.value = value;
    }

    public static Part findPart(String part) {
        return Arrays.stream(Part.values())
                .filter(p -> p.getValue().equals(part))
                .findAny()
                .orElseThrow(() -> new UserException(ExceptionCode.INVALID_PART));
    }
}
