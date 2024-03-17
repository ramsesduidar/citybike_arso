package estaciones.repositorios;

import estaciones.dominio.Historico;
import repositorios.Repositorio;
import repositorios.RepositorioException;

public interface RepositorioHistoricoAdHoc extends Repositorio<Historico, String>{

	Historico getHistorico(String id_bici, String id_estacion) throws RepositorioException;

}
