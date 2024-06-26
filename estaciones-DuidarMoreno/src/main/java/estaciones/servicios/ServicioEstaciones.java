package estaciones.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.EstadoBici;
import estaciones.dominio.Historico;
import estaciones.dominio.Incidencia;
import estaciones.dominio.SitioTuristico;
import estaciones.dto.BiciDTO;
import estaciones.dto.IncidenciaDTO;
import estaciones.eventos.EventoBici;
import estaciones.eventos.IEventosListener;
import estaciones.repositorios.RepositorioBici;
import estaciones.repositorios.RepositorioEstacion;
import estaciones.repositorios.RepositorioHistorico;
import estaciones.repositorios.RepositorioIncidencia;
import repositorios.EntidadNoEncontrada;
import repositorios.Repositorio;
import repositorios.RepositorioException;
import servicios.ServicioException;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones, IEventosListener{

	private RepositorioEstacion repositorioEstacion;
	
	private RepositorioBici repositorioBici;

	private RepositorioHistorico repositorioHistorico;
	
	private IServicioSitiosTuristicos sitiosService;
	
	private IServicioEventos eventosService;
	
	
	@Autowired
	public ServicioEstaciones(RepositorioEstacion repositorioEstacion,
							RepositorioHistorico repositorioHistorico,
							RepositorioBici repositorioBici,
							IServicioSitiosTuristicos sitiosService,
							IServicioEventos eventosService) {
		
		this.repositorioBici = repositorioBici;
		this.repositorioEstacion = repositorioEstacion;
		this.repositorioHistorico = repositorioHistorico;
		this.sitiosService = sitiosService;
		this.eventosService = eventosService;
		this.eventosService.iniciarOyente(this);
	}
	
	@Override
	public String altaEstacion(String nombre, int num_puestos, String direccion,double latitud, double longitud) throws DataAccessException {
		
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
		
		if(num_puestos<1)
			throw new IllegalArgumentException("num_puestos: tiene que haber al menos un puesto");
		
		if (direccion == null || direccion.isEmpty())
			throw new IllegalArgumentException("direccion: no debe ser nulo ni vacio");
		
		
		Estacion estacion = new Estacion(nombre, num_puestos, direccion, latitud, longitud);
		String id = this.repositorioEstacion.save(estacion).getId();
		
		
		return id;
	}

	@Override
	public Estacion recuperarEstacion(String id) throws DataAccessException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Optional<Estacion> optional = repositorioEstacion.findById(id);
		
		if(!optional.isPresent())
			throw new EntidadNoEncontrada("No existe la estacion con id :" + id);
		
		return optional.get();
	}

	@Override
	public List<SitioTuristico> obtenerSitios(String id) throws Exception {
		
		Estacion estacion = this.recuperarEstacion(id);
		List<SitioTuristico> sitiosCompletos = new LinkedList<>();
		
		List<SitioResumen> sitiosResumen = this.sitiosService.obtenerSitios(estacion.getLatitud(), estacion.getLongitud());
		
		for(SitioResumen idS : sitiosResumen)
			sitiosCompletos.add(this.sitiosService.obtenerSitioCompleto(idS.getNombre()));
		
		return sitiosCompletos;
	}

	@Override
	public void establecerSitios(String id, List<SitioTuristico> sitios) throws DataAccessException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Optional<Estacion> optional = repositorioEstacion.findById(id);
		
		if(!optional.isPresent())
			throw new EntidadNoEncontrada("No existe la estacion con id :" + id);
		
		Estacion estacion = optional.get();
		
		estacion.setSitiosTuristicos(sitios);
		
		this.repositorioEstacion.save(estacion);
		
	}

	@Override
	public String altaBici(String modelo, String id_estacion) throws DataAccessException, EntidadNoEncontrada, ServicioException {
		Bici bici = new Bici(modelo);
		
		Estacion estacion = this.recuperarEstacion(id_estacion);
		
		if(estacion.estaLlena())
			throw new ServicioException("Creación de la bici " +modelo+" cancelada-"
					+ "La estacion no tiene más espacio para bicis");
		
		String id_bici = repositorioBici.save(bici).getId();
		
		this.estacionarBici(id_bici, id_estacion);
		
		return id_bici;
	}

	@Override
	public Bici recuperarBici(String id) throws DataAccessException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Optional<Bici> optional = repositorioBici.findById(id);
		
		if(!optional.isPresent())
			throw new EntidadNoEncontrada("No existe la bici con id :" + id);
		
		return optional.get();
	}

	@Override
	public void estacionarBici(String id_bici, String id_estacion) throws DataAccessException, EntidadNoEncontrada, ServicioException {
		
		Bici bici = this.recuperarBici(id_bici);
		
		if(!bici.esDisponible())
			throw new ServicioException("La bici con id "+ id_bici + " no esta disponible ");
		
		if(bici.getIdEstacion() != null)
			throw new ServicioException("La bici ya está estacionada en la estacion con id: " +  bici.getIdEstacion());
		
		
		Estacion estacion = this.recuperarEstacion(id_estacion);
		
		if(estacion.containsBici(id_bici))
			throw new ServicioException("La bici ya está estacionada en la estacion con id: " +  id_estacion);
		
		
		if(estacion.estacionarBici(id_bici)==false)
			throw new ServicioException("La estación no tiene mas espacio para bicis ");
		
		bici.setIdEstacion(id_estacion);
		Historico hist = new Historico(id_bici, id_estacion);
		
		repositorioHistorico.save(hist);
		repositorioBici.save(bici);
		repositorioEstacion.save(estacion);
		
	}
	
	@Override
	public void estacionarBici(String id_bici) throws DataAccessException, EntidadNoEncontrada, ServicioException {
		String id_estacion = this.buscarEstacion();
		if(id_estacion==null)
			throw new ServicioException("No hay estaciones libres");
		
		this.estacionarBici(id_bici, id_estacion);
		
	}
	
	private String buscarEstacion() throws DataAccessException {
		return repositorioEstacion.findFirstLibre().get(0).getId();
	}

	@Override
	public void retirarBici(String id_bici) throws DataAccessException, EntidadNoEncontrada, ServicioException {
		
		System.out.println("Retirar bici: " + id_bici);
		
		Bici bici = this.recuperarBici(id_bici);
		
		String id_estacion = bici.getIdEstacion();
		
		if(id_estacion == null)
			throw new ServicioException("La bici no está estacionada en ninguna estacion");
		
		Estacion estacion = this.recuperarEstacion(id_estacion);
		
		bici.setIdEstacion(null);
		if (estacion.retirarBici(id_bici) == false)
			throw new ServicioException("La bici no está estacionada en la estacion con id: " +  id_estacion);
		
		Historico hist = repositorioHistorico.findByIdBiciAndIdEstacionAndFechaFinNull(id_bici, id_estacion);
		hist.setFechaFin(LocalDateTime.now());
		
		repositorioHistorico.save(hist);
		Bici nuew = repositorioBici.save(bici);
		System.out.println(bici);
		System.out.println(estacion);
		Estacion es2 = repositorioEstacion.save(estacion);
		System.out.println(es2);
	}

	@Override
	public void darBajaBici(String id, String motivo) throws DataAccessException, EntidadNoEncontrada, ServicioException {
		
		Bici bici = this.recuperarBici(id);
		
		if(bici.esDe_Baja())
			throw new ServicioException("La bici con id "+ id + " ya está dada de baja ");
		
		try{
			this.retirarBici(id);
		} catch (ServicioException e) {
			System.out.println(e.getMessage());
		}
		
		bici.darDeBaja(motivo);
		repositorioBici.save(bici);
		
		this.eventosService.publicarEventoBiciDesactivada(id, LocalDateTime.now());
		
	}

	@Override
	public List<Bici> recuperarBiciPosicion(Double latitud, Double longitud) throws DataAccessException {
		
		List<Estacion> estaciones = repositorioEstacion.findFirst3ByCoordenadasNear(new Point(latitud, longitud));
		
		List<Bici> bicis = new LinkedList<>();
		estaciones.stream()
					.flatMap(e -> e.getIdBicis().stream())
					.map(id -> {
						try {
							return repositorioBici.findById(id).get();
						} catch (DataAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return null;
					})
					.filter(b -> b != null)
					.filter(Bici::esDisponible)
					.forEach(b -> bicis.add(b));
					
					
		return bicis;
	}
	

	@Override
	public List<Estacion> recuperarEstacionPorSitios() throws DataAccessException {
		
		return repositorioEstacion.getEstacionesPorSitios();
	}
	
	// Funciones adicionales para entrega 2
	
	public List<Estacion> recuperarTodasEstaciones() throws DataAccessException {
		
		List<Estacion> estaciones = new LinkedList<>();
		
		repositorioEstacion.findAll().forEach(e -> estaciones.add(e));
		
		return estaciones;
	}
	
	public Page<Estacion> recuperarTodasEstacionesPaginado(Pageable pageable) throws DataAccessException {
		
		Page<Estacion> estaciones = repositorioEstacion.findAll(pageable);
		
		return estaciones;
	}
	
	public Page<Estacion> recuperarEstacionesPaginadoFiltro(String like, 
			int numPuestos, Pageable pageable) throws DataAccessException{
		
		if(numPuestos > 0) {
			return repositorioEstacion.findByNombreAndNumPuestos(like, numPuestos, pageable);
		}
		
		return repositorioEstacion.findByNombreLike(like, pageable);
	}
	
	
	// ¿a lo mejor crear un metodo para usuarios normales y otro para gestores?
	public List<Bici> getBicisFromEstacion(String id_estacion) throws DataAccessException, EntidadNoEncontrada{
		
		List<Bici> bicis = this.repositorioBici.findByIdEstacion(id_estacion);
		
		
		return bicis;
	}
	
	public Page<Bici> getBicisFromEstacionPaginado(String id_estacion, Pageable pageable) throws DataAccessException, EntidadNoEncontrada{
		
		Page<Bici> bicis = this.repositorioBici.findByIdEstacion(id_estacion, pageable);
		
		return bicis;
	}
	
	public Page<Bici> getBicisDisponiblesFromEstacionPaginado(String id_estacion, Pageable pageable) throws DataAccessException, EntidadNoEncontrada{
		
		Page<Bici> bicis = this.repositorioBici.findByEstadoAndIdEstacion(EstadoBici.DISPONIBLE, id_estacion, pageable);
		
		return bicis;
	}

	@Override
	public void biciAlquilada(EventoBici evento) throws Exception {
		this.retirarBici(evento.getIdBici());
		
		Bici bici = this.recuperarBici(evento.getIdBici());
		bici.setEstado(EstadoBici.NO_DISPONIBLE);
		this.repositorioBici.save(bici);
	}

	@Override
	public void alquilerFin(EventoBici evento) throws Exception {
		Bici bici = this.recuperarBici(evento.getIdBici());
		
		bici.setEstado(EstadoBici.DISPONIBLE);
		this.repositorioBici.save(bici);
		
		this.estacionarBici(bici.getId(), evento.getFecha());
	}

	
	@Override
	public void biciReservada(EventoBici evento) throws Exception {
		Bici bici = this.recuperarBici(evento.getIdBici());
		bici.setEstado(EstadoBici.NO_DISPONIBLE);
		
		this.repositorioBici.save(bici);
		
	}

	@Override
	public void reservaFin(EventoBici evento) throws Exception {
		
		this.retirarBici(evento.getIdBici());
		
	}
	
	
}
