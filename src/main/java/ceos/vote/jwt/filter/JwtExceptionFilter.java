package ceos.vote.jwt.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ceos.vote.common.exception.ExceptionResponse;
import ceos.vote.jwt.exception.InvalidJwtException;
import ceos.vote.jwt.exception.LoginInfoParsingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (LoginInfoParsingException e) {
            setExceptionResponse(response, e, HttpServletResponse.SC_BAD_REQUEST);
        } catch (MalformedJwtException | SignatureException e) {
            setExceptionResponse(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            setExceptionResponse(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            setExceptionResponse(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (InvalidJwtException e) {
            setExceptionResponse(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            setExceptionResponse(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void setExceptionResponse(HttpServletResponse response, Exception e, int scUnauthorized)
            throws IOException {
        log.error("JWT 예외 발생: {}", e.getMessage());

        ObjectMapper om = new ObjectMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(scUnauthorized);
        response.getWriter().write(om.writeValueAsString(exceptionResponse));
    }
}
