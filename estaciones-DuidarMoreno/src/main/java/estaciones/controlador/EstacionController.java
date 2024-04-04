package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import estaciones.dominio.Estacion;
import estaciones.dto.BiciDTO;
import estaciones.dto.CrearEstacionDTO;
import estaciones.dto.EstacionDTO;
import estaciones.servicios.IServicioEstaciones;
import estaciones.servicios.ServicioEstaciones;
import repositorios.EntidadNoEncontrada;
import servicios.ServicioException;

@RestController
@RequestMapping("/estaciones")
public class EstacionController {

    @Autowired
    private IServicioEstaciones servicio;
    
    public EstacionController() {
    	
    }

    @GetMapping("/")
    public Page<EstacionDTO> getAllEstaciones(@RequestParam int page,
    										  @RequestParam int size) throws DataAccessException{
        
    	Pageable paginacion =
    			PageRequest.of(page, size, Sort.by("nombre").ascending());
    	
    	return servicio.recuperarTodasEstacionesPaginado(paginacion)
        		.map( e -> new EstacionDTO(e));
    }

    @GetMapping("/{id}")
    public EstacionDTO getEstacionById(@PathVariable String id) throws DataAccessException, EntidadNoEncontrada {
        return new EstacionDTO(servicio.recuperarEstacion(id));
    }

    @PostMapping("/")
    public ResponseEntity<Void> createEstacion(@RequestBody CrearEstacionDTO dto) throws DataAccessException {
    	
    	String id = this.servicio.altaEstacion(dto.getNombre(), dto.getNumPuestos(), 
    			dto.getDireccion(), dto.getLatitud(), dto.getLongitud());
    			
    	// Construye la URL completa del nuevo recurso
    	URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("/{id}").buildAndExpand(id).toUri();
    	
    	return ResponseEntity.created(nuevaURL).build();
    }

    @GetMapping("/{id}/bicis")
    public Page<BiciDTO> getBicisEnEstacion(@PathVariable String id,
    										@RequestParam int page,
    										@RequestParam int size) throws DataAccessException, EntidadNoEncontrada {
        
    	Pageable paginacion =
    			PageRequest.of(page, size, Sort.by("modelo").ascending());
    	
    	return servicio.getBicisFromEstacionPaginado(id, paginacion)
    			.map(bici -> new BiciDTO(bici));
    			
    			
    }

    @PutMapping("/{id}/bicis")
    public ResponseEntity<Void> estacionarBici(@PathVariable String id, 
    										@RequestBody String idBici) throws DataAccessException, EntidadNoEncontrada, ServicioException {
        
    	servicio.estacionarBici(idBici, id);
    	
    	return ResponseEntity.ok().build();
    	
    }
}