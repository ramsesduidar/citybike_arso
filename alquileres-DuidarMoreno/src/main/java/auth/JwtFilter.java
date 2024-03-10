package auth;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
    	// Comprobamos si la ruta tiene la anotación @PermitAll
    	if (resourceInfo.getResourceMethod()
    			.isAnnotationPresent(PermitAll.class)) {
    		return;
    	}
    	
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
        	return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        try {
            Claims claims = JwtUtil.validateTokenAndGetClaims(token);
            System.out.println("Intento de acceso de usuario, token: " + claims);
            
            // Esto ya lo hace el parser de JWT
            Date caducidad = claims.getExpiration();
			
			if (caducidad.before(new Date())) {
				System.out.println("ha caducado...");
				requestContext.abortWith(
						Response.status(Response.Status.UNAUTHORIZED).entity("Token caducado").build());
			}
			
			// Gestion de roles
			
			Set<String> roles = new HashSet<>(
					Arrays.asList(claims.get(Rol.nombre_cabecera, String.class).split(",")));
			
			// Consulta si la operación está protegida por rol
			if (this.resourceInfo.getResourceMethod()
					.isAnnotationPresent(RolesAllowed.class)) {
				String[] allowedRoles = resourceInfo.getResourceMethod()
						.getAnnotation(RolesAllowed.class).value();
				if (roles.stream()
						.noneMatch(userRole -> Arrays.asList(allowedRoles)
								.contains(userRole))) {
					requestContext.abortWith(
							Response.status(Response.Status.FORBIDDEN).entity("Rol no permitido").build());
				}
			}
			
			
        } catch (Exception e) {
        	requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build());
		}
    }
}