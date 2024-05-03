package pasarela.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pasarela.security.JwtUtils;
import pasarela.service.AuthService;
import pasarela.controller.AuthRequest;
import pasarela.controller.AuthResponse;
import pasarela.controller.OAuth2Request;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        
        Map<String, Object> claims = authService.getClaimsFromUsernamePassword(authRequest.getUsername(), authRequest.getPassword());

        if (claims== null) {
        	return ResponseEntity.badRequest().body("Usuario o contraseña no son válidos");
        }
       
        String token = jwtUtils.generateToken(claims);

      
        return ResponseEntity.ok(new AuthResponse(token, claims));
    }

    @PostMapping("/oauth2")
    public ResponseEntity<?> authenticateOAuth2(@RequestBody OAuth2Request oauth2Request) {
        
        Map<String, Object> claims = authService.getClaimsFromOAuth2Id(oauth2Request.getOauth2Id());
        
        if (claims== null) {
        	return ResponseEntity.badRequest().body("Usuario oauth2 no es válido");
        }
        String token = jwtUtils.generateToken(claims);

        return ResponseEntity.ok(new AuthResponse(token, claims));
    }
    
}