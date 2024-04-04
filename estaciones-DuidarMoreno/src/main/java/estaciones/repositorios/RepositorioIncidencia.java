package estaciones.repositorios;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.dominio.EstadoIncidencia;
import estaciones.dominio.Incidencia;

@Repository
public interface RepositorioIncidencia extends JpaRepository<Incidencia, String>{
	//extends PagingAndSortingRepository<Incidencia, String>{

	List<Incidencia> findByEstadoIn(List<EstadoIncidencia> estados);

    @Query("SELECT i FROM Incidencia i WHERE i.bici.id = ?1")
    List<Incidencia> findByBiciId(String idBici);

	Incidencia save(Incidencia incidencia);

	List<Incidencia> findIncidenciasAbiertas();
	
}