package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.Incidencia;


@NoRepositoryBean
public interface RepositorioBici 
	extends CrudRepository<Bici, String>{
	//extends PagingAndSortingRepository<Bici, String>{
	
	Optional<Bici> findByIncidencias(String idIncidencia);
	
	List<Bici> findByIdEstacion(String idEstacion);

}
