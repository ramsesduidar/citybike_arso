package retrofit.github;

import alquileres.dominio.Alquiler;
import estaciones.dto.BiciDTO;
import estaciones.dto.EstacionDTO;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RetrofitUtils {
    private static Retrofit retrofit;
    private static EstacionesRestClient estacionesRestClient;

    public static EstacionesRestClient getEstacionesRestClient() {
        if (estacionesRestClient == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            estacionesRestClient = retrofit.create(EstacionesRestClient.class);
        }

        return estacionesRestClient;
    }
}