package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import estaciones.dto.BiciDTO;
import estaciones.dto.CrearBiciDTO;
import estaciones.servicios.ServicioEstaciones;
import repositorios.EntidadNoEncontrada;
import servicios.ServicioException;

@RestController
@RequestMapping("/bicis")
public class BiciController {

    @Autowired
    private ServicioEstaciones servicio;

    public BiciController() {
    	
    }

    @PostMapping("/")
    public ResponseEntity<Void> createBici(@RequestBody CrearBiciDTO dto) throws DataAccessException, EntidadNoEncontrada, ServicioException {
    	
    	String id = this.servicio.altaBici(dto.getModelo(), dto.getIdEstacion());
    			
    	// Construye la URL completa del nuevo recurso
    	URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("/{id}").buildAndExpand(id).toUri();
    	
    	return ResponseEntity.created(nuevaURL).build();
    }
    
    @GetMapping("/{id}")
    public BiciDTO getBici(@PathVariable String id) throws DataAccessException, EntidadNoEncontrada {
        return new BiciDTO(servicio.recuperarBici(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> bajaBici(@PathVariable String id, @RequestBody String motivo) throws DataAccessException, EntidadNoEncontrada, ServicioException {
        
    	servicio.darBajaBici(id, motivo);
    	return ResponseEntity.ok().build();
    }

}