package auth;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import alquileres.servicios.IServicioAlquileres;
import alquileres.servicios.IServicioAuth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import servicios.FactoriaServicios;

@Path("auth")
public class AuthController {

	private IServicioAuth servicioAuth = FactoriaServicios.getServicio(IServicioAuth.class);
	
	// curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" --data "username=paco2@um.es&password=123" http://localhost:8080/api/auth/login
    @POST
    @Path("login")
    @PermitAll
    public Response login(
    		@FormParam("username") String username, 
    		@FormParam("password") String password) {
    	
    	// Verificar las credenciales y emisión del token
    	Map<String, Object> claims = servicioAuth.verificarCredenciales(username, password);
        
    	if (claims != null) {
			
			String token = JwtUtil.generateJwtToken(claims);
			
			return Response.ok(token).build();
		} else {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity("Usuario o contraseña no son válidos").build();
		}
    }
}