package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import estaciones.dominio.Estacion;
import repositorios.RepositorioException;

public interface RepositorioEstacionMongoDB
	extends RepositorioEstacion, 
			MongoRepository<Estacion, String> {

	@Query(value = "{$expr: {$lt: [{$size: '$idBicis'}, '$numPuestos']}}", fields = "{ '_id': 1 }")
	List<Estacion> findFirstLibre();

	
	//List<Estacion> findFirst3ByCoordenadasNear(Point coordenadas);

	@Aggregation(pipeline = {
			  "{ $addFields : { 'arrSize' : { $size: '$sitiosTuristicos' } } }",
			  "{ $sort : { 'arrSize' : -1 } }"
	  })
	List<Estacion> getEstacionesPorSitios();
	
	@Query("{ 'nombre' : { $regex: ?0, $options: 'i' } }")
	Page<Estacion> findByNombreLike(String nombre, Pageable pageable);
	
	@Query("{ 'nombre' : { $regex: ?0, $options: 'i' }, 'numPuestos': ?1}")
	Page<Estacion> findByNombreAndNumPuestos(String nombre, int num, Pageable pageable);
 
}
