package estaciones.repositorios;

import java.util.List;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.Incidencia;
import repositorios.EntidadNoEncontrada;
import repositorios.Repositorio;
import repositorios.RepositorioException;

public interface RepositorioBiciAdHoc extends Repositorio<Bici, String>{
	
	
	//public String addIncidencia(Bici bici, Incidencia incidencia) throws RepositorioException, EntidadNoEncontrada;
	
	public Incidencia getIncidenciaById(String id) throws RepositorioException, EntidadNoEncontrada;

	public List<Incidencia> getIncidenciasAbiertas() throws RepositorioException;
	
	public List<Incidencia> getIncidenciasAbiertasLazy(int start, int max) throws RepositorioException;
	
	public Number countIncidenciasAbiertas() throws RepositorioException;
	
	public Bici getBiciByIncidencia(String id_incidencia) throws RepositorioException, EntidadNoEncontrada;
	
	public List<Bici> getBiciByEstacionLazy(List<Estacion> estaciones, int start, int max) throws RepositorioException, EntidadNoEncontrada;
}
