package estaciones.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.Incidencia;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

@Repository
public class RepositorioBiciAdHocSQL implements RepositorioBiciAdHoc{

	@Override
	public String add(Bici entity) throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Bici entity) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Bici entity) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Bici getById(String id) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bici> getAll() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Incidencia getIncidenciaById(String id) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertas() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertasLazy(int start, int max) throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number countIncidenciasAbiertas() throws RepositorioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bici getBiciByIncidencia(String id_incidencia) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bici> getBiciByEstacionLazy(List<Estacion> estaciones, int start, int max)
			throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		return null;
	}

}
