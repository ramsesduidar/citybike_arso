package estaciones.dto;

import java.time.LocalDateTime;

import org.springframework.data.geo.Point;

import estaciones.dominio.Estacion;


public class EstacionDTO {

	private String id;
	
	private String nombre;
	
	private LocalDateTime fechaDeAlta;
	
	private int numPuestos;
	
	private int huecosLibres;
	
	private String direccion;
	
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
