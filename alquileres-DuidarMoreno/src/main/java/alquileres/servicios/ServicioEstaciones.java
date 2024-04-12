package alquileres.servicios;

import java.io.IOException;

import dto.EstacionDTO;
import retrofit2.Call;
import retrofit.github.EstacionesRestClient;
import retrofit.github.RetrofitUtils;
import retrofit2.Response;

public class ServicioEstaciones implements IServicioEstaciones{
	
	private EstacionesRestClient estacionesRestClient;
/*
	public ServicioEstaciones(EstacionesRestClient estacionesRestClient) {
        this.estacionesRestClient = estacionesRestClient;
    }*/
	public ServicioEstaciones() {
        this.estacionesRestClient = RetrofitUtils.getEstacionesRestClient();
    }
	@Override
	public boolean hayEspacioLibre(String idEstacion) {

		Call<EstacionDTO> call = estacionesRestClient.recuperarEstacion(idEstacion);
        try {
            Response<EstacionDTO> response = call.execute();
            if (response.isSuccessful()) {
            	
                return response.body().getHuecosLibres() > 0;
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
