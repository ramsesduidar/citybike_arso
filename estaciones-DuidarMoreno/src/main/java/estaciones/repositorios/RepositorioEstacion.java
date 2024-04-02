package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.geo.Point;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import estaciones.dominio.Estacion;
//import repositorios.RepositorioException;

@Repository
public interface RepositorioEstacion  extends PagingAndSortingRepository<Estacion, String> {
	//extends PagingAndSortingRepository<Estacion, String>{
	//extends CrudRepository<Estacion, String>{
	
    List<Estacion> findFirstByDisponibleTrue();

	//List<Estacion> findFirstLibre();
	
	Estacion findFirstByNombre(String titulo);

	List<Estacion> findFirst3ByCoordenadasNear(Point coordenadas);

    List<Estacion> findBySitiosTuristicos(List<String> idsSitios);

	//List<Estacion> getEstacionesPorSitios();
	
}
