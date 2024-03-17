package estaciones.servicios;

import java.util.List;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.SitioTuristico;
import estaciones.dto.BiciDTO;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.ServicioException;

public interface IServicioEstaciones {

	String altaEstacion(String nombre, int num_puestos, String direccion,
			double latitud, double longitud) throws RepositorioException;
	
	Estacion recuperarEstacion(String id) throws RepositorioException, EntidadNoEncontrada;
	
	List<SitioTuristico> obtenerSitios(String id) throws Exception;
	
	void establecerSitios(String id, List<SitioTuristico> sitios) throws RepositorioException, EntidadNoEncontrada;
	
	String altaBici(String modelo, String id_estacion) throws RepositorioException, EntidadNoEncontrada, ServicioException;
	
	BiciDTO recuperarBiciDTO(String id) throws RepositorioException, EntidadNoEncontrada;
	
	Bici recuperarBici(String id) throws RepositorioException, EntidadNoEncontrada;
	
	void estacionarBici(String id_bici, String id_estacion) throws RepositorioException, EntidadNoEncontrada, ServicioException;
	
	void estacionarBici(String id_bici) throws RepositorioException, EntidadNoEncontrada, ServicioException;
	
	void retirarBici(String id_bici) throws RepositorioException, ServicioException, EntidadNoEncontrada; 
	
	void darBajaBici(String id, String motivo) throws RepositorioException, EntidadNoEncontrada, ServicioException;
	
	Integer recuperarBiciPosicionCount(Double latitud, Double longitud) throws RepositorioException;
	
	List<Bici> recuperarBiciPosicion(Double latitud, Double longitud) throws RepositorioException;
	
	public List<BiciDTO> recuperarBiciPosicionLazy(Double latitud, Double longitud, int start, int max) throws RepositorioException;
	
	List<Estacion> recuperarEstacionPorSitios() throws RepositorioException;
	
}
