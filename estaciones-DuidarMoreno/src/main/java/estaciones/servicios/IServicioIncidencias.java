package estaciones.servicios;

import java.util.ArrayList;
import java.util.List;

import estaciones.dominio.Bici;
import estaciones.dominio.Incidencia;
import estaciones.dto.IncidenciaDTO;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.ServicioException;

public interface IServicioIncidencias {

	// Devuelve el id de la Incidencia
	void crearIncidencia(String bici, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	IncidenciaDTO recuperarIncidencia(String id) throws RepositorioException, EntidadNoEncontrada;
	
	List<Incidencia> recuperarIncidenciasAbiertas() throws RepositorioException;
	
	void cancelarIncidencia(String id, String motivo_cierre) throws RepositorioException, EntidadNoEncontrada;
	
	void asignarIncidencia(String id, String operario) throws RepositorioException, EntidadNoEncontrada;
	
	void resolverIncidenciaReparada(String id, String motivo_cierre) throws RepositorioException, EntidadNoEncontrada;
	
	void resolverIncidenciaNoReparada(String id, String motivo_cierre) throws RepositorioException, EntidadNoEncontrada;

	List<IncidenciaDTO> recuperarIncidenciasAbiertasLazy(int start, int max) throws ServicioException;

	int countIncidenciasAbiertas() throws ServicioException;


}
