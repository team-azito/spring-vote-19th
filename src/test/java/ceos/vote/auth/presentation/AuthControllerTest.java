package ceos.vote.auth.presentation;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import ceos.vote.auth.presentation.docs.AuthDocs;
import ceos.vote.config.SecurityConfig;
import ceos.vote.jwt.JwtUtil;
import ceos.vote.user.application.UserService;
import ceos.vote.user.application.dto.response.UserResponse;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.fixture.VoteFixture;

@WebMvcTest(AuthController.class)
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    void 로그인한_회원_정보를_조회한다() throws Exception {
        // given
        UserResponse response = UserResponse.builder()
                .id(1L)
                .username("test")
                .email("email@email.com")
                .name("이도현")
                .part("백엔드")
                .teamName("Azito")
                .build();

        given(userService.findByUsername(anyString())).willReturn(response);
        given(jwtUtil.getUsername(anyString())).willReturn("test");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(VoteFixture.DEMO_DAY_VOTE_1.getVoteUser()));

        // when & then
        mockMvc.perform(get("/api/v1/auth")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.name").value("이도현"))
                .andExpect(jsonPath("$.part").value("백엔드"))
                .andExpect(jsonPath("$.teamName").value("Azito"))
                .andDo(print())
                .andDo(AuthDocs.generateCheckLoginUserDocumentation());
    }

    @Test
    void 로그인하지_않은_경우_실패한다() throws Exception {
        mockMvc.perform(get("/api/v1/auth"))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(AuthDocs.generateCheckLoginUserFailDocumentation());
    }
}
