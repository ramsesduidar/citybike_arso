package pasarela.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private SecuritySuccessHandler successHandler;
	
	@Autowired
    private JwtRequestFilter authenticationRequestFilter;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/usuarios").hasAuthority("Gestor")
            .antMatchers(HttpMethod.POST, "/usuarios").hasAuthority("Gestor")
            .antMatchers(HttpMethod.DELETE, "/usuarios/*").hasAuthority("Gestor")// Proteger las rutas del microservicio de usuarios
            .antMatchers("/auth/oauth2").authenticated()
            .anyRequest().permitAll() // Requerir autenticación para todas las demás rutas
        	.and()
        	.oauth2Login().successHandler(this.successHandler)
        	.and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar esta línea
        
    }
}