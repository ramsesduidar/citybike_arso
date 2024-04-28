package pasarela.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pasarela.security.AuthorizationFilter;
import pasarela.security.JwtUtils;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	Autowired
    private JwtUtils jwtUtils;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll() // Permitir acceso a las rutas de autenticación
            .antMatchers("/usuarios/**").authenticated() // Proteger las rutas del microservicio de usuarios
            .anyRequest().authenticated() // Requerir autenticación para todas las demás rutas
        	.and()
        	.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // Agregar esta línea
        
    }
}