package alquileres.servicios;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import eventos.EventoBici;
import eventos.IEventosListener;

public class ServicioEventosRabbitMQ implements IServicioEventos{
	
	private ConnectionFactory factory;
	
	public ServicioEventosRabbitMQ() throws Exception{
		factory = new ConnectionFactory();
		factory.setUri("amqps://gsjdktco:VsVS3S5wsqIHdqnvWb27kSFlmjJ6UpwA@rat.rmq2.cloudamqp.com/gsjdktco");
	}
	
	@Override
	public void publicarEventoBiciAlquilada(String idBici, LocalDateTime fechaAlquiler) throws Exception{
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String mensaje = "{\"idBici\":\""+idBici+"\",\"fecha\":\""+fechaAlquiler.toString()+"\"}";
		String routingkey = "cityBike.alquileres.bicicleta-alquilada";
		channel.basicPublish("cityBike", routingkey,
				new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(),
				mensaje.getBytes());
		
		channel.close();
		connection.close();
		
	}

	@Override
	public void publicarEventoBiciAlquilerFin(String idBici, LocalDateTime fechaFinAlquiler) throws Exception{
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String mensaje = "{\"idBici\":\""+idBici+"\",\"fecha\":\""+fechaFinAlquiler.toString()+"\"}";
		String routingkey = "cityBike.alquileres.bicicleta-alquiler-concluido";
		channel.basicPublish("cityBike", routingkey,
				new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(),
				mensaje.getBytes());
		
		channel.close();
		connection.close();
		
	}

	@Override
	public void subscribirseEventoBiciDesactivada(IEventosListener listener) throws Exception{
		
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		//Consumidor

		boolean autoAck = false;
		String cola = "cityBike-alquileres";
		String etiquetaConsumidor = "alquileres-consumidor";

		channel.basicConsume(cola, autoAck, etiquetaConsumidor,
				
				new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope,
							AMQP.BasicProperties properties, byte[] body) throws IOException {
						
						String routingKey = envelope.getRoutingKey();
						String contentType = properties.getContentType();
						long deliveryTag = envelope.getDeliveryTag();
						
						String contenido = new String(body);
						
						System.out.println("contenido:");
						System.out.println(contenido);
						System.out.println("routing key");
						System.out.println(routingKey);
					
						try {
							Gson gson = new GsonBuilder()
					                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
					                .create();
							EventoBici evento = gson.fromJson(contenido, EventoBici.class);
						
							System.out.println("Evento: " + evento.getIdBici());
							
							if(routingKey.endsWith("bicicleta-desactivada"))
								listener.biciDesactivada(evento);
							
						}catch(Exception e) {
							e.printStackTrace();
						}
			            
						// Confirma el procesamiento
						channel.basicAck(deliveryTag, false);
					}
		});

		
		
	}
	
	public static void main(String[] args) throws Exception {
		ServicioEventosRabbitMQ service = new ServicioEventosRabbitMQ();
		service.publicarEventoBiciAlquilada("65f9f7299f56956a7f851071", LocalDateTime.now());
		service.subscribirseEventoBiciDesactivada((evento) -> {
			System.out.println("Listener: "+evento.getIdBici() + evento.getFecha());
		});
	}

	private class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
	    private final DateTimeFormatter formatter;

	    public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
	        this.formatter = formatter;
	    }

	    @Override
	    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	        String fechaStr = json.getAsString();
	        return LocalDateTime.parse(fechaStr, formatter);
	    }

		
	}
	
}


