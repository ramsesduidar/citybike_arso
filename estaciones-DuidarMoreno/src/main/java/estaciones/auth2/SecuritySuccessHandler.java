package estaciones.auth2;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import estaciones.servicios.IServicioAuth;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IServicioAuth servicioAuth;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        
    	DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> claims = servicioAuth.fetchUserInfo(usuario);
        
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