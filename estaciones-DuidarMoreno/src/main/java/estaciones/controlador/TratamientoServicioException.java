package estaciones.controlador;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import estaciones.servicios.ServicioEstaciones;
import servicios.ServicioException;

@RestControllerAdvice
public class TratamientoServicioException {
	
	@ExceptionHandler(ServicioException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RespuestaError handleGlobalException(ServicioException ex) {
		return new RespuestaError("Error en el servicio", ex.getMessage());
	}
	
}
