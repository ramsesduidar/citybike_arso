package auth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import auth2.JWTAuthenticationFilter;
import auth2.JwtUtils;
import auth2.SecurityConfig;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private AuthenticationManager authenticationManager;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            try {
                Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

                String username = claims.getSubject();
                List<String> roles = (List<String>) claims.get("roles");

                Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                logger.error("An error occurred while trying to authenticate the user", e);
            }
        }

        chain.doFilter(request, response);
    }

}