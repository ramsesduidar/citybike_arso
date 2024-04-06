package estaciones.auth2;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	//@Value("${jwt.secret}")
    //private static String SECRET_KEY;
	private static final String SECRET_KEY = "secret";
	private static final int CADUCIDAD = 3600; //1 hora

    public static String generateToken(Map<String, Object> claims) {
    	
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
        				Instant.now()
        				.plusSeconds(CADUCIDAD))) // 10 minuto de validez
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

 // VALIDACIÓN Y OBTENCIÓN DE CLAIMS
    public static Claims validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}