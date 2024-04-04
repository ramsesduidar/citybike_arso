package auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "secret";

    // GENERACIÓN DEL TOKEN
    public String generateJwtToken(Map<String, String> claims) {
    	
    	Date caducidad = Date.from(
				Instant.now()
				.plusSeconds(3600)); // 1 hora de validez
		
		String token = Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, JwtUtil.SECRET_KEY)
				.setExpiration(caducidad)
				.setIssuedAt(new Date())
				.compact();
		
		return token;
    }

    // VALIDACIÓN Y OBTENCIÓN DE CLAIMS
    public static Claims validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

	public boolean validateToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	public String generateToken(Map<String, Object> claims) {
		// TODO Auto-generated method stub
		return null;
	}

	public Claims getClaims(String token) {
		// TODO Auto-generated method stub
		return null;
	}

    /* esto se hace en el filtro
    public static void checkAuthorization(Map<String, Object> claims) {

        if (claims.containsKey(Rol.nombre_cabecera)) {
            String roleClaim = (String) claims.get(Rol.nombre_cabecera);
            if (!isValidRole(roleClaim)) {
                throw new RuntimeException("Unauthorized");
            }
        }
    }*/
    
    /*
    private static boolean isValidRole(String userRole) {
        return roleClaim.equals(Rol.USUARIO.toString()) || roleClaim.equals(Rol.ADMIN.toString());
    }
    
    public static Response unauthorized() {
		return null;
	}*/
}
