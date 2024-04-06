package estaciones.controlador;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamientoDataAccessException {
	
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RespuestaError handleGlobalException(DataAccessException ex) {
		return new RespuestaError("Error en el sistema de persistencia", ex.getMessage());
	}
	
	private static class RespuestaError {
		private String estado;
		private String mensaje;
		
		public RespuestaError(String estado, String mensaje) {
			this.estado = estado;
			this.mensaje = mensaje;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		
		
	}
}
