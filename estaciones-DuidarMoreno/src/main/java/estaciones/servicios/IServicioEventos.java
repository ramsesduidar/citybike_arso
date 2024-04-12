package estaciones.servicios;

import java.time.LocalDateTime;

import estaciones.eventos.IEventosListener;

public interface IServicioEventos {
	
	void publicarEventoBiciDesactivada(String idBici, LocalDateTime fecha);
	
	void iniciarOyente(IEventosListener listener);

}
