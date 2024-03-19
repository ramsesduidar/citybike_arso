package estaciones.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import estaciones.dominio.Bici;
import estaciones.dominio.Incidencia;
import estaciones.dto.IncidenciaDTO;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.ServicioException;

public interface IServicioIncidencias {

	// Devuelve el id de la Incidencia
	void crearIncidencia(String bici, String descripcion) throws DataAccessException, EntidadNoEncontrada;
	
	IncidenciaDTO recuperarIncidencia(String id) throws DataAccessException, EntidadNoEncontrada;
	
	List<Incidencia> recuperarIncidenciasAbiertas() throws DataAccessException;
	
	void cancelarIncidencia(String id, String motivo_cierre) throws DataAccessException, EntidadNoEncontrada;
	
	void asignarIncidencia(String id, String operario) throws DataAccessException, EntidadNoEncontrada;
	
	void resolverIncidenciaReparada(String id, String motivo_cierre) throws DataAccessException, EntidadNoEncontrada;
	
	void resolverIncidenciaNoReparada(String id, String motivo_cierre) throws DataAccessException, EntidadNoEncontrada;


}
