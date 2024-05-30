package ceos.vote.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    INVALID_TEAM_NAME("존재하지 않는 팀 이름입니다."),
    INVALID_PART("존재하지 않는 파트입니다."),
    ALREADY_EXIST_USERNAME("이미 존재하는 아이디입니다."),
    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다.");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
