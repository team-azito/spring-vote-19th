package ceos.vote.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    INVALID_TEAM_NAME("존재하지 않는 팀 이름입니다."),
    INVALID_PART("존재하지 않는 파트입니다."),
    INVALID_JWT("토큰이 비어있거나 'Bearer '로 시작하지 않습니다."),
    ALREADY_EXIST_USERNAME_EXCEPTION("이미 존재하는 아이디입니다."),
    ALREADY_EXIST_EMAIL_EXCEPTION("이미 존재하는 이메일입니다."),
    LOGIN_INFO_PARSING_EXCEPTION("로그인 정보를 읽을 수 없습니다.");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
