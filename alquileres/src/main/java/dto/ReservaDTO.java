package dto;

import java.time.LocalDateTime;

import alquileres.dominio.Reserva;

public class ReservaDTO {
	
	private String idBicicleta;
	private String creada;
	private String caducidad;

	public ReservaDTO(Reserva r) {
		this.idBicicleta = r.getIdBicicleta();
		this.creada = r.getCreada() == null ? null : r.getCreada().toString();
		this.caducidad = r.getCaducidad() == null ? null : r.getCaducidad().toString();
	}
	
	public ReservaDTO() {
		
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public String getCreada() {
		return creada;
	}

	public void setCreada(String creada) {
		this.creada = creada;
	}

	public String getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	
	@Override
	public String toString() {
		return "Reserva [idBicicleta=" + idBicicleta + ", creada=" + creada + ", caducidad=" + caducidad + "]";
	}

}
