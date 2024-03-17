package estaciones.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.dominio.Bici;
import estaciones.dominio.EstadoBici;
import estaciones.dominio.EstadoIncidencia;
import estaciones.dominio.Incidencia;
import estaciones.dto.IncidenciaDTO;
import estaciones.repositorios.RepositorioBiciAdHoc;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.ServicioException;

@Service
@Transactional
public class ServicioIncidencias implements IServicioIncidencias {
	
	@Autowired
	private RepositorioBiciAdHoc repositorioBici;
	
	@Autowired
	private IServicioEstaciones serviceEstacion;
	
	@Override
	public void crearIncidencia(String id_bici, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		if (id_bici == null)
	        throw new IllegalArgumentException("id_Bici: no debe ser nulo");
		
		if (descripcion == null || descripcion.isEmpty())
	        throw new IllegalArgumentException("descripcion: no debe ser nula");
		
		Bici bici = this.repositorioBici.getById(id_bici);
		
		if(bici.esDe_Baja() || ! bici.esDisponible())
			throw new IllegalArgumentException("La bici está dada de baja, ne se pueden crear incidencias en ella");
		
		Incidencia incidencia = new Incidencia(bici, descripcion);
		bici.addIncidencia(incidencia);
		
		repositorioBici.update(bici);
		
	}

	@Override
	public IncidenciaDTO recuperarIncidencia(String id) throws RepositorioException, EntidadNoEncontrada {
		
		return transformToDTOIncidencia(repositorioBici.getIncidenciaById(id));
		
	}

	@Override
	public List<Incidencia> recuperarIncidenciasAbiertas() throws RepositorioException {

		/*LinkedList<Incidencia> lista = new LinkedList<Incidencia>();
		List<Bici> bicis = repositorioBici.getAll();
		
		for (Bici bici : bicis) {
			ArrayList<Incidencia> incidencias = bici.getIncidencias();
			for (Incidencia incidencia : incidencias) {
				if (incidencia.getEstado() == EstadoIncidencia.PENDIENTE)
					lista.add(incidencia);
			}
		}*/
		
		return this.repositorioBici.getIncidenciasAbiertas();
		
	}

	@Override
	public void cancelarIncidencia(String id, String motivo_cierre) throws RepositorioException, EntidadNoEncontrada {

		
		Bici bici = this.repositorioBici.getBiciByIncidencia(id);
		
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.PENDIENTE)
			throw new IllegalArgumentException("La incidencia con id " + id + " debe de estar en estado PENDIENTE para poder cancelarse");
		
		bici.cancelarIncidencia(id, motivo_cierre);
					
		repositorioBici.update(bici);
					
	}

	@Override
	public void asignarIncidencia(String id, String operario) throws RepositorioException, EntidadNoEncontrada {

		Bici bici = this.repositorioBici.getBiciByIncidencia(id);
		
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.PENDIENTE)
			throw new IllegalArgumentException("La incidencia con id " + id + " debe de estar en estado PENDIENTE para poder asignarla a un operario");
		
		
		bici.asignarIncidencia(id, operario);
		
		repositorioBici.update(bici);
		
		try {
			
			serviceEstacion.retirarBici(bici.getId());
		} catch (ServicioException e) {
			
			System.out.println(e.getMessage());
		} catch (EntidadNoEncontrada e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void resolverIncidenciaReparada(String id, String motivo_cierre) throws RepositorioException, EntidadNoEncontrada {
		
		Bici bici = this.repositorioBici.getBiciByIncidencia(id);
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.ASIGNADA)
			throw new IllegalArgumentException("La incidencia con id "+id+" debe de estar en estado ASIGNADA para poder resolverse");
		
		
		bici.resolverIncidenciaReparada(id, motivo_cierre);
					
		repositorioBici.update(bici);
		
		try {
			this.serviceEstacion.estacionarBici(bici.getId());

		} catch (EntidadNoEncontrada e) {
			
			System.out.println(e.getMessage());
		} catch (ServicioException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void resolverIncidenciaNoReparada(String id, String motivo_cierre) throws RepositorioException, EntidadNoEncontrada {
		
		Bici bici = this.repositorioBici.getBiciByIncidencia(id);
		if (bici.getEstadoIncidencia(id) != EstadoIncidencia.ASIGNADA)
			throw new IllegalArgumentException("La incidencia con id "+id+" debe de estar en estado ASIGNADA para poder resolverse");
		
		
		bici.resolverIncidenciaNoReparada(id, motivo_cierre);
					
		repositorioBici.update(bici);
		
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
	
	public List<IncidenciaDTO> recuperarIncidenciasAbiertasLazy(int start, int max) throws ServicioException{
	 
	    List<Incidencia> incidencias;
	    try {
	    	incidencias = repositorioBici.getIncidenciasAbiertasLazy(start, max);
	        List<IncidenciaDTO> incidenciasDTO = new ArrayList<>();
	        for(Incidencia i:incidencias) {
	        	incidenciasDTO.add(transformToDTOIncidencia(i));
	        }
	        return incidenciasDTO;
	    } catch (RepositorioException e) {
	        e.printStackTrace();
	        throw new ServicioException(e.getMessage(), e);
	    }
	}

	public int countIncidenciasAbiertas() throws ServicioException{
	    try {
	    	Number numero = repositorioBici.countIncidenciasAbiertas();
	        return numero==null?0:numero.intValue();
	    } catch (RepositorioException e) {
	        e.printStackTrace();
	        throw new ServicioException(e.getMessage(), e);
	    }
	}

}
