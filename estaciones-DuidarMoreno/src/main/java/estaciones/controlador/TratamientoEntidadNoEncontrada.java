package estaciones.controlador;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import repositorios.EntidadNoEncontrada;

@RestControllerAdvice
public class TratamientoEntidadNoEncontrada {
	
	@ExceptionHandler(EntidadNoEncontrada.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RespuestaError handleGlobalException(EntidadNoEncontrada ex) {
		return new RespuestaError("La entidad con el id dado no existe", ex.getMessage());
	}
	
}
