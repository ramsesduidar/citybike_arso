package pasarela.auth;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import pasarela.service.AuthService;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private AuthService servicioAuth;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        
    	DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
    	
    	System.out.println("Intento de OAuth2 de: " + usuario.getName());
    	
        Map<String, Object> claims = servicioAuth.getClaimsFromOAuth2Id(usuario.getName());
        
        System.out.println("DefaultOAuth2User usuario: " + usuario);
        
        if (claims != null) {
            String token = JwtUtils.generateToken(claims);
            System.out.println("Token generado: " + token);
            response.getWriter().append(token);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario "+usuario.getName()
            											+" no está autorizado en la aplicación");
        }
    }

}