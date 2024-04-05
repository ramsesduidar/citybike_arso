package estaciones.servicios;

import java.util.Map;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public interface IServicioAuth {

	Map<String, Object> verificarCredenciales(String username, String password);
	Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario);

}
