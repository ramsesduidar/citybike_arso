package pasarela.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        System.out.println("La cabecera auth: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7).trim();
            try {
            	Claims claims = JwtUtils.validateTokenAndGetClaims(token); 
            	Set<String> roles = new HashSet<>(
    					Arrays.asList(claims.get("Roles", String.class).split(",")));
            	
            	
            	ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            	roles.forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol)));
                
                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, authorities);
                
                System.out.println("Accedçso de: " + auth);
                
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch(Exception e) {
            	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error al verificar el token JWT: " + e.getMessage());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}