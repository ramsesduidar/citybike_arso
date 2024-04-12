package eventos;

import java.time.LocalDateTime;

public class EventoBici {
	
	String idBici;
	
	LocalDateTime fecha;

	public EventoBici(String idBici, LocalDateTime fecha) {
		this.idBici = idBici;
		this.fecha = fecha;
	}
	
	
	public EventoBici() {
		
	}


	public String getIdBici() {
		return idBici;
	}


	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	
	

}
