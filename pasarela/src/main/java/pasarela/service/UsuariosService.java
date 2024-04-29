package pasarela.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Map;

public interface UsuariosService {
    @POST("{usuario}/contrasena")
    Call<Map<String, Object>> verifyCredentials(@Path("usuario") String usuario, @Body String password);

    @GET("{oauth2}/oauth2")
    Call<Map<String, Object>> verifyOAuth2(@Path("oauth2") String oauth2Id);
}