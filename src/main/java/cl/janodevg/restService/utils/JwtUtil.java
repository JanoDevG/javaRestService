package cl.janodevg.restService.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.function.Supplier;

public final class JwtUtil {

    private JwtUtil(){
        throw new UnsupportedOperationException("Utility class must not be instanced.");
    }

    private static final String SECRET_KEY = generateASecureKey();

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // generate a random secure secret key
    static protected String generateASecureKey() {
        Supplier<String> secretKeySupplier = () -> {
            byte[] keyBytes = new byte[32];
            SecureRandom random = new SecureRandom();
            random.nextBytes(keyBytes);
            return Base64.getEncoder().encodeToString(keyBytes);
        };
        return secretKeySupplier.get();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace("Bearer ", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractSubject(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return claims.getBody().getSubject();
    }
}
