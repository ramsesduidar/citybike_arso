package estaciones.repositorios;

import org.springframework.stereotype.Repository;

import estaciones.dominio.SitioTuristico;
import repositorios.RepositorioJSON;

@Repository
public class RepositorioSitioTuristicoJSON extends RepositorioJSON<SitioTuristico>{
	
	
	public Class<SitioTuristico> getClase() {
		return SitioTuristico.class;
	}
	

}
