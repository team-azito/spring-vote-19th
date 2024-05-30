package ceos.vote.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.user.exception.AlreadyExistException;
import ceos.vote.user.fixture.UserFixture;
import ceos.vote.user.presentation.dto.request.UserCreateRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원 가입에 성공한다.")
    void testCreateUser_Success() {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.save(any())).willReturn(UserFixture.USER_1);

        // when
        Long userId = userService.createUser(request);

        // then
        assertThat(userId).isEqualTo(UserFixture.USER_1.getId());
    }

    @Test
    @DisplayName("아아디가 존재하는 경우 회원 가입에 실패한다.")
    void testCreateUser_Fail_WhenDuplicateUsername() {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        given(userRepository.existsByUsername(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request))
            .isInstanceOf(AlreadyExistException.class)
            .hasMessage(ExceptionCode.ALREADY_EXIST_USERNAME.getMessage());
    }

    @Test
    @DisplayName("이메일이 존재하는 경우 회원 가입에 실패한다.")
    void testCreateUser_Fail_WhenDuplicateEmail() {
        // given
        UserCreateRequest request = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessage(ExceptionCode.ALREADY_EXIST_EMAIL.getMessage());
    }
}
