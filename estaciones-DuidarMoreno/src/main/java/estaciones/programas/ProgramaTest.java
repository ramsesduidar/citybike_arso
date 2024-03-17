package estaciones.programas;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import estaciones.dominio.Estacion;
import estaciones.dominio.SitioTuristico;
import estaciones.servicios.IServicioEstaciones;
import estaciones.servicios.IServicioSitiosTuristicos;
import estaciones.servicios.SitioResumen;
import repositorios.Repositorio;

public class ProgramaTest {

	public static void main(String[] args) throws Exception {

		double lat = 37.980906, lng = -1.1273963;

		IServicioEstaciones service = FactoriaServicios.getServicio(IServicioEstaciones.class);
		
		// En esta primera ejecución se debe ver el mensaje "La entidad id: NombreSitio.json no existe"
		String id = service.altaEstacion("Estacion 1", 10, "Calle 1", lat, lng);
		Estacion estacion = service.recuperarEstacion(id);
		System.out.println("Estacion antes de añadir los sitios:");
		System.out.println(estacion.toString() + "\r\n");
		
		List<SitioTuristico> sitios = service.obtenerSitios(id);
		sitios.forEach(System.out::println);
		service.establecerSitios(id, sitios);
		
		System.out.println("\r\nEstacion despues de añadir los sitios:");
		System.out.println(service.recuperarEstacion(id) + "\r\n");
		
		// En esta segunda ejecución se debe ver el mensaje "La entidad id: NombreSitio.json si existe"
		// Y por tanto no se llama a DBPedia.
		String id2 = service.altaEstacion("Estacion 2", 10, "Calle 2", lat, lng);
		
		System.out.println("\r\nEstacion antes de añadir los sitios:");
		Estacion estacion2 = service.recuperarEstacion(id2);
		System.out.println(estacion2.toString() + "\r\n");
		
		List<SitioTuristico> sitios2 = service.obtenerSitios(id);
		sitios.forEach(System.out::println);
		service.establecerSitios(id2, sitios2);
		
		System.out.println("\r\nEstacion despues de añadir los sitios:");
		System.out.println(service.recuperarEstacion(id2) + "\r\n");
		
		

	}

}
