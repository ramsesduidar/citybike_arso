package estaciones.dto;

import estaciones.dominio.Bici;
import estaciones.dominio.EstadoBici;

public class BiciDTO {
	
	private String id;
	
	private String modelo;
	
	private EstadoBici estado;

	private String idEstacion;
	
	public BiciDTO (Bici bici) {
		
		super();
		this.id = bici.getId();
		this.modelo = bici.getModelo();
		this.estado = bici.getEstado();
		this.idEstacion = bici.getIdEstacion();
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
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
}
