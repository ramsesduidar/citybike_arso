package estaciones.controlador;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import estaciones.servicios.ServicioEstaciones;
import servicios.ServicioException;

@ControllerAdvice
public class TratamientoServicioException {
	
	@ExceptionHandler(ServicioException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RespuestaError handleGlobalException(ServicioException ex) {
		return new RespuestaError("Error en el servicio", ex.getMessage());
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
