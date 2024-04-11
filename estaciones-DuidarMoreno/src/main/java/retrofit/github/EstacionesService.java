package retrofit.github;

import alquileres.dominio.Alquiler;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EstacionesService {

    // Consultar si hay un hueco de aparcamiento en una estación
    @GET("estaciones/{idEstacion}/disponibilidad")
    Call<Boolean> consultarDisponibilidad(@Path("idEstacion") String idEstacion);

    // Marcar una bicicleta como no disponible al alquilarla
    @POST("estaciones/{idEstacion}/bicicletas/{idBicicleta}/alquilar")
    Call<Void> marcarBicicletaNoDisponible(@Path("idEstacion") String idEstacion,
                                           @Path("idBicicleta") String idBicicleta,
                                           @Body Alquiler alquiler);

    // Marcar una bicicleta como disponible al finalizarse el alquiler
    @POST("estaciones/{idEstacion}/bicicletas/{idBicicleta}/devolver")
    Call<Void> marcarBicicletaDisponible(@Path("idEstacion") String idEstacion,
                                         @Path("idBicicleta") String idBicicleta);
}