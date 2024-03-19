package estaciones.dominio;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import repositorios.Identificable;

@Document(collection ="incidencia")
public class Incidencia implements Identificable{

	@Id
	private String id;
	
	@DocumentReference(lazy=true)
	private Bici bici;
	
	private String descripcion;
	
	private LocalDate fechaCreacion;
	
	private EstadoIncidencia estado;
	
	private String motivoCierre;
	
	private LocalDate fechaCierre;
	
	private String nombreOperario;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
		
	}

	public Bici getBici() {
		return bici;
	}

	public void setBici(Bici bici) {
		this.bici = bici;
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

	public String getMotivoCierre() {
		return motivoCierre;
	}

	public void setMotivoCierre(String motivoCierre) {
		this.motivoCierre = motivoCierre;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getNombreOperario() {
		return nombreOperario;
	}

	public void setNombreOperario(String nombreOperario) {
		this.nombreOperario = nombreOperario;
	}
	
	public Incidencia () {
		
	}
	
	public Incidencia (Bici bici, String descripcion) {
		
		this.bici = bici;
		this.descripcion = descripcion;
		this.estado = EstadoIncidencia.PENDIENTE;
		this.fechaCreacion = LocalDate.now();
		
	}
	
	
	
	
}
