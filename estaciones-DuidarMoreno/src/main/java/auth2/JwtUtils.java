package auth2;

import java.util.List;

import auth2.JWTAuthenticationFilter;
import auth2.SecurityConfig;

import io.jsonwebtoken.Claims;

public class JwtUtils {

    private static final String SECRET_KEY = "my-secret-key";

    public static String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}