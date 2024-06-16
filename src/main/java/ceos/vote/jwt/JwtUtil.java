package ceos.vote.jwt;

import static io.jsonwebtoken.Jwts.SIG.HS256;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.jwt.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiredMs;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret,
                   @Value("${spring.jwt.expired}") long expiredMs) {
        final String algorithm = HS256.key()
                .build()
                .getAlgorithm();
        this.secretKey = new SecretKeySpec(secret.getBytes(UTF_8), algorithm);
        this.expiredMs = expiredMs;
    }

    public String createJwt(String username, String role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public void validateJwt(String token) {
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            throw new InvalidJwtException(ExceptionCode.INVALID_JWT);
        }
    }

    public String getUsername(String token) {
        return getPayload(token).get("username", String.class);
    }

    public String getRole(String token) {
        return getPayload(token).get("role", String.class);
    }

    public boolean isExpired(String token) {
        return getPayload(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
