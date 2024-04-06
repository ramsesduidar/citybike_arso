package estaciones.controlador;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import estaciones.auth2.JwtUtils;
import estaciones.servicios.IServicioAuth;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IServicioAuth servicioAuth;
	
	public AuthController() {
		
	}
	
	// curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" --data "username=paco2@um.es&password=123" http://localhost:8080/api/auth/login
    @GetMapping("/login")
    public String login(
    		@RequestParam String username, 
    		@RequestParam String password) {
    	
    	// Verificar las credenciales y emisión del token
    	Map<String, Object> claims = servicioAuth.verificarCredenciales(username, password);
        
    	
			
			String token = JwtUtils.generateToken(claims);
			System.out.println(token);
			return token;
		
    }
}