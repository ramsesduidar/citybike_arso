package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import javax.validation.Valid;

import estaciones.dto.BiciDTO;
import estaciones.dto.CrearBiciDTO;
import estaciones.servicios.ServicioEstaciones;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import repositorios.EntidadNoEncontrada;
import servicios.ServicioException;

@RestController
@RequestMapping("/bicis")
@Tag(name = "Bicicletas", description ="Controlador para trabajar con Bicis")
public class BiciController {

    @Autowired
    private ServicioEstaciones servicio;

    public BiciController() {
    	
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('Gestor')")
    @Operation(
    		summary ="Crear bicicleta",
    		description ="Da de alta una bicicleta con los datos especificados (Solo para el rol Gestor)")
    public ResponseEntity<Void> createBici(@Valid @RequestBody CrearBiciDTO dto) throws DataAccessException, EntidadNoEncontrada, ServicioException {
    	
    	String id = this.servicio.altaBici(dto.getModelo(), dto.getIdEstacion());
    			
    	// Construye la URL completa del nuevo recurso
    	URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("/{id}").buildAndExpand(id).toUri();
    	
    	return ResponseEntity.created(nuevaURL).build();
    }
    
    @GetMapping("/{id}")
    @Operation(
    		summary ="Obtener bicicleta",
    		description ="Obtener al informacion una bicicleta a partir de su id (No es necesario rol)")
    public BiciDTO getBici(@PathVariable String id) throws DataAccessException, EntidadNoEncontrada {
        return new BiciDTO(servicio.recuperarBici(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('Gestor')")
    @Operation(
    		summary ="Dar baja bicicleta",
    		description ="Da de baja una bicicleta especificando el motivo (Solo para el rol Gestor)")
    public ResponseEntity<Void> bajaBici(@PathVariable String id, @RequestBody String motivo) throws DataAccessException, EntidadNoEncontrada, ServicioException {
        
    	servicio.darBajaBici(id, motivo);
    	return ResponseEntity.noContent().build();
    }

}