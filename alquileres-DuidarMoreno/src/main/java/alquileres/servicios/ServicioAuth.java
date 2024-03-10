package alquileres.servicios;

import java.util.HashMap;
import java.util.Map;

import auth.Rol;

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
		this.usuarios.put("paco2@um.es", new Info("123", Rol.ADMINISTRADOR));
		this.usuarios.put("ramses@um.es", new Info("ramses", Rol.USUARIO_NORMAL));
		this.usuarios.put("piero@um.es", new Info("piero", Rol.USUARIO_NORMAL));
		
	}
	
	public Map<String, Object> verificarCredenciales(String username, String password) {
		
		System.out.println("Inicio de sesion de: " + username + " " + password);
		
		if (this.usuarios.containsKey(username)
				&& this.usuarios.get(username).getPassword().equals(password)) {
			
			Map<String, Object> claims = new HashMap<>();// el cuerpo del token
			
			claims.put("sub", username);
			claims.put(Rol.nombre_cabecera, this.usuarios.get(username).getRol());
			
			return claims;
		}
		else {
			return null;
		}
		
		
	}
}
