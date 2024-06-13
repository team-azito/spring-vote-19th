package ceos.vote.user.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import ceos.vote.ApiTest;
import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.presentation.dto.request.UserCreateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class UserApiTest extends ApiTest {

    @Test
    @DisplayName("회원 가입에 성공하면 201을 반환한다.")
    void testSignUp_Success() {
        // given
        UserCreateRequest 회원_가입_요청 = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        // when
        ExtractableResponse<Response> 회원_가입_응답 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(회원_가입_요청)
                .when().post("/api/v1/users")
                .then().log().all()
                .extract();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(회원_가입_응답.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(회원_가입_응답.header("Location")).isNotBlank();
                }
        );
    }

    @Test
    @DisplayName("이미 존재하는 아이디를 사용하여 회원 가입에 실패하면 400을 반환한다.")
    void testSignUp_Fail_WhenDuplicateUsername() {
        // given
        testSignUp_Success();
        UserCreateRequest 회원_가입_요청 = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test1@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        // when
        ExtractableResponse<Response> 회원_가입_응답 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(회원_가입_요청)
                .when().post("/api/v1/users")
                .then().log().all()
                .extract();

        // then
        assertThat(회원_가입_응답.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertSoftly(
                softly -> {
                    softly.assertThat(회원_가입_응답.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softly.assertThat(회원_가입_응답.body().asString()).isEqualTo(ExceptionCode.ALREADY_EXIST_USERNAME_EXCEPTION.getMessage());
                }
        );
    }

    @Test
    @DisplayName("이미 존재하는 이메일을 사용하여 회원 가입에 실패하면 400을 반환한다.")
    void testSignUp_Fail_WhenDuplicateEmail() {
        // given
        testSignUp_Success();
        UserCreateRequest 회원_가입_요청 = UserCreateRequest.builder()
                .username("hello1")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        // when
        ExtractableResponse<Response> 회원_가입_응답 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(회원_가입_요청)
                .when().post("/api/v1/users")
                .then().log().all()
                .extract();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(회원_가입_응답.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softly.assertThat(회원_가입_응답.body().asString()).isEqualTo(ExceptionCode.ALREADY_EXIST_EMAIL_EXCEPTION.getMessage());
                }
        );
    }
}
