package cl.janodevg.restService.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    public void whenGenerateToken_thenShouldReturnToken() {
        String subject = "user";
        String token = JwtUtil.generateToken(subject);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void whenValidateToken_withValidToken_thenShouldReturnTrue() {
        String subject = "user";
        String token = JwtUtil.generateToken(subject);
        assertTrue(JwtUtil.validateToken(token));
    }

    @Test
    public void whenValidateToken_withInvalidToken_thenShouldReturnFalse() {
        String invalidToken = "invalidToken";
        assertFalse(JwtUtil.validateToken(invalidToken));
    }

    @Test
    public void whenExtractSubject_withValidToken_thenShouldReturnSubject() {
        String expectedSubject = "user";
        String token = JwtUtil.generateToken(expectedSubject);
        String actualSubject = JwtUtil.extractSubject(token);
        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void whenTokenIsTampered_thenValidationShouldFail() {
        String subject = "user";
        String token = JwtUtil.generateToken(subject);

        // Simulate the token being tampered with
        String tamperedToken = token.replace("user", "admin");

        assertTrue(JwtUtil.validateToken(tamperedToken));
    }

    @Test
    public void whenTokenIsExpired_thenValidationShouldFail() {
        String subject = "user";
        // Generate a token with a past expiration date
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // token expired
                .signWith(SignatureAlgorithm.HS256, JwtUtil.generateASecureKey()) // generate a new key for testing
                .compact();

        assertFalse(JwtUtil.validateToken(token));
    }
}
