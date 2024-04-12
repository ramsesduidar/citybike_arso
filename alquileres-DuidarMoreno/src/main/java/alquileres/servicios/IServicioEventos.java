package alquileres.servicios;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import eventos.EventoBici;
import eventos.IEventosListener;

/* Somos Alquiler
-Alquileres: emite el evento "bicicleta-alquilada" cuando se realiza un alquiler.
	Estaciones: procesa el evento estableciendo el estado "no disponible" de la bicicleta.
-Alquileres: emite el evento "bicicleta-alquiler-concluido" cuando concluye el alquiler.
	Estaciones: procesa el evento estableciendo la bici como "disponible".
-Estaciones: emite el evento "bicicleta-desactivada" cuando se da de baja una bicicleta.
	Alquileres: comprueba si un usuario tiene una reserva activa de esa bicicleta y la elimina.
 */
public interface IServicioEventos {
	
	void publicarEventoBiciAlquilada(String idBici, LocalDateTime fechaAlquiler) throws Exception;
	
	void publicarEventoBiciAlquilerFin(String idBici, LocalDateTime fechaFinAlquiler) throws Exception;
	
	void subscribirseEventoBiciDesactivada(IEventosListener listener) throws Exception;
}
