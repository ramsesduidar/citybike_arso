package estaciones.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import estaciones.dominio.Historico;

@NoRepositoryBean
public interface RepositorioHistorico 
	//extends PagingAndSortingRepository<Historico, String>{
	extends CrudRepository<Historico, String>{

	List<Historico> findByIdBiciAndIdEstacion(String id_bici, String id_estacion);

	Historico findByIdBiciAndIdEstacionAndFechaFinNull(String id_bici, String id_estacion);

}