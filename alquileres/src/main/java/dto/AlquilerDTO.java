package dto;

import java.time.LocalDateTime;

import alquileres.dominio.Alquiler;

public class AlquilerDTO {
	
	private String idBicicleta;
	private String inicio;
	private String fin;

	public AlquilerDTO(Alquiler a) {
		
		this.idBicicleta = a.getIdBicicleta();
		this.inicio = a.getInicio() == null ? null : a.getInicio().toString();
		this.fin = a.getFin() == null ? null : a.getFin().toString();
	}
	
	public AlquilerDTO() {
		
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	
	@Override
	public String toString() {
		return "Alquiler [idBicicleta=" + idBicicleta + ", inicio=" + inicio + ", fin=" + fin + "]";
	}
	

}
