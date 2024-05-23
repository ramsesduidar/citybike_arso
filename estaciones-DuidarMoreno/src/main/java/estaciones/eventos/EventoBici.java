package estaciones.eventos;

import java.time.LocalDateTime;

public class EventoBici {
	
	String idBici;
	
	String fecha;

	public EventoBici(String idBici, String fecha) {
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


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	

}
