package estaciones.programas;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.EstacionesApp;
import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.EstadoIncidencia;
import estaciones.dominio.Incidencia;
import estaciones.dominio.SitioTuristico;
import estaciones.repositorios.RepositorioBiciMongoDB;
import estaciones.repositorios.RepositorioEstacionMongoDB;
import estaciones.servicios.IServicioEstaciones;
import estaciones.servicios.IServicioIncidencias;
import repositorios.RepositorioMongoDB;

public class ProgramaTestEntrega2 {

	public static void main(String[] args) throws Exception {
		
		ConfigurableApplicationContext contexto =
				SpringApplication.run(EstacionesApp.class, args);
		//Murcia
		double lat1 = 37.980906, lng1 = -1.1273963;
		
		//Madrid
		double lat2 = 40.4165000, lng2 = -3.7025600;
		
		//Barcelona
		double lat3 = 41.3887900, lng3 = 2.1589900;
		
		//Bilbao
		double lat4 = 43.2627100, lng4 = -2.9252800;
		

		IServicioIncidencias serviceIncidencias = contexto.getBean(IServicioIncidencias.class);
		IServicioEstaciones service = contexto.getBean(IServicioEstaciones.class);
		RepositorioEstacionMongoDB repoEstacion = contexto.getBean(RepositorioEstacionMongoDB.class);
		RepositorioBiciMongoDB repoBici = contexto.getBean(RepositorioBiciMongoDB.class);
		
		//--------------------------SERVICIO ESTACIONES--------------------------
		
		String id1 = service.altaEstacion("Estacion 1", 1, "Calle 1", lat1, lng1);
		Estacion estacion1 = service.recuperarEstacion(id1);
		
		String id2 = service.altaEstacion("Estacion 2", 2, "Calle 2", lat2, lng2);
		Estacion estacion2 = service.recuperarEstacion(id2);
		
				
		List<SitioTuristico> sitios1 = service.obtenerSitios(id1);
		service.establecerSitios(id1, sitios1);
		
		List<SitioTuristico> sitios2 = service.obtenerSitios(id2);
		service.establecerSitios(id2, sitios2);
				
		String idBici1 = service.altaBici("Bici1", id1);
		String idBici2 = service.altaBici("Bici2", id2);
		
		String id_estacion = repoEstacion.findFirstLibre().get(0).getId();
		System.out.println(repoEstacion.findById(id_estacion));
		
		// con esto todas las estaciones llenas
		String idBici3 = service.altaBici("Bici3", id_estacion);
		
		String idBici4;
		try {
			idBici4 = service.altaBici("Bici4", id1);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Estacion 1 libre
		service.darBajaBici(idBici1, "esta rota");
		
		try {
			// da error porque está de baja
			service.estacionarBici(idBici1);;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Ahora si hay espacio
		idBici4 = service.altaBici("Bici4", id1);
		
		// Estacion 2 sólo tiene 1 sitios por el substring
		// Estacion 1 tiene 5, por lo que estacion 1 debe ser la pirmera
		service.recuperarEstacionPorSitios().forEach(System.out::println);
		
		
		
		String id3 = service.altaEstacion("Estacion 3", 2, "Calle 3", lat1, lng1);
		Estacion estacion3 = service.recuperarEstacion(id3);
		
		String id4 = service.altaEstacion("Estacion 4", 2, "Calle 4", lat2, lng2);
		Estacion estacion4 = service.recuperarEstacion(id4);
		
		String idBici5 = service.altaBici("Bici5", id3);
		String idBici6 = service.altaBici("Bici6", id4);
		
		// estas coordenadas son las mas cercanas a Estacion 2 que tiene bici2 y 3
		service.recuperarBiciPosicion(40-.0, -3.0).forEach(b-> System.out.println(b.getModelo()));
		
		//--------------------------SERVICIO INCIDENCIAS--------------------------
		
		try {
			serviceIncidencias.crearIncidencia(idBici1, "rueda pinchada");
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		serviceIncidencias.crearIncidencia(idBici2, "rueda pinchada");
		
		
		List<Incidencia> abiertas = serviceIncidencias.recuperarIncidenciasAbiertas();
		abiertas.forEach(i -> System.out.println(i.getDescripcion()));
		String idIncidencia1 = abiertas.get(0).getId();
		
		
		System.out.println(serviceIncidencias.recuperarIncidencia(idIncidencia1).getDescripcion());
		System.out.println(repoBici.findByIncidencias(idIncidencia1).get().getModelo());
		
		serviceIncidencias.cancelarIncidencia(idIncidencia1, "tonterias");
		
		//----------------Incidencia 2-------------
		
		serviceIncidencias.crearIncidencia(idBici6, "silla rota");
		
		abiertas = serviceIncidencias.recuperarIncidenciasAbiertas();
		abiertas.forEach(i -> System.out.println(i.getDescripcion()));
		String idIncidencia2 = abiertas.get(0).getId();
		
		serviceIncidencias.asignarIncidencia(idIncidencia2, "paco");
		
		Bici bici6 = repoBici.findByIncidencias(idIncidencia2).get();
		if(bici6.getIdEstacion()==null)
			System.out.println("Se ha retirado con exito");
		
		//No deberia de imprimir nada
		System.out.println("Bicis en la estacion:");
		estacion4.getIdBicis().forEach(System.out::println);
		System.out.println("Fin de Bicis en la estacion:");
		
		serviceIncidencias.resolverIncidenciaReparada(idIncidencia2, "reparado la silla");
		
		//----------------Incidencia 3-------------
		
		serviceIncidencias.crearIncidencia(idBici5, "silla rota2");
		
		abiertas = serviceIncidencias.recuperarIncidenciasAbiertas();
		abiertas.forEach(i -> System.out.println(i.getDescripcion()));
		String idIncidencia3 = abiertas.get(0).getId();
		
		try {
		serviceIncidencias.resolverIncidenciaNoReparada(idIncidencia3, "no se puede reparar");
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		serviceIncidencias.asignarIncidencia(idIncidencia3, "paco");
		serviceIncidencias.resolverIncidenciaNoReparada(idIncidencia3, "no se puede reparar");
		
		contexto.close();
	}
}
