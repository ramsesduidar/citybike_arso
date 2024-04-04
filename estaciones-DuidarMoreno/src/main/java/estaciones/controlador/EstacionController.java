package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import estaciones.servicios.ServicioEstaciones;
import repositorios.EntidadNoEncontrada;
import servicios.ServicioException;

@RestController
@RequestMapping("/estaciones")
public class EstacionController {

    @Autowired
    private ServicioEstaciones servicio;
    
    public EstacionController() {
    	
    }

    @GetMapping("/")
    public List<EstacionDTO> getAllEstaciones() throws DataAccessException{
        
    	return servicio.recuperarTodasEstaciones()
        		.stream()
        		.map( e -> new EstacionDTO(e))
        		.collect(Collectors.toList());
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
    public List<BiciDTO> getBicisEnEstacion(@PathVariable String id) throws DataAccessException, EntidadNoEncontrada {
        
    	return servicio.getBicisFromEstacion(id)
    			.stream()
    			.map(bici -> new BiciDTO(bici))
    			.collect(Collectors.toList());
    			
    }

    @PutMapping("/{id}/bicis")
    public ResponseEntity<Void> estacionarBici(@PathVariable String id, 
    										@RequestParam String idBici) throws DataAccessException, EntidadNoEncontrada, ServicioException {
        
    	servicio.estacionarBici(idBici, id);
    	
    	return ResponseEntity.ok().build();
    	
    }
}