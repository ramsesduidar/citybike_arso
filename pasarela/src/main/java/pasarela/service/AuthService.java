package pasarela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pasarela.controller.OAuth2Request;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
	
	@Value("${zuul.routes.usuarios.url}")
    private String usuariosUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getClaimsFromUsernamePassword(String username, String password) {
        // Llamar al microservicio de usuarios para obtener los claims
        Map<String, Object> claims = restTemplate.postForObject(
                //"http://localhost:777/api/usuarios/verify-credentials",
        		usuariosUrl + "/" + username + "/contrasena",
        		password,
        		//new CredentialsRequest(username, password),
                Map.class);

        return claims;
    }

    public Map<String, Object> getClaimsFromOAuth2Id(String oauth2Id) {
        // Llamar al microservicio de usuarios para obtener los claims
        Map<String, Object> claims = restTemplate.getForObject(
                //"http://localhost:777/api/usuarios/verify-oauth2",
        		//usuariosUrl + "/verify-oauth2",
        		usuariosUrl + "/" + oauth2Id + "/oauth2",
        		//new OAuth2Request(oauth2Id),
                Map.class);

        return claims;
    }

}