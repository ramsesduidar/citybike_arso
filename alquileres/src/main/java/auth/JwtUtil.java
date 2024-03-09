package auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "secret";

    // GENERACIÓN DEL TOKEN
    public static String generateJwtToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "12345");
        claims.put(Rol.nombre_cabecera, Rol.USUARIO);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // VALIDACIÓN Y OBTENCIÓN DE CLAIMS
    public static Map<String, Object> validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // AUTORIZACIÓN
    public static void checkAuthorization(Map<String, Object> claims) {

        if (claims.containsKey(Rol.nombre_cabecera)) {
            Rol userRole = (Rol) claims.get(Rol.nombre_cabecera);
            if (!isValidRole(userRole)) {
                throw new RuntimeException("Unauthorized");
            }
        }
    }

    private static boolean isValidRole(Rol userRole) {
        return userRole == Rol.USUARIO || userRole == Rol.ADMIN;
    }
}