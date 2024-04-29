package pasarela.service;

import java.io.IOException;
import java.util.Map;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class AuthService {

    private final UsuariosService usuariosService;

    public AuthService(@Value("${zuul.routes.usuarios.url}") String usuariosUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(usuariosUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuariosService = retrofit.create(UsuariosService.class);
    }

    public Map<String, Object> getClaimsFromUsernamePassword(String username, String password) {
        Call<Map<String, Object>> call = usuariosService.verifyCredentials(username, password);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getClaimsFromOAuth2Id(String oauth2Id) {
        Call<Map<String, Object>> call = usuariosService.verifyOAuth2(oauth2Id);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}