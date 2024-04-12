package estaciones.eventos;

public interface IEventosListener {

	void biciAlquilada(EventoBici evento) throws Exception;
	void alquilerFin(EventoBici evento) throws Exception;
}
