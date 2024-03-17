package estaciones.repositorios;

import java.util.List;

import estaciones.dominio.Estacion;
import repositorios.Repositorio;
import repositorios.RepositorioException;

public interface RepositorioEstacionAdHoc extends Repositorio<Estacion, String>{

	String buscarPrimeraLibre() throws RepositorioException;

	List<Estacion> obtener3MasCercanas(Double latitud, Double longitud) throws RepositorioException;

	List<Estacion> getEstacionesPorSitios() throws RepositorioException;

}
