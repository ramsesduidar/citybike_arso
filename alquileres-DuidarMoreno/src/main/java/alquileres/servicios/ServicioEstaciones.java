package alquileres.servicios;

import java.io.IOException;

import okhttp3.Call;
import retrofit.github.EstacionesRestClient;
import retrofit2.Response;

public class ServicioEstaciones implements IServicioEstaciones{
	
	private EstacionesRestClient estacionesRestClient;

	public ServicioEstaciones(EstacionesRestClient estacionesRestClient) {
        this.estacionesRestClient = estacionesRestClient;
    }
	@Override
	public boolean hayEspacioLibre(String idEstacion) {

		Call<Boolean> call = estacionesRestClient.hayEspacioLibre(idEstacion);
        try {
            Response<Boolean> response = call.execute();
            if (response.isSuccessful()) {
            	
                return response.body();
            } else {
                return false;
            }
        } catch (IOException e) {
        	
            return false;
        }
	}

	@Override
	public boolean estacionarBici(String idEstacion, String idBici) {
		
		Call<Void> call = estacionesRestClient.estacionarBici(idEstacion, idBici);
        try {
            Response<Void> response = call.execute();
            return response.isSuccessful();
        } catch (IOException e) {
            // Manejar la excepción
            return false;
        }
	}

}
