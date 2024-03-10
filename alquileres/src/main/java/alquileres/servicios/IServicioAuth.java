package alquileres.servicios;

import java.util.Map;

public interface IServicioAuth {

	Map<String, Object> verificarCredenciales(String username, String password);

}
