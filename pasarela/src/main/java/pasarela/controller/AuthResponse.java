package pasarela.controller;

import java.util.Map;


public class AuthResponse {
	private String token;
    private Map<String, Object> claims;

    // Constructor
    public AuthResponse(String token, Map<String, Object> claims) {
        this.token = token;
        this.claims = claims;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

}
