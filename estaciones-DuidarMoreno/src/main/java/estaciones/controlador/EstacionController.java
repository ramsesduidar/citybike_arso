package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

import javax.validation.Valid;

import estaciones.dto.BiciDTO;
import estaciones.dto.CrearEstacionDTO;
import estaciones.dto.EstacionDTO;
import estaciones.servicios.IServicioEstaciones;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import repositorios.EntidadNoEncontrada;
import servicios.ServicioException;

@RestController
@RequestMapping("/estaciones")
@Tag(name = "Estaciones", description ="Controlador para trabajar con Encuestas")
public class EstacionController {

    @Autowired
    private IServicioEstaciones servicio;
    
    @Autowired
    private PagedResourcesAssembler<EstacionDTO> pagedRA_Estacion;
    @Autowired
    private PagedResourcesAssembler<BiciDTO> pagedRA_Bici;
    
    public EstacionController() {
    	
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('Usuario')")
    @Operation(
    		summary ="Obtener todas las Estaciones",
    		description ="Operacion para obtener todas las Estaciones (Solo para el rol Usuario)")
    public PagedModel<EntityModel<EstacionDTO>> getAllEstaciones(@RequestParam int page,
    										  @RequestParam int size) throws DataAccessException{
        
    	Pageable paginacion =
    			PageRequest.of(page, size, Sort.by("nombre").ascending());
    	
    	Page<EstacionDTO> estaciones = servicio.recuperarTodasEstacionesPaginado(paginacion)
        		.map( e -> new EstacionDTO(e));
    	
    	return  this.pagedRA_Estacion.toModel(estaciones);
    	
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('Usuario')")
    @Operation(
    		summary ="Obtener Estacione",
    		description ="Operacion para obtener una Estacion dado su id (Solo para el rol Usuario)")
    public EstacionDTO getEstacionById(@PathVariable String id) throws DataAccessException, EntidadNoEncontrada {
        return new EstacionDTO(servicio.recuperarEstacion(id));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('Gestor')")
    @Operation(
    		summary ="Crear Estacion",
    		description ="Crea una Estaciones con los datos especificados (Solo para el rol Gestor)")
    public ResponseEntity<Void> createEstacion(@Valid @RequestBody CrearEstacionDTO dto) throws DataAccessException {
    	
    	String id = this.servicio.altaEstacion(dto.getNombre(), dto.getNumPuestos(), 
    			dto.getDireccion(), dto.getLatitud(), dto.getLongitud());
    			
    	// Construye la URL completa del nuevo recurso
    	URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("/{id}").buildAndExpand(id).toUri();
    	
    	return ResponseEntity.created(nuevaURL).build();
    }

    @GetMapping("/{id}/bicis")
    @PreAuthorize("hasAuthority('Gestor') or hasAuthority('Usuario')")
    @Operation(
    		summary ="Obtener Bicis en Estacion",
    		description ="Operacion para obtener todas las Bicis en una Estacion (Solo para el rol Usuario y Gestor)")
    public PagedModel<EntityModel<BiciDTO>> getBicisEnEstacion(@PathVariable String id,
    										@RequestParam int page,
    										@RequestParam int size) throws DataAccessException, EntidadNoEncontrada {
        
    	Pageable paginacion =
    			PageRequest.of(page, size, Sort.by("modelo").ascending());
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	ArrayList<String> roles = new ArrayList<>();
    	auth.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
    	
    	System.out.println("Roles: " + roles);
    	
    	Page<BiciDTO> bicis;
    	
    	if(roles.contains("Usuario"))
    		bicis = servicio.getBicisDisponiblesFromEstacionPaginado(id, paginacion)
        			.map(bici -> new BiciDTO(bici));
    	
    	else 
    		bicis = servicio.getBicisFromEstacionPaginado(id, paginacion)
    				.map(bici -> new BiciDTO(bici));
    				
    	
    	if(roles.contains("Gestor"))
	    	return this.pagedRA_Bici.toModel(bicis, bici -> {
				// Envolver el DTO con EntityModel y agregar enlace dar_baja
				EntityModel<BiciDTO> model = EntityModel.of(bici);
				
				String urlEliminar;
				try {
					urlEliminar = WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder
									.methodOn(BiciController.class)
									.bajaBici(bici.getId(), "motivo"))
							.toUri()
							.toString();
					model.add(Link.of(urlEliminar,
	    					"dar_Baja"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return model;
			});
    			
    	else
	    	return this.pagedRA_Bici.toModel(bicis);
    }

    @PutMapping("/{id}/bicis")
    //@PreAuthorize("hasAuthority('Usuario')")
    @Operation(
    		summary ="Estacionar Bici",
    		description ="Estaciona una Bici en la Estacion dada por el id (Solo para el rol Usuario)")
    public ResponseEntity<Void> estacionarBici(@PathVariable String id, 
    										@RequestBody String idBici) throws DataAccessException, EntidadNoEncontrada, ServicioException {
        
    	servicio.estacionarBici(idBici, id);
    	
    	return ResponseEntity.noContent().build();
    	
    }
}