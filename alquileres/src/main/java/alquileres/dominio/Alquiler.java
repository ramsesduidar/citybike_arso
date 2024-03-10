package alquileres.dominio;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Alquiler {

	private String idBicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	
	
	
	public Alquiler(String idBicicleta, LocalDateTime f) {
		
		this.idBicicleta = idBicicleta;
		this.inicio = f;
		
	}

	public Alquiler() {
		
	}

	public boolean activo() {
		return fin == null;
	}
	
	public int tiempo() {
		
		LocalDateTime dateTime2 = activo() ? LocalDateTime.now() : fin;
		
      	return (int) Duration.between(inicio, dateTime2).toMinutes();

	}
	
	public String terminar() {
	
		this.fin = LocalDateTime.now();
		
		return this.idBicicleta;
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	@Override
	public String toString() {
		return "Alquiler [idBicicleta=" + idBicicleta + ", inicio=" + inicio + ", fin=" + fin + "]";
	}
	
	
	
}
