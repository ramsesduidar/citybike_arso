package auth2;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import auth.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, String> claims = fetchUserInfo(usuario);
        if (claims != null) {
            String token = jwtUtil.generateJwtToken(claims);
            response.setHeader("Authorization", "Bearer " + token);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No está autorizado en la aplicación");
        }
    }

    private Map<String, String> fetchUserInfo(DefaultOAuth2User usuario) {
        Map<String, String> claims = (Map<String, String>) usuario.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .findFirst()
                .map(rol -> {
                    return Map.of(
                            "sub", usuario.getName(),
                            "rol", rol
                    );
                })
                .orElse(null);
        return claims;
    }
}