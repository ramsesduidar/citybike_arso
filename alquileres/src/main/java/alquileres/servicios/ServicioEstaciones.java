package alquileres.servicios;

public class ServicioEstaciones implements IServicioEstaciones{

	@Override
	public boolean hayEspacioLibre(String idEstacion) {

		return true;
	}

	@Override
	public boolean estacionarBici(String idEstacion, String idBici) {
		
		return true;
	}

}
