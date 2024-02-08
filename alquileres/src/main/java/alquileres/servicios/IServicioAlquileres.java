package alquileres.servicios;

import alquileres.dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;

public interface IServicioAlquileres {
	
	void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada;
	
	void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada;
	
	void alquilar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada;
	
	Usuario historialUsuario(String idUsuario) throws RepositorioException;
	
	void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada;
	
	void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada;

}
