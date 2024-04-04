package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.geo.Point;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import estaciones.dominio.Bici;

//import com.mongodb.client.model.geojson.Point;

import estaciones.dominio.Estacion;
import estaciones.dominio.EstadoBici;
import estaciones.repositorios.RepositorioEstacion;
//import repositorios.RepositorioException;

@Repository
public interface RepositorioEstacion  extends JpaRepository<Estacion, String> {
	//extends PagingAndSortingRepository<Estacion, String>{
	//extends CrudRepository<Estacion, String>{
	
    List<Estacion> findFirstByDisponibleTrue();

	List<Estacion> findFirstLibre();
	
	Estacion findFirstByNombre(String nombre);

	List<Estacion> findFirst3ByCoordenadasNear(Point coordenadas);

    List<Estacion> findBySitiosTuristicos(List<String> idsSitios);

	List<Estacion> getEstacionesPorSitios();
    
    Optional<Estacion> findByNombre(String nombre);

    List<Estacion> findByCoordenadasNear(double latitud, double longitud, double distancia);

    List<Estacion> findBySitiosTuristicosIds(List<String> idsSitios);

	Estacion create(Estacion estacion);

	Estacion update(String id, Estacion estacion);

	void delete(String id);

	List<Estacion> findCercanas(Double latitud, Double longitud);

	List<Bici> findBicisEnEstacion(String id);

	List<Estacion> findDisponibles();

	List<Estacion> findByCapacidadGreaterThan(int capacidadMinima);

	List<Estacion> findByBicisDisponiblesGreaterThan(int bicicletasDisponiblesMin);

	List<Estacion> findByEstadoBici(EstadoBici estadoBici);
	
}
