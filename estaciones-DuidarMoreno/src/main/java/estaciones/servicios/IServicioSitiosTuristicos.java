package estaciones.servicios;

import java.util.List;

import estaciones.dominio.SitioTuristico;

public interface IServicioSitiosTuristicos {

	List<SitioResumen> obtenerSitios(double lat, double lng) throws Exception;
	
	SitioTuristico obtenerSitioCompleto(String id) throws Exception;
}
