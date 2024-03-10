package alquileres.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.net.URI;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import alquileres.servicios.IServicioAlquileres;
import auth.Rol;
import servicios.FactoriaServicios;

@Path("usuarios")
public class AlquileresControladorRest {

	private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@Context
	private UriInfo uriInfo;

	//curl -i -X POST --data "idBici=34" -H "Authorization: Bearer token_jwt" http://localhost:8080/api/usuarios/1/reservas
	@POST
	@Path("{idU}/reservas")
	@RolesAllowed(Rol.USUARIO_NORMAL)
	public Response crearReserva( @PathParam("idU") String idU,
			@FormParam("idBici") String idB) throws Exception {
		
		this.servicio.reservar(idU, idB);
		
		return Response.status(Response.Status.NO_CONTENT).build();

	}
	
	//curl -i -X PATCH -H "Authorization: Bearer token_jwt" http://localhost:8080/api/usuarios/1/reservas
	@PATCH
	@Path("{idU}/reservas")
	@RolesAllowed(Rol.USUARIO_NORMAL)
	public Response confirmarReserva( @PathParam("id") String id)
			throws Exception {
		
		this.confirmarReserva(id);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	//curl -i -X POST --data "idBici=34" -H "Authorization: Bearer token_jwt" http://localhost:8080/api/usuarios/1/alquileres
	@POST
	@Path("{id}/alquileres")
	@RolesAllowed(Rol.USUARIO_NORMAL)
	public Response alquilar( @PathParam("id") String idU,
			@FormParam("idBici") String idB) throws Exception {
		
		this.servicio.alquilar(idU, idB);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	//curl -i -X GET -H "Authorization: Bearer token_jwt" http://localhost:8080/api/usuarios/1
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({Rol.USUARIO_NORMAL, Rol.ADMINISTRADOR})
	public Response getHistorialUsuario( @PathParam("id") String id)
			throws Exception {
		
		return Response.status(Response.Status.OK)
				.entity(servicio.historialUsuario(id)).build();
	}
	
	
	//curl -i -X PATCH --data "idEstacion=55" -H "Authorization: Bearer token_jwt" http://localhost:8080/api/usuarios/1/alquileres
	@PATCH
	@Path("{id}/alquileres")
	@RolesAllowed(Rol.USUARIO_NORMAL)
	public Response dejarBicicleta( @PathParam("idU") String idU,
			@FormParam("idEstacion") String idE) throws Exception {
		
		this.servicio.dejarBicicleta(idU, idE);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	//curl -i -X PATCH -H "Authorization: Bearer token_jwt" http://localhost:8080/api/usuarios/1
	@PATCH
	@Path("{idU}")
	@RolesAllowed(Rol.ADMINISTRADOR)
	public Response liberarBloqueo( @PathParam("idU") String id)
			throws Exception {
		
		this.servicio.liberarBloqueo(id);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
