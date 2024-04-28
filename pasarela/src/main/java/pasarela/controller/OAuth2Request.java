package pasarela.controller;

public class OAuth2Request {
	private String oauth2Id;

    
    public OAuth2Request() {
    }

    public OAuth2Request(String oauth2Id) {
        this.oauth2Id = oauth2Id;
    }

    // Getter y Setter
    public String getOauth2Id() {
        return oauth2Id;
    }

    public void setOauth2Id(String oauth2Id) {
        this.oauth2Id = oauth2Id;
    }

}
