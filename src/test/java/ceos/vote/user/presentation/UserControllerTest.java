package ceos.vote.user.presentation;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.config.SecurityConfig;
import ceos.vote.user.application.UserService;
import ceos.vote.user.exception.AlreadyExistException;
import ceos.vote.user.fixture.UserFixture;
import ceos.vote.user.presentation.dto.request.UserCreateRequest;

@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private UserService userService;

    @Test
    void testSignUp_Success() throws Exception {
        // given
        given(userService.createUser(any())).willReturn(UserFixture.USER_1.getId());

        // when & then
        UserCreateRequest request = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user/create/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("part").description("파트"),
                                fieldWithPath("teamName").description("팀 이름")
                        )));
    }

    @Test
    @DisplayName("아이디가 이미 존재하는 경우 회원 가입에 실패한다.")
    void testSignUp_Fail_WhenDuplicateUsername() throws Exception {
        // given
        given(userService.createUser(any()))
                .willThrow(new AlreadyExistException(ExceptionCode.ALREADY_EXIST_USERNAME));

        // when & then
        UserCreateRequest request = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("user/create/fail/username",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("part").description("파트"),
                                fieldWithPath("teamName").description("팀 이름")
                        )));
    }

    @Test
    @DisplayName("이메일이 이미 존재하는 경우 회원 가입에 실패한다.")
    void testSignUp_Fail_WhenDuplicateEmail() throws Exception {
        // given
        given(userService.createUser(any()))
                .willThrow(new AlreadyExistException(ExceptionCode.ALREADY_EXIST_EMAIL));

        // when & then
        UserCreateRequest request = UserCreateRequest.builder()
                .username("hello")
                .password("hotekvkx124!")
                .email("test@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("user/create/fail/email",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("part").description("파트"),
                                fieldWithPath("teamName").description("팀 이름")
                        )));
    }
}