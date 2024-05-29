package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.dominio.Estacion;


@NoRepositoryBean
public interface RepositorioEstacion 
	extends PagingAndSortingRepository<Estacion, String>{
	//extends CrudRepository<Estacion, String>{

	List<Estacion> findFirstLibre();

	Optional<Estacion> findFirstByNombre(String titulo);
	
	Page<Estacion> findByNombreLike(String nombre, Pageable pageable);
	
	Page<Estacion> findByNumPuestos(int num, Pageable pageable);
	
	Page<Estacion> findByNombreAndNumPuestos(String nombre, int num, Pageable pageable);

	List<Estacion> findFirst3ByCoordenadasNear(Point coordenadas);

	List<Estacion> getEstacionesPorSitios();

}