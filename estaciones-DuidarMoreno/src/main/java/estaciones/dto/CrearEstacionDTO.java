package estaciones.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="DTO para especificar los datos al crear una Estacion")
public class CrearEstacionDTO {
	
	@Schema(description ="Nombre de la Estacion")
	@NotBlank
	private String nombre;
	
	@Schema(description ="Numero de puestos de la Estacion")
	@NotNull
	@Positive
	private int numPuestos; 
	
	@Schema(description ="Direccion de la Estacion")
	private String direccion;
	
	@Schema(description ="Latitud de la Estacion para crear las coordenadas")
	@NotNull
	private long latitud;
	
	@Schema(description ="Longitud de la Estacion para crear las coordenadas")
	@NotNull
	private long longitud;

	public CrearEstacionDTO(String nombre, int numPuestos, String direccion, long latitud, long longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public CrearEstacionDTO() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getLatitud() {
		return latitud;
	}

	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}

	public long getLongitud() {
		return longitud;
	}

	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}
	
	
	
	

}
