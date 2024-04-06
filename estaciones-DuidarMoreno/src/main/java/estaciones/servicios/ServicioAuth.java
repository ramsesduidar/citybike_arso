package estaciones.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.auth2.JwtUtils;

@Service
@Transactional
public class ServicioAuth implements IServicioAuth{

	private Map<String, Info> usuarios;
	
	private class Info{
		
		private String password;
		private String rol;
		
		public Info(String p, String r) {
			this.password = p;
			this.rol = r;
		}

		public String getPassword() {
			return password;
		}

		
		public String getRol() {
			return rol;
		}
		
	}
	
	public ServicioAuth() {
		this.usuarios = new HashMap<>();
		this.usuarios.put("paco2@um.es", new Info("123", "Gestor"));
		this.usuarios.put("ramses@um.es", new Info("ramses", "Usuario"));
		this.usuarios.put("piero@um.es", new Info("piero", "Usuario"));
		
	}
	
	public Map<String, Object> verificarCredenciales(String username, String password) {
		
		System.out.println("Inicio de cjhdsbd de: " + username + " " + password);
		
		if (this.usuarios.containsKey(username)
				&& this.usuarios.get(username).getPassword().equals(password)) {
			
			Map<String, Object> claims = new HashMap<>();// el cuerpo del token
			
			claims.put("sub", username);
			claims.put("Roles", this.usuarios.get(username).getRol());
			System.out.println(claims);
			String token = JwtUtils.generateToken(claims);
			System.out.println(token);
			return claims;
		}
		else {
			return null;
		}
		
	}
	
	public Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario) {
		
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", usuario.getAttribute("login"));
		claims.put("Roles", "Gestor");
		System.out.println(claims);
        return claims;
    }
}
