package estaciones.repositorios;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import estaciones.dominio.Bici;

public interface RepositorioBiciMongoDB 
	extends RepositorioBici,
			MongoRepository<Bici, String>{
	
	@Query("{incidencias: ObjectId(?0)}")
	Optional<Bici> findByIncidencias(String idIncidencia);
	
	//List<Bici> findByIdEstacion(String idEstacion);

}
