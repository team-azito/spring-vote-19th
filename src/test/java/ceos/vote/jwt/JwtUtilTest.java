package ceos.vote.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.jwt.exception.InvalidJwtException;
import io.jsonwebtoken.ExpiredJwtException;

class JwtUtilTest {

    private String secret;
    private JwtUtil jwtUtil;
    private String jwt;

    @BeforeEach
    void setUp() {
        secret = "myseckrekey123456bfdpdsfgklsdfjl";
        jwtUtil = new JwtUtil(secret, 1000L);
        jwt = jwtUtil.createJwt("username", null);
    }

    @Test
    @DisplayName("JWT를 생성한다.")
    void testCreateJwt() {
        // given
        String username = "myname";
        String role = null;

        // when
        String jwt = jwtUtil.createJwt(username, role);

        // then
        assertThat(jwt).isNotNull();
    }

    @Test
    @DisplayName("유효한 JWT를 검증한다.")
    void testValidJwt() {
        String validJwt = "Bearer " + jwt;

        assertThatNoException().isThrownBy(() -> jwtUtil.validateJwt(validJwt));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("JWT가 비어있으면 예외가 발생한다.")
    void testJwtIsBlank(String jwt) {
        assertThatThrownBy(() -> jwtUtil.validateJwt(jwt))
                .isInstanceOf(InvalidJwtException.class)
                .hasMessage(ExceptionCode.INVALID_JWT.getMessage());
    }

    @Test
    @DisplayName("JWT가 'Bearer '로 시작하지 않으면 예외가 발생한다.")
    void testJwtNotStartWithBearer() {
        assertThatThrownBy(() -> jwtUtil.validateJwt("token"))
                .isInstanceOf(InvalidJwtException.class)
                .hasMessage(ExceptionCode.INVALID_JWT.getMessage());
    }
    
    @Test
    @DisplayName("JWT에서 username을 추출한다.")
    void testGetUsernameFromJwt() {
        assertThat(jwtUtil.getUsername(jwt)).isEqualTo("username");
    }

    @Test
    @DisplayName("JWT에서 role을 추출한다.")
    void testGetRoleFromJwt() {
        assertThat(jwtUtil.getRole(jwt)).isNull();
    }

    @Test
    @DisplayName("JWT가 만료되지 않았으면 false를 반환한다.")
    void testNotExpiredJwt() {
        assertThat(jwtUtil.isExpired(jwt)).isFalse();
    }

    @Test
    @DisplayName("JWT가 만료되었으면 예외가 발생한다.")
    void testExpiredJwt() {
        // given
        JwtUtil jwtUtil = new JwtUtil(secret, 0L);
        String jwt = jwtUtil.createJwt("username", null);

        // when & then
        assertThatThrownBy(() -> jwtUtil.isExpired(jwt))
                .isInstanceOf(ExpiredJwtException.class);
    }
}
