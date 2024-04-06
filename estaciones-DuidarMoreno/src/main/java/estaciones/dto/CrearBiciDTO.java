package estaciones.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="DTO para especificar los datos al crear una Bici")
public class CrearBiciDTO {
	
	@Schema(description ="Identificador de la Estacion en la que añadir la Bici")
	@NotNull
	private String idEstacion;
	
	@Schema(description ="Modelo de la Bici")
	@NotBlank
	private String modelo;

	public CrearBiciDTO(String idEstacion, String modelo) {
		this.idEstacion = idEstacion;
		this.modelo = modelo;
	}
	
	public CrearBiciDTO() {
		
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	
	

}
