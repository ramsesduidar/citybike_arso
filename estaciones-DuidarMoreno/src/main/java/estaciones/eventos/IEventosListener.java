package estaciones.eventos;

public interface IEventosListener {

	void biciReservada(EventoBici evento) throws Exception;
	void reservaFin(EventoBici evento) throws Exception;
	void biciAlquilada(EventoBici evento) throws Exception;
	void alquilerFin(EventoBici evento) throws Exception;
}
