package estaciones.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import repositorios.Identificable;

@Document(collection ="bici")
public class Bici implements Identificable{

	@Id
	private String id;
	
	private String modelo;
	
	private LocalDate fechaAlta;
	
	private LocalDate fechaBaja;
	
	private String motivo;
	
	private EstadoBici estado;
	
	private String idEstacion;
	
	@DocumentReference
	private List<Incidencia> incidencias;

	
	public Bici() {
		
	}

	public Bici(String modelo) {
		this.modelo = modelo;
		this.fechaAlta = LocalDate.now();
		this.estado = EstadoBici.DISPONIBLE;
		this.incidencias = new LinkedList<>();
	}
	
	public List<Incidencia> getIncidencias() {
		return incidencias;
	}
	
	public void setIncidencias(ArrayList<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public EstadoBici getEstado() {
		return estado;
	}

	public void setEstado(EstadoBici estado) {
		this.estado = estado;
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public void darDeBaja(String motivo2) {
		this.fechaBaja = LocalDate.now();
		this.motivo = motivo2;
		this.estado = EstadoBici.DE_BAJA;
		
	}

	public boolean esDisponible() {
		return this.estado.equals(EstadoBici.DISPONIBLE);
	}
	
	public boolean esDe_Baja() {
		return this.estado.equals(EstadoBici.DE_BAJA);
	}

	public void addIncidencia(Incidencia incidencia) {
		if(!this.containsIncidencia(incidencia.getId())) {
			
			this.incidencias.add(incidencia);
			this.estado = EstadoBici.NO_DISPONIBLE;
		}
		
	}
	
	private boolean containsIncidencia(String id) {
		if(id==null) return false;
		return this.incidencias.stream().anyMatch(i -> i.getId().equals(id));
	}
	
	public Incidencia getIncidencia(String idI) {
		for (Incidencia incidencia : incidencias)
			if (incidencia.getId().equals(idI))
				return incidencia;
		
		return null;
	}

	public void cancelarIncidencia(String id2, String motivo_cierre) {
		Incidencia incidencia = this.getIncidencia(id2);
		if (incidencia != null) {
				
			incidencia.setFechaCierre(LocalDate.now());
			incidencia.setEstado(EstadoIncidencia.CANCELADA);
			incidencia.setMotivoCierre(motivo_cierre);
				
			this.estado = EstadoBici.DISPONIBLE;
			
		}
		
	}
	
	public void asignarIncidencia(String id2, String operario) {
		Incidencia incidencia = this.getIncidencia(id2);
		if (incidencia != null) {
	
			incidencia.setEstado(EstadoIncidencia.ASIGNADA);
			incidencia.setNombreOperario(operario);	
			
		}
		
	}
	public void resolverIncidenciaReparada(String id2, String motivo_cierre) {
		Incidencia incidencia = this.getIncidencia(id2);
		if (incidencia != null) {
				
			incidencia.setEstado(EstadoIncidencia.RESUELTA);
			incidencia.setFechaCierre(LocalDate.now());
			incidencia.setMotivoCierre(motivo_cierre);
				
			this.estado = EstadoBici.DISPONIBLE;
			
		}
		
	}
	public void resolverIncidenciaNoReparada(String id2, String motivo_cierre) {
		Incidencia incidencia = this.getIncidencia(id2);
		if (incidencia != null) {
				
			incidencia.setEstado(EstadoIncidencia.RESUELTA);
			incidencia.setFechaCierre(LocalDate.now());
			incidencia.setMotivoCierre(motivo_cierre);
			
			
		}
		
	}

	public EstadoIncidencia getEstadoIncidencia(String id2) {
		Incidencia incidencia = this.getIncidencia(id2);
		if (incidencia != null) {
			return incidencia.getEstado();
		}
		return null;
	}
	
	
}
