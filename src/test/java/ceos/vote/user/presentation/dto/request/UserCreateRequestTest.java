package ceos.vote.user.presentation.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserCreateRequestTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory()
            .getValidator();

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("아이디가 빈 값일 경우 예외가 발생한다.")
    void testUsernameIsBlank(String username) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .username(username)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("아이디는 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("비밀번호가 빈 값일 경우 예외가 발생한다.")
    void testPasswordIsBlank(String password) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .password(password)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("비밀번호는 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab1!", "abcdefghijklmnopqrstuvwxyz1!", "Abcdef!", "12345678!@", "abcdef1234", "Abc 123!@"})
    @DisplayName("비밀번호가 8~20자의 대문자, 소문자, 숫자, 특수문자를 포함하지 않으면 예외가 발생한다.")
    void testPasswordPattern(String password) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .password(password)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("비밀번호는 8~20자의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이메일이 빈 값일 경우 예외가 발생한다.")
    void testEmailIsBlank(String email) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .email(email)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("이메일은 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "test.com", "test@com."})
    @DisplayName("비밀번호가 빈 값일 경우 예외가 발생한다.")
    void testEmailIFormat(String email) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .email(email)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("이메일 형식이 올바르지 않습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 빈 값일 경우 예외가 발생한다.")
    void testNameIsBlank(String name) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .name(name)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("이름은 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("파트가 빈 값일 경우 예외가 발생한다.")
    void testPartIsBlank(String part) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .part(part)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("파트는 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("팀 이름이 빈 값일 경우 예외가 발생한다.")
    void testTeamNameIsBlank(String teamName) {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .teamName(teamName)
                .build();

        // when
        List<String> messages = getMessages(request);

        // then
        assertThat(messages).contains("팀 이름은 필수 입력 값입니다.");
    }

    private List<String> getMessages(UserCreateRequest request) {
        return VALIDATOR.validate(request)
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
    }
}
