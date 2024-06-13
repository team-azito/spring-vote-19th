package ceos.vote.jwt.filter;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ceos.vote.auth.dto.request.LoginDto;
import ceos.vote.config.SecurityConfig;
import ceos.vote.jwt.JwtUtil;
import ceos.vote.user.domain.repository.UserRepository;

@WebMvcTest(JwtAuthenticationFilter.class)
@Import(SecurityConfig.class)
@AutoConfigureRestDocs
class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("로그인에 성공한다.")
    void testLogin() throws Exception {
        // given
        LoginDto request = new LoginDto("test", "gkdkgl133!");

        Authentication authResult = new UsernamePasswordAuthenticationToken("test", "gkdkgl133!");

        given(authenticationManager.authenticate(any())).willReturn(authResult);
        given(jwtUtil.createJwt(anyString(), anyString()))
                .willReturn("example.access.token");

        // when & then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", containsString("Bearer ")))
                .andDo(print())
                .andDo(document("login/success",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("username").description("아이디"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseHeaders(
                                        headerWithName("Authorization").description("Access Token")
                                )
                        )
                );
    }

    @Test
    @DisplayName("로그인에 실패한다.")
    void attemptFail() throws Exception {
        // given
        LoginDto request = new LoginDto("test", "wrong1234!");

        given(authenticationManager.authenticate(any()))
                .willThrow(new BadCredentialsException("로그인 실패"));

        // when & then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(document("login/fail/wrong",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("username").description("아이디"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("message").description("에러 메시지")
                                )
                        )
                );
    }

    @Test
    public void attemptAuthenticationWithInvalidJson() throws Exception {
        String invalidJson = "{\"username\":\"username\", \"password\"}";

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("login/fail/invalid-json",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("message").description("에러 메시지")
                                )
                        )
                );
    }
}
