package estaciones.servicios;

import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.EstacionesApp;
import estaciones.eventos.EventoBici;
import estaciones.eventos.IEventosListener;
import estaciones.eventos.RabbitMQConfig;
import estaciones.repositorios.RepositorioBici;

@Service
@Transactional
public class ServicioEventosRabbitMQ implements IServicioEventos{

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
	
	private IEventosListener listener;
	
	@Override
	public void publicarEventoBiciDesactivada(String idBici, LocalDateTime fecha) {
		
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.EXCHANGE_NAME,
				"cityBike.estaciones.bicicleta-desactivada",
				new EventoBici(idBici, fecha));
		
		
	}
	
	public void iniciarOyente(IEventosListener listener) {
        
		this.listener = listener;
		
		// Obtener el identificador del oyente
        String listenerId = "estaciones";

        // Iniciar el oyente manualmente
        rabbitListenerEndpointRegistry.getListenerContainers().forEach( System.out::println );
    }
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, autoStartup = "false", id="estaciones")
	public void handleEvent(Message mensaje, EventoBici evento) throws Exception {
		System.out.println("Mensaje recibido: " + mensaje);
		
		String body = new String(mensaje.getBody());
		String routingKey = mensaje.getMessageProperties().getReceivedRoutingKey();
		
		System.out.println("Evento:" + body + " routingkey: " + routingKey);
		System.out.println("Evento-idBici: " + evento.getIdBici());
		
		if (routingKey.endsWith("bicicleta-alquilada"))
			listener.biciAlquilada(evento);
			
		
		if (routingKey.endsWith("bicicleta-alquiler-concluido"))
			listener.alquilerFin(evento);
		
	}
	
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext contexto =
				SpringApplication.run(EstacionesApp.class, args);
		IServicioEventos service = contexto.getBean(IServicioEventos.class);
		service.publicarEventoBiciDesactivada("23", LocalDateTime.now());
		
	}

}
