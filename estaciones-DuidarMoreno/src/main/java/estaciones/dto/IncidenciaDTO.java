package estaciones.dto;

import java.time.LocalDate;

import estaciones.dominio.Bici;
import estaciones.dominio.EstadoIncidencia;
import estaciones.dominio.Incidencia;

public class IncidenciaDTO {
	
	private String id;
	
	private String id_bici;
	
	private String descripcion;
	
	private LocalDate fechaCreacion;
	
	private EstadoIncidencia estado;
	
	private String nombreOperario;
	

	public IncidenciaDTO(Incidencia incidencia) {
		super();
		this.id = incidencia.getId();
		this.id_bici = incidencia.getBici().getId();
		this.descripcion = incidencia.getDescripcion();
		this.fechaCreacion = incidencia.getFechaCreacion();
		this.estado = incidencia.getEstado();
		this.nombreOperario = incidencia.getNombreOperario();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_bici() {
		return id_bici;
	}

	public void setId_bici(String id_bici) {
		this.id_bici = id_bici;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EstadoIncidencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	public String getNombreOperario() {
		return nombreOperario;
	}

	public void setNombreOperario(String nombreOperario) {
		this.nombreOperario = nombreOperario;
	}
	
	
}
