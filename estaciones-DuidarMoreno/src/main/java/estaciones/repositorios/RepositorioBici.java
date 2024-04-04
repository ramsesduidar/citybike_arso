package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.dominio.Bici;


@NoRepositoryBean
public interface RepositorioBici 
	//extends CrudRepository<Bici, String>{
	extends PagingAndSortingRepository<Bici, String>{

	Optional<Bici> findByIncidencias(String idIncidencia);

	Page<Bici> findByIdEstacion(String idEstacion, Pageable pageable);
	
	List<Bici> findByIdEstacion(String idEstacion);

}