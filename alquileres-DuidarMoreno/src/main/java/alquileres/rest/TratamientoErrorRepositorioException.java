package alquileres.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import repositorios.RepositorioException;

@Provider
public class TratamientoErrorRepositorioException implements ExceptionMapper<RepositorioException> {
	
	@Override
	public Response toResponse(RepositorioException arg0) {
		return Response
				.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(arg0.getMessage()).build();
	}
}
