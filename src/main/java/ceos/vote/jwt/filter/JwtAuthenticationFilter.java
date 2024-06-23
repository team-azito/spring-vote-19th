package ceos.vote.jwt.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import ceos.vote.auth.dto.request.LoginDto;
import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.common.exception.ExceptionResponse;
import ceos.vote.jwt.JwtUtil;
import ceos.vote.jwt.exception.LoginInfoParsingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            LoginDto loginDto = objectMapper.readValue(messageBody, LoginDto.class);

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new LoginInfoParsingException(ExceptionCode.LOGIN_INFO_PARSING_EXCEPTION);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication authResult) {
        log.info("로그인 성공");
        final String username = authResult.getName();

        String role = authResult.getAuthorities()
                .iterator()
                .hasNext() ? authResult.getAuthorities().iterator().next().getAuthority() : "";

        // 토큰 생성
        String access = jwtUtil.createJwt(username, role);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access);
        response.setStatus(OK.value());
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                              final AuthenticationException failed) throws IOException {
        log.info("로그인 실패");

        ObjectMapper om = new ObjectMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(failed.getMessage());

        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(om.writeValueAsString(exceptionResponse));
    }
}
