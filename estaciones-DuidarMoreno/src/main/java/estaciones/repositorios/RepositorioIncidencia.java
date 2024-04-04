package estaciones.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import estaciones.dominio.Incidencia;

@NoRepositoryBean
public interface RepositorioIncidencia 
	extends CrudRepository<Incidencia, String>{
	//extends PagingAndSortingRepository<Incidencia, String>{

	List<Incidencia> findIncidenciasAbiertas();


}