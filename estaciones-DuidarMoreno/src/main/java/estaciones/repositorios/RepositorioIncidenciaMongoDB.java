package estaciones.repositorios;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import estaciones.dominio.Incidencia;

public interface RepositorioIncidenciaMongoDB
	extends RepositorioIncidencia,
			MongoRepository<Incidencia, String>{
	
	@Query("{'estado': { $in: [ 'PENDIENTE', 'ASIGNADA' ] } }")
	List<Incidencia> findIncidenciasAbiertas();

}
