package estaciones.programas;

import java.util.List;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.Incidencia;
import estaciones.dominio.SitioTuristico;
import estaciones.repositorios.RepositorioEstacionAdHocMongoDB;
import estaciones.servicios.IServicioEstaciones;
import estaciones.servicios.IServicioIncidencias;

public class ProgramaTestEntrega3 {
public static void main(String[] args) throws Exception {
		
		//Murcia
		double lat1 = 37.980906, lng1 = -1.1273963;
		

		IServicioIncidencias serviceIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
		IServicioEstaciones service = FactoriaServicios.getServicio(IServicioEstaciones.class);
		RepositorioEstacionAdHocMongoDB repoEstacion = FactoriaRepositorios.getRepositorio(Estacion.class);
		RepositorioBiciAdHocMySQL repoBici = FactoriaRepositorios.getRepositorio(Bici.class);
		
		String id1 = service.altaEstacion("Estacion-Super", 1000, "Calle 1", lat1, lng1);
		
		for(int i=0; i<20; i++) {
			String idBici = service.altaBici("Super-Bici-"+i, id1);
			serviceIncidencias.crearIncidencia(idBici, "rueda pinchada en Super-Bici-"+i);
		}
		
		
		List<Incidencia> abiertas = serviceIncidencias.recuperarIncidenciasAbiertas();
		abiertas.forEach(i -> System.out.println(i.getDescripcion()));
		
	}
}
