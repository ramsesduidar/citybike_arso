package alquileres.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
	
	private String idBicicleta;
	private LocalDateTime creada;
	private LocalDateTime caducidad;
	
	public Reserva() {
		
	}
	
	public Reserva(String idBicicleta, LocalDateTime f1, LocalDateTime f2) {
		this.idBicicleta = idBicicleta;
		this.creada = f1;
		this.caducidad = f2;
	}

	public boolean caducada() {
		return LocalDateTime.now().isAfter(caducidad);
	}
	
	public boolean activa() {
		return !caducada();
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDateTime getCreada() {
		return creada;
	}

	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}

	public LocalDateTime getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}

	@Override
	public String toString() {
		return "Reserva [idBicicleta=" + idBicicleta + ", creada=" + creada + ", caducidad=" + caducidad + "]";
	}
	
	
}
