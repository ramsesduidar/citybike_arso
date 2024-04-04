package estaciones.repositorios;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import estaciones.dominio.Historico;

@Repository
public interface RepositorioHistorico extends JpaRepository<Historico, String>{
	//extends PagingAndSortingRepository<Historico, String>{
	
	List<Historico> findByIdBiciAndIdEstacion(String idBici, String idEstacion);
	
	Historico findByIdBiciAndIdEstacionAndFechaFinNull(String idBici, String idEstacion);

	Historico registrarMovimiento(String idBici, String idEstacion);

	void finalizarMovimiento(String idBici, String idEstacion);

	Historico consultarMovimientoActivo(String idBici, String idEstacion);

}