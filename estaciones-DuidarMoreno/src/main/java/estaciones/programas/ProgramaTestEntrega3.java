package estaciones.programas;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.EstacionesApp;
import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.Incidencia;
import estaciones.dominio.SitioTuristico;
import estaciones.repositorios.RepositorioBiciMongoDB;
import estaciones.repositorios.RepositorioEstacionMongoDB;
import estaciones.servicios.IServicioEstaciones;
import estaciones.servicios.IServicioIncidencias;

public class ProgramaTestEntrega3 {
	public static void main(String[] args) throws Exception {

		ConfigurableApplicationContext contexto =
				SpringApplication.run(EstacionesApp.class, args);
		//Murcia
		double lat1 = 37.980906, lng1 = -1.1273963;


		IServicioIncidencias serviceIncidencias = contexto.getBean(IServicioIncidencias.class);
		IServicioEstaciones service = contexto.getBean(IServicioEstaciones.class);
		RepositorioEstacionMongoDB repoEstacion = contexto.getBean(RepositorioEstacionMongoDB.class);
		RepositorioBiciMongoDB repoBici = contexto.getBean(RepositorioBiciMongoDB.class);

		String id1 = service.altaEstacion("Estacion-Super", 1000, "Calle 1", lat1, lng1);

		for(int i=0; i<20; i++) {
			String idBici = service.altaBici("Super-Bici-"+i, id1);
			serviceIncidencias.crearIncidencia(idBici, "rueda pinchada en Super-Bici-"+i);
		}


		List<Incidencia> abiertas = serviceIncidencias.recuperarIncidenciasAbiertas();
		abiertas.forEach(i -> System.out.println(i.getDescripcion()));

		contexto.close();

	}
}
