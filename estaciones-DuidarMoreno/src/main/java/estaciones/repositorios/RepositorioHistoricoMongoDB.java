package estaciones.repositorios;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import estaciones.dominio.Historico;

public interface RepositorioHistoricoMongoDB 
	extends RepositorioHistorico, 
			MongoRepository<Historico, String> {
	
	//List<Historico> findByIdBiciAndIdEstacion(String id_bici, String id_estacion);
	
	//Historico findByIdBiciAndIdEstacionAndFechaFinNull(String id_bici, String id_estacion);
}
