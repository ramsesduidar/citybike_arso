package alquileres.servicios;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import alquileres.dominio.Reserva;
import alquileres.dominio.Usuario;
import alquileres.repositorios.RepositorioUsuarios;
import dto.UsuarioDTO;
import eventos.EventoBici;
import eventos.IEventosListener;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.Repositorio;
import repositorios.RepositorioException;
import servicios.FactoriaServicios;

public class ServicioAlquileres implements IServicioAlquileres, IEventosListener{
	
	private RepositorioUsuarios repo = FactoriaRepositorios.getRepositorio(Usuario.class);
	private IServicioEstaciones service = FactoriaServicios.getServicio(IServicioEstaciones.class);
	private IServicioEventos eventosService = FactoriaServicios.getServicio(IServicioEventos.class);
	
	public ServicioAlquileres() {
		try {
			eventosService.subscribirseEventoBiciDesactivada(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * reservar(idUsuario, idBicicleta). Esta operación está a cargo de crear una reserva 
	 * para una bicicleta. Los identificadores son cadena de texto. La fecha de creación 
	 * corresponde al momento de la solicitud y la fecha de caducidad es 30 minutos 
	 * más tarde. Requisitos:
		No está permitida la reserva si el usuario tiene una reservaActiva.
		No está permitida la reserva si el usuario tiene un alquiler activo.
		No está permitida la reserva si el usuario está bloqueado o superaTiempo.
	 */
	@Override
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada {
		
		Usuario user = this.recuperarUsuario(idUsuario);
		
		if(user.reservaActiva() != null)
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "ya tiene una reserva activa");
		
		if(user.alquilerActivo() != null)
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "ya tiene un alquiler activo");
		
		if(user.bloqueado())
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "está bloqueado");
		
		if(user.superaTiempo())
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "ha superado el tiempo máximo de uso");
		
		user.addReserva(idBici, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
		repo.update(user);
		
	}

	/*
	 * confirmarReserva(idUsuario). Esta operación confirma la reservaActiva del usuario 
	 * creando un alquiler. La propiedad inicio del alquiler corresponde al momento 
	 * de la confirmación. Un alquiler recién creado no tiene valor para la propiedad fin. 
	 * Por último, esta operación elimina la reserva. 
	 * Requisitos:
		El usuario tiene una reservaActiva.
	 */
	@Override
	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		Usuario user = this.recuperarUsuario(idUsuario);
		
		Reserva activa = user.reservaActiva(); 
		if(activa == null)
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "no tiene ninguna reserva activa");
		
		LocalDateTime fecha = LocalDateTime.now();
		user.addAlquiler(activa.getIdBicicleta(), fecha);
		
		user.deleteReservaActiva();
		
		repo.update(user);
		
		try {
			this.eventosService.publicarEventoBiciAlquilada(activa.getIdBicicleta(), fecha);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * alquilar(idUsuario, idBicicleta). Crea el alquiler, sin reserva previa, 
	 * de una bicicleta por parte de un usuario. La propiedad inicio del alquiler 
	 * corresponde al momento de la confirmación. En un alquiler recién creado 
	 * no tiene valor la propiedad fin. 
	 * Requisitos:
		El usuario no tiene una reservaActiva.
		El usuario no tiene un alquilerActivo.
		No está permitido el alquiler si el usuario está bloqueado o superaTiempo.
	 */
	@Override
	public void alquilar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada {
		
		Usuario user = this.recuperarUsuario(idUsuario);
		
		if(user.reservaActiva() != null)
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "ya tiene una reserva activa");
		
		if(user.alquilerActivo() != null)
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "ya tiene un alquiler activo");
		
		if(user.bloqueado())
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "está bloqueado");
		
		if(user.superaTiempo())
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "ha superado el tiempo máximo de uso");
		
		LocalDateTime fecha = LocalDateTime.now();
		user.addAlquiler(idBici, fecha);
		
		repo.update(user);
		
		try {
			this.eventosService.publicarEventoBiciAlquilada(idBici, fecha);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * historialUsuario(idUsuario): Usuario. Retorna la información con los alquileres 
	 * y reservas del usuario, y el estado del servicio (bloqueado, tiempo de uso).
	 */
	@Override
	public Usuario historialUsuario(String idUsuario) throws RepositorioException {
		
		Usuario user = this.recuperarUsuario(idUsuario);
		
		
		return user;
	}

	/*
	 * dejarBicicleta(idUsuario, idEstacion). La operación se encarga de concluir el 
	 * alquilerActivo del usuario estableciendo como fecha de fin el instante actual. 
	 * Además, sitúa la bicicleta en la estación. 
	 * Requisitos:
		El usuario tiene un alquilerActivo.
		La estación tiene un hueco disponible para el estacionamiento.
	 */
	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		
		Usuario user = this.recuperarUsuario(idUsuario);
		
		
		if(user.alquilerActivo() == null)
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "no tiene ningun alquiler activo");
		
		if(!service.hayEspacioLibre(idEstacion))
			throw new IllegalArgumentException("No hay huecos libres"
					+ "en la estacion con id: " + idEstacion);
		
		String idBici = user.terminarAlquilerActivo();
		
		service.estacionarBici(idEstacion, idBici);
		
		System.out.println("llamo a repo despues de dejar bici");
		repo.update(user);
		
		try {
			this.eventosService.publicarEventoBiciAlquilerFin(idBici, LocalDateTime.now());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * liberarBloqueo(idUsuario). Esta operación elimina todas las reservas caducadas 
	 * de un usuario.
	 */
	@Override
	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		
		Usuario user = this.recuperarUsuario(idUsuario);
		
		if(!user.bloqueado())
			throw new IllegalArgumentException("El usuario con id: " + idUsuario
					+ "no está bloqueado");
		
		user.liberarBloqueo();
		
		repo.update(user);
	}
	
	private Usuario recuperarUsuario(String idUsuario) throws RepositorioException {
		
		Usuario user;
		try {
			user = repo.getById(idUsuario);
		} catch (EntidadNoEncontrada e) {
			user = new Usuario(idUsuario);
			repo.add(user);
		}
		
		return user;
	}

	@Override
	public void biciDesactivada(EventoBici evento) throws Exception {
		
		//this.reservar("33", evento.getIdBici()); //para pruebas
		System.out.println("Alquileres listener activado");
		Usuario user = this.repo.getUsuarioByReservaActiva(evento.getIdBici(), evento.getFecha());
		if (user != null) {
			user.deleteReservaActiva();
			repo.update(user);
		}
		
	}

}
