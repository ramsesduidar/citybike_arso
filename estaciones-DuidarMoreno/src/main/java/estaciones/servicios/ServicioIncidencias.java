package estaciones.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.dominio.Bici;
import estaciones.dominio.EstadoBici;
import estaciones.dominio.EstadoIncidencia;
import estaciones.dominio.Incidencia;
import estaciones.dto.IncidenciaDTO;
import estaciones.repositorios.RepositorioBici;
import estaciones.repositorios.RepositorioIncidencia;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.ServicioException;


/*
 * Cambiar la lógica de las operaciones que modifican incidencias
 */
@Service
@Transactional
public class ServicioIncidencias implements IServicioIncidencias {
	
	private RepositorioBici repositorioBici;
	
	private RepositorioIncidencia repositorioIncidencia;
	
	private IServicioEstaciones serviceEstacion;
	
	@Autowired
	public ServicioIncidencias(RepositorioBici repositorioBici,
							RepositorioIncidencia repositorioIncidencia,
							IServicioEstaciones serviceEstacion) {
		
		this.repositorioBici = repositorioBici;
		this.repositorioIncidencia = repositorioIncidencia;
		this.serviceEstacion = serviceEstacion;
	}
	
	@Override
	public void crearIncidencia(String id_bici, String descripcion) throws DataAccessException, EntidadNoEncontrada {
		if (id_bici == null)
	        throw new IllegalArgumentException("id_Bici: no debe ser nulo");
		
		if (descripcion == null || descripcion.isEmpty())
	        throw new IllegalArgumentException("descripcion: no debe ser nula");
		
		Bici bici = serviceEstacion.recuperarBici(id_bici);
		
		if(bici.esDe_Baja() || ! bici.esDisponible())
			throw new IllegalArgumentException("La bici está dada de baja, ne se pueden crear incidencias en ella");
		
		Incidencia incidencia = new Incidencia(bici, descripcion);
		incidencia = this.repositorioIncidencia.save(incidencia);
		
		bici.addIncidencia(incidencia);
		repositorioBici.save(bici);
		
	}

	@Override
	public IncidenciaDTO recuperarIncidencia(String id) throws DataAccessException, EntidadNoEncontrada {
		
		Optional<Incidencia> optional = repositorioIncidencia.findById(id);
		
		if(!optional.isPresent())
			throw new EntidadNoEncontrada("No existe la incidencia con id: "+id);
			
		return transformToDTOIncidencia(optional.get());
		
	}
	
	private Bici recuperarBiciPorIncidencia(String id) throws DataAccessException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Optional<Bici> optional = repositorioBici.findByIncidencias(id);
		
		if(!optional.isPresent())
			throw new EntidadNoEncontrada("No existe la bici con incidencia id :" + id);
		
		return optional.get();
	}

	@Override
	public List<Incidencia> recuperarIncidenciasAbiertas() throws DataAccessException {
		
		return this.repositorioIncidencia.findIncidenciasAbiertas();
		
	}

	@Override
	public void cancelarIncidencia(String id, String motivo_cierre) throws DataAccessException, EntidadNoEncontrada {

		Bici bici = this.recuperarBiciPorIncidencia(id);
		
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.PENDIENTE)
			throw new IllegalArgumentException("La incidencia con id " + id + " debe de estar en estado PENDIENTE para poder cancelarse");
		
		bici.cancelarIncidencia(id, motivo_cierre);
					
		repositorioIncidencia.save(bici.getIncidencia(id));
		repositorioBici.save(bici);
		
					
	}

	@Override
	public void asignarIncidencia(String id, String operario) throws DataAccessException, EntidadNoEncontrada {

		Bici bici = this.recuperarBiciPorIncidencia(id);
		
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.PENDIENTE)
			throw new IllegalArgumentException("La incidencia con id " + id + " debe de estar en estado PENDIENTE para poder asignarla a un operario");
		
		
		bici.asignarIncidencia(id, operario);
		repositorioIncidencia.save(bici.getIncidencia(id));
		repositorioBici.save(bici);
		
		try {
			
			serviceEstacion.retirarBici(bici.getId());
		} catch (ServicioException e) {
			
			System.out.println(e.getMessage());
		} catch (EntidadNoEncontrada e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void resolverIncidenciaReparada(String id, String motivo_cierre) throws DataAccessException, EntidadNoEncontrada {
		
		Bici bici = this.recuperarBiciPorIncidencia(id);
		
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.ASIGNADA)
			throw new IllegalArgumentException("La incidencia con id "+id+" debe de estar en estado ASIGNADA para poder resolverse");
		
		
		bici.resolverIncidenciaReparada(id, motivo_cierre);
		repositorioIncidencia.save(bici.getIncidencia(id));	
		repositorioBici.save(bici);
		
		try {
			this.serviceEstacion.estacionarBici(bici.getId());

		} catch (EntidadNoEncontrada e) {
			
			System.out.println(e.getMessage());
		} catch (ServicioException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void resolverIncidenciaNoReparada(String id, String motivo_cierre) throws DataAccessException, EntidadNoEncontrada {
		
		Bici bici = this.recuperarBiciPorIncidencia(id);
		
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.ASIGNADA)
			throw new IllegalArgumentException("La incidencia con id "+id+" debe de estar en estado ASIGNADA para poder resolverse");
		
		
		bici.resolverIncidenciaNoReparada(id, motivo_cierre);
		repositorioIncidencia.save(bici.getIncidencia(id));
		repositorioBici.save(bici);
		
		try {
			this.serviceEstacion.darBajaBici(bici.getId(), motivo_cierre);
		
		} catch (EntidadNoEncontrada e) {
			System.out.println(e.getMessage());
			
		} catch (ServicioException e) {
			System.out.println(e.getMessage());
			
		}
	}
	
	private IncidenciaDTO transformToDTOIncidencia(Incidencia incidencia) {        
        return  new IncidenciaDTO(incidencia);       
    }
	
}
