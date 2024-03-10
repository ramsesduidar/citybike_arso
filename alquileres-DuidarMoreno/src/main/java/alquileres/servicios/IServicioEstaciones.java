package alquileres.servicios;

public interface IServicioEstaciones {

	boolean hayEspacioLibre(String idEstacion);
	
	boolean estacionarBici(String idEstacion, String idBici);
}
