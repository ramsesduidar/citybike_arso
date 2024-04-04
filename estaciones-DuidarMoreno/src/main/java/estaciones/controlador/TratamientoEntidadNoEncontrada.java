package estaciones.controlador;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import repositorios.EntidadNoEncontrada;

@ControllerAdvice
public class TratamientoEntidadNoEncontrada {
	
	@ExceptionHandler(EntidadNoEncontrada.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public RespuestaError handleGlobalException(EntidadNoEncontrada ex) {
		return new RespuestaError("La entidad con el id dado no existe", ex.getMessage());
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
