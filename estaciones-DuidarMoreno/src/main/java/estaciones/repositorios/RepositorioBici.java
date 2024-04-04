package estaciones.repositorios;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import estaciones.dominio.Bici;
//import estaciones.dominio.Estacion;
//import estaciones.dominio.Incidencia;


@Repository
public interface RepositorioBici extends JpaRepository<Bici, String>{
	//extends PagingAndSortingRepository<Bici, String>{
	
	List<Bici> findByIdEstacion(String idEstacion);

	Bici create(Bici bici);

	Bici update(String id, Bici bici);

	void delete(String id);

	Optional<Bici> findByIncidenciasId(String id);

}
