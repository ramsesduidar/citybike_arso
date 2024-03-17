package encuestas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import encuestas.modelo.Encuesta;
import encuestas.servicio.EncuestaResumen;
import encuestas.servicio.IServicioEncuestas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/encuestas")
@Tag(name ="Encuestas", description ="Aplicación de encuestas")
public class EncuestasController {
	
	private IServicioEncuestas servicio;
	
	@Autowired
	private PagedResourcesAssembler<EncuestaResumen> pagedResourcesAssembler;
	
	@Autowired
	public EncuestasController(IServicioEncuestas servicio) {
		this.servicio = servicio;
	}
	
	@Operation(
			summary ="Obtener encuesta",
			description ="Obtiene una encuesta por su id")
	@GetMapping("/{id}")
	public EntityModel<Encuesta> getEncuestaById(@PathVariable String id)
			throws Exception {
		Encuesta encuesta = this.servicio.getEncuesta(id);
		
		// Envolver el DTO con EntityModel y agregar enlace self
		EntityModel<Encuesta> model = EntityModel.of(encuesta);
		model.add(
				WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EncuestasController.class)
						.getEncuestaById(id))
				.withSelfRel());
		return model;
	}
	
	@GetMapping
	public PagedModel<EntityModel<EncuestaResumen>> getAllEncuestasPaginado2(
			@RequestParam int page,
			@RequestParam int size) throws Exception {
		
		Pageable paginacion =
				PageRequest.of(page, size, Sort.by("titulo").ascending());
		
		Page<EncuestaResumen> r1 = this.servicio.getListadoPaginado(paginacion);
		
		/*Page<EntityModel<EncuestaResumen>> r2 = r1.map(e ->{
			EntityModel<EncuestaResumen> model = EntityModel.of(e);
			try {
				model.add(WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder
							.methodOn(EncuestasController.class)
							.getEncuestaById(e.getId())
						).withSelfRel());
			}catch(Exception err) {
				err.printStackTrace();
			}
			return model;
		});*/
		
		return this.pagedResourcesAssembler.toModel(r1, e ->{
			EntityModel<EncuestaResumen> model = EntityModel.of(e);
			try {
				model.add(WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder
							.methodOn(EncuestasController.class)
							.getEncuestaById(e.getId())
						).withSelfRel());
			}catch(Exception err) {
				err.printStackTrace();
			}
			return model;
		});
	}


}

