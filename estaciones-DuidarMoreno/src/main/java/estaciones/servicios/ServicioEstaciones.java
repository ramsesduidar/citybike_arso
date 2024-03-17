package estaciones.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.Historico;
import estaciones.dominio.Incidencia;
import estaciones.dominio.SitioTuristico;
import estaciones.dto.BiciDTO;
import estaciones.dto.IncidenciaDTO;
import estaciones.repositorios.RepositorioBiciAdHoc;
import estaciones.repositorios.RepositorioEstacionAdHoc;
import estaciones.repositorios.RepositorioHistoricoAdHoc;
import repositorios.EntidadNoEncontrada;
import repositorios.Repositorio;
import repositorios.RepositorioException;
import servicios.ServicioException;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones{

	@Autowired
	private RepositorioEstacionAdHoc repositorioEstacion;
	
	@Autowired
	private RepositorioBiciAdHoc repositorioBici;
	
	@Autowired
	private RepositorioHistoricoAdHoc repositorioHistorico ;
	
	@Autowired
	private IServicioSitiosTuristicos sitiosService;
	
	@Override
	public String altaEstacion(String nombre, int num_puestos, String direccion,double latitud, double longitud) throws RepositorioException {
		
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
		
		if(num_puestos<1)
			throw new IllegalArgumentException("num_puestos: tiene que haber al menos un puesto");
		
		if (direccion == null || direccion.isEmpty())
			throw new IllegalArgumentException("direccion: no debe ser nulo ni vacio");
		
		
		Estacion estacion = new Estacion(nombre, num_puestos, direccion, latitud, longitud);
		String id = this.repositorioEstacion.add(estacion);
		
		
		return id;
	}

	@Override
	public Estacion recuperarEstacion(String id) throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		return repositorioEstacion.getById(id);
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
	public void establecerSitios(String id, List<SitioTuristico> sitios) throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Estacion estacion = repositorioEstacion.getById(id);
		estacion.setSitiosTuristicos(sitios);
		
		this.repositorioEstacion.update(estacion);
		
	}

	@Override
	public String altaBici(String modelo, String id_estacion) throws RepositorioException, EntidadNoEncontrada, ServicioException {
		Bici bici = new Bici(modelo);
		Estacion estacion = repositorioEstacion.getById(id_estacion);
		
		if(estacion.estaLlena())
			throw new ServicioException("Creación de la bici " +modelo+" cancelada-"
					+ "La estacion no tiene más espacio para bicis");
		
		String id_bici = repositorioBici.add(bici);
		
		this.estacionarBici(id_bici, id_estacion);
		
		return id_bici;
	}

	@Override
	public Bici recuperarBici(String id) throws RepositorioException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		return repositorioBici.getById(id);
	}
	
	@Override
	public BiciDTO recuperarBiciDTO(String id) throws RepositorioException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		return transformToDTOBici(repositorioBici.getById(id));
	}

	@Override
	public void estacionarBici(String id_bici, String id_estacion) throws RepositorioException, EntidadNoEncontrada, ServicioException {
		
		Bici bici = repositorioBici.getById(id_bici);
		if(!bici.esDisponible())
			throw new ServicioException("La bici con id "+ id_bici + " no esta disponible ");
		
		if(bici.getIdEstacion() != null)
			throw new ServicioException("La bici ya está estacionada en la estacion con id: " +  bici.getIdEstacion());
		
		
		Estacion estacion = repositorioEstacion.getById(id_estacion);
		
		if(estacion.containsBici(id_bici))
			throw new ServicioException("La bici ya está estacionada en la estacion con id: " +  id_estacion);
		
		
		if(estacion.estacionarBici(id_bici)==false)
			throw new ServicioException("La estación no tiene mas espacio para bicis ");
		
		bici.setIdEstacion(id_estacion);
		Historico hist = new Historico(id_bici, id_estacion);
		
		repositorioHistorico.add(hist);
		repositorioBici.update(bici);
		repositorioEstacion.update(estacion);
		
	}
	
	@Override
	public void estacionarBici(String id_bici) throws RepositorioException, EntidadNoEncontrada, ServicioException {
		String id_estacion = this.buscarEstacion();
		if(id_estacion==null)
			throw new ServicioException("No hay estaciones libres");
		
		this.estacionarBici(id_bici, id_estacion);
		
	}
	
	private String buscarEstacion() throws RepositorioException {
		return repositorioEstacion.buscarPrimeraLibre();
	}

	@Override
	public void retirarBici(String id_bici) throws ServicioException, RepositorioException, EntidadNoEncontrada {
		Bici bici = repositorioBici.getById(id_bici);
		String id_estacion = bici.getIdEstacion();
		
		if(id_estacion == null)
			throw new ServicioException("La bici no está estacionada en ninguna estacion");
		
		Estacion estacion = repositorioEstacion.getById(bici.getIdEstacion());
		
		bici.setIdEstacion(null);
		if (estacion.retirarBici(id_bici) == false)
			throw new ServicioException("La bici no está estacionada en la estacion con id: " +  id_estacion);
		
		Historico hist = repositorioHistorico.getHistorico(id_bici, id_estacion);
		hist.setFechaFin(LocalDateTime.now());
		
		repositorioHistorico.update(hist);
		repositorioBici.update(bici);
		repositorioEstacion.update(estacion);
	}

	@Override
	public void darBajaBici(String id, String motivo) throws RepositorioException, EntidadNoEncontrada, ServicioException {
		
		Bici bici = repositorioBici.getById(id);
		
		if(bici.esDe_Baja())
			throw new ServicioException("La bici con id "+ id + " ya está dada de baja ");
		
		try{
			this.retirarBici(id);
		} catch (ServicioException e) {
			System.out.println(e.getMessage());
		}
		
		//Bici bici = repositorioBici.getById(id);
		bici.darDeBaja(motivo);
		repositorioBici.update(bici);
		
		
	}

	@Override
	public List<Bici> recuperarBiciPosicion(Double latitud, Double longitud) throws RepositorioException {
		
		List<Estacion> estaciones = repositorioEstacion.obtener3MasCercanas(latitud, longitud);
		
		List<Bici> bicis = new LinkedList<>();
		estaciones.stream()
					.flatMap(e -> e.getIdBicis().stream())
					.map(id -> {
						try {
							return repositorioBici.getById(id);
						} catch (RepositorioException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (EntidadNoEncontrada e1) {
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
	public Integer recuperarBiciPosicionCount(Double latitud, Double longitud) throws RepositorioException {
		
		List<Estacion> estaciones = repositorioEstacion.obtener3MasCercanas(latitud, longitud);
		
		List<Bici> bicis = new LinkedList<>();
		estaciones.stream()
					.flatMap(e -> e.getIdBicis().stream())
					.map(id -> {
						try {
							return repositorioBici.getById(id);
						} catch (RepositorioException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (EntidadNoEncontrada e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return null;
					})
					.filter(b -> b != null)
					.filter(Bici::esDisponible)
					.forEach(b -> bicis.add(b));
					
					
		return bicis.size();
	}
	
	private BiciDTO transformToDTOBici(Bici bici) {        
        return  new BiciDTO(bici);   
    }
	
	@Override
	public List<BiciDTO> recuperarBiciPosicionLazy(Double latitud, Double longitud, int start, int max) throws RepositorioException {
		
		List<Estacion> estaciones = repositorioEstacion.obtener3MasCercanas(latitud, longitud);
		List<Bici> bicis = new LinkedList<>();
		try {
			bicis = repositorioBici.getBiciByEstacionLazy(estaciones, start, max);
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<BiciDTO> bicisDTO = new LinkedList<>();
		
		for (Bici bici : bicis) {
			bicisDTO.add(transformToDTOBici(bici));
		}
							
		return bicisDTO;
	}

	@Override
	public List<Estacion> recuperarEstacionPorSitios() throws RepositorioException {
		
		return repositorioEstacion.getEstacionesPorSitios();
	}
	
	

}
