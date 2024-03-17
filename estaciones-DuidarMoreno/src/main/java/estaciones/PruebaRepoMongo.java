package estaciones;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.geo.Point;

import estaciones.dominio.Estacion;
import estaciones.dominio.SitioTuristico;
import estaciones.repositorios.RepositorioEstacion;

public class PruebaRepoMongo {

	public static void main(String[] args) throws Exception {
		
		ConfigurableApplicationContext contexto =
		SpringApplication.run(EstacionesApp.class, args);
		RepositorioEstacion repo = contexto.getBean(RepositorioEstacion.class);
		
		System.out.println(repo.findFirstLibre().get(0));
		System.out.println(repo.findFirstByNombre("Estacion-Super"));
		
		List<Estacion> estaciones = repo.findFirst3ByCoordenadasNear(new Point(37, -1));
		
		System.out.println("findFirst3ByCoordenadasNear, primero el size y luego las estaciones");
		System.out.println(estaciones.size());
		System.out.println(estaciones);
		
		/*Estacion estacion = new Estacion("Super1", 10, "calle 2", 20.0, 1.0);
		SitioTuristico sitio = new SitioTuristico("mi casa", "es mi casa", new LinkedList<String>(), new LinkedList<String>(), "imagen", "url");
		
		List<SitioTuristico> lista = new LinkedList<SitioTuristico>();
		lista.add(sitio);
		estacion.setSitiosTuristicos(lista);
		
		Estacion estacion2 = new Estacion("Super2", 10, "calle 2", 20.0, 1.0);
		SitioTuristico sitio2 = new SitioTuristico("mi casa2", "es mi casa", new LinkedList<String>(), new LinkedList<String>(), "imagen", "url");
		SitioTuristico sitio3 = new SitioTuristico("mi casa3", "es mi casa", new LinkedList<String>(), new LinkedList<String>(), "imagen", "url");
		
		List<SitioTuristico> lista2 = new LinkedList<SitioTuristico>();
		lista2.add(sitio2);
		lista2.add(sitio3);
		estacion2.setSitiosTuristicos(lista2);
		
		repo.save(estacion);
		repo.save(estacion2);*/
		
		System.out.println("getEstacionesPorSitios():");
		repo.getEstacionesPorSitios().forEach(System.out::println);;
		contexto.close();
		
		
	}

}
