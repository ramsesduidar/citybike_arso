package estaciones.servicios;

import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.SitioTuristico;
import estaciones.dto.BiciDTO;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.ServicioException;

public interface IServicioEstaciones {

	String altaEstacion(String nombre, int num_puestos, String direccion,
			double latitud, double longitud) throws DataAccessException;
	
	Estacion recuperarEstacion(String id) throws DataAccessException, EntidadNoEncontrada;
	
	List<SitioTuristico> obtenerSitios(String id) throws Exception;
	
	void establecerSitios(String id, List<SitioTuristico> sitios) throws DataAccessException, EntidadNoEncontrada;
	
	String altaBici(String modelo, String id_estacion) throws DataAccessException, EntidadNoEncontrada, ServicioException;
	
	Bici recuperarBici(String id) throws DataAccessException, EntidadNoEncontrada;
	
	void estacionarBici(String id_bici, String id_estacion) throws DataAccessException, EntidadNoEncontrada, ServicioException;
	
	void estacionarBici(String id_bici) throws DataAccessException, EntidadNoEncontrada, ServicioException;
	
	void retirarBici(String id_bici) throws DataAccessException, ServicioException, EntidadNoEncontrada; 
	
	void darBajaBici(String id, String motivo) throws DataAccessException, EntidadNoEncontrada, ServicioException;
	
	List<Bici> recuperarBiciPosicion(Double latitud, Double longitud) throws DataAccessException;
	
	List<Estacion> recuperarEstacionPorSitios() throws DataAccessException;
	
	List<Estacion> recuperarTodasEstaciones() throws DataAccessException;
	
	List<Bici> getBicisFromEstacion(String id_estacion) throws DataAccessException, EntidadNoEncontrada;
	
	Page<Estacion> recuperarTodasEstacionesPaginado(Pageable pageable) throws DataAccessException;
	
	Page<Bici> getBicisFromEstacionPaginado(String id_estacion, Pageable pageable) throws DataAccessException, EntidadNoEncontrada;
	
}
