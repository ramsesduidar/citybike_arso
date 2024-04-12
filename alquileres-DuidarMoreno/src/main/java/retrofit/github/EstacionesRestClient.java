package retrofit.github;

import dto.EstacionDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EstacionesRestClient {
    @GET("estaciones/{idEstacion}")
    Call<EstacionDTO> recuperarEstacion(@Path("idEstacion") String idEstacion);

    @POST("estaciones/{idEstacion}/bicis")
    Call<Void> estacionarBici(@Path("idEstacion") String idEstacion, @Body String idBici);

}