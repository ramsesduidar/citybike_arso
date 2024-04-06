package estaciones.dto;

import estaciones.dominio.Bici;
import estaciones.dominio.EstadoBici;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="DTO de la entidad Bici")
public class BiciDTO {
	
	@Schema(description ="Identificador de la Bici")
	private String id;
	
	@Schema(description ="Modelo de la Bici")
	private String modelo;
	
	@Schema(description ="Estado de la Bici", 
			example = "DISPONIBLE")
	private EstadoBici estado;

	@Schema(description ="Identificador de la Estacion en la que se encuentra la Bici")
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
