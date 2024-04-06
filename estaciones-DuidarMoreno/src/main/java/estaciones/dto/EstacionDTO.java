package estaciones.dto;

import java.time.LocalDateTime;

import org.springframework.data.geo.Point;

import estaciones.dominio.Estacion;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="DTO de la entidad Estacion")
public class EstacionDTO {

	@Schema(description ="Identificador de la Estacion")
	private String id;
	
	@Schema(description ="Nombre de la Estacion")
	private String nombre;
	
	@Schema(description ="Fecha de creación de la Estacion")
	private LocalDateTime fechaDeAlta;
	
	@Schema(description ="Puestos totales de la Estacion")
	private int numPuestos;
	
	@Schema(description ="Numero de puestos libres en la Estacion")
	private int huecosLibres;
	
	@Schema(description ="Dirección de la Estacion")
	private String direccion;
	
	@Schema(description ="Coordenadas geográficas de la Estacion")
	private Point coordenadas;

	public EstacionDTO(Estacion estacion) {
		this.id = estacion.getId();
		this.nombre = estacion.getNombre();
		this.fechaDeAlta = estacion.getFechaDeAlta();
		this.numPuestos = estacion.getNumPuestos();
		this.huecosLibres = estacion.huecosLibres();
		this.direccion = estacion.getDireccion();
		this.coordenadas = estacion.getCoordenadas();
	}
	
	public EstacionDTO() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDateTime getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(LocalDateTime fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public int getHuecosLibres() {
		return huecosLibres;
	}

	public void setHuecosLibres(int huecosLibres) {
		this.huecosLibres = huecosLibres;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Point getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Point coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	
	
}
