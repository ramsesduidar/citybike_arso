package dto;

import java.time.LocalDateTime;



public class EstacionDTO {

	
	private String id;
	
	
	private String nombre;
	
	
	private String fechaDeAlta;
	
	
	private int numPuestos;
	
	
	private int huecosLibres;
	
	
	private String direccion;
	
	

	
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

	public String getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(String fechaDeAlta) {
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

	@Override
	public String toString() {
		return "EstacionDTO [id=" + id + ", nombre=" + nombre + ", fechaDeAlta=" + fechaDeAlta + ", numPuestos="
				+ numPuestos + ", huecosLibres=" + huecosLibres + ", direccion=" + direccion + "]";
	}
	
	

	
}
