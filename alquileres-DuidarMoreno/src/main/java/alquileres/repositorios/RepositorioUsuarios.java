package alquileres.repositorios;

import java.time.LocalDateTime;

import alquileres.dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.Repositorio;
import repositorios.RepositorioException;

public interface RepositorioUsuarios extends Repositorio<Usuario, String> {
	
	Usuario getUsuarioByReservaActiva(String idBici, LocalDateTime fecha) throws RepositorioException, EntidadNoEncontrada;
	

}
