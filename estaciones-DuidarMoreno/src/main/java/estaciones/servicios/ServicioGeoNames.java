package estaciones.servicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import estaciones.dominio.SitioTuristico;
import repositorios.Repositorio;

@Service
@Transactional
public class ServicioGeoNames implements IServicioSitiosTuristicos{
	
	@Autowired
	private Repositorio<SitioTuristico, String> repositorio;
	private final String URL_DBPEDIA = "https://es.dbpedia.org/data/";
	private final String URL_WIKI = "https://es.wikipedia.org/wiki/";

	@Override
	public List<SitioResumen> obtenerSitios(double lat, double lng) throws Exception {
		
		ArrayList<SitioResumen> lista = new ArrayList<>();
		
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizador = factoria.newDocumentBuilder();

		Document documento = analizador.parse(
				"http://api.geonames.org/findNearbyWikipedia?lat=" + lat + "&lng=" + lng + "&username=ramses.d.m&lang=es");

		NodeList entradas = documento.getElementsByTagName("entry");
		
		
		for (int i = 0; i < entradas.getLength(); i++) {
			Element elemento = (Element) entradas.item(i);
			lista.add(this.crearResumen(elemento));
		}
		
		return lista;
	}

	@Override
	public SitioTuristico obtenerSitioCompleto(String id) throws Exception {
		
		SitioTuristico completo;
		
		try {
			completo = this.repositorio.getById(id);
			System.out.println("La entidad: " + id + " existe en el repositorio");
			return completo;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		completo = new SitioTuristico();
		String url = this.generar_Url_DBPedia(id);
	
		InputStream fuente = new URL(url).openStream();
		JsonReader jsonReader = Json.createReader(fuente);
	    JsonObject obj = jsonReader.readObject(); 
	    
	    System.out.println("Llamamos a DBPedia...");
	    JsonObject propiedades = obj.getJsonObject("http://es.dbpedia.org/resource/" + id.replace(" ", "_"));
	    
	    // Obtenemos el nombre
	    JsonArray nombres = propiedades.getJsonArray("http://www.w3.org/2000/01/rdf-schema#label");       
        if(nombres != null) {
		    for(JsonObject nombre : nombres.getValuesAs(JsonObject.class))
	        	if (nombre.containsKey("type") && nombre.containsKey("lang")) {
	        		if (nombre.getString("type").equals("literal") && nombre.getString("lang").equals("es"))
	        			completo.setNombre(nombre.getString("value"));
	        	}
        }
        
        // Obtenemos el resumen
	    JsonArray resumenes = propiedades.getJsonArray("http://dbpedia.org/ontology/abstract");
        if(resumenes != null) {
		    for(JsonObject resumen : resumenes.getValuesAs(JsonObject.class))
	        	if (resumen.containsKey("type") && resumen.containsKey("lang")) {
	        		if (resumen.getString("type").equals("literal") && resumen.getString("lang").equals("es"))
	        			completo.setResumen(resumen.getString("value"));
	        	}
        }
        
        // Obtenemos  las etiquetas
	    JsonArray etiquetas = propiedades.getJsonArray("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        if(etiquetas != null) {
		    for(JsonObject etiqueta : etiquetas.getValuesAs(JsonObject.class)){
	        	if (etiqueta.containsKey("type") && etiqueta.getString("type").equals("uri"))
	        		completo.addCategoria(etiqueta.getString("value"));
			}
        }
        
        // Obtenemos  los enlaces
    	JsonArray enlaces = propiedades.getJsonArray("http://dbpedia.org/ontology/wikiPageExternalLink");
    	if(enlaces != null) {
	    	for(JsonObject enlace : enlaces.getValuesAs(JsonObject.class)){
	          	if (enlace.containsKey("type") && enlace.getString("type").equals("uri"))
	          		completo.addEnlace(enlace.getString("value"));
	    	}
    	}
        
        // Obtenemos la imagen
	    JsonArray imagenes = propiedades.getJsonArray("http://es.dbpedia.org/property/imagen");
	    if(imagenes != null) {
		    for(JsonObject imagen : imagenes.getValuesAs(JsonObject.class)) {
	        	if (imagen.containsKey("type") && imagen.containsKey("lang")) {
	        		if (imagen.getString("type").equals("literal") && imagen.getString("lang").equals("es"))
	        			completo.setImagen(imagen.getString("value"));
	        	}
		    }
	    }
	    
        completo.setUrl(this.generar_Url_wiki(id));
        
        this.repositorio.add(completo);
		
		return completo;
	}
	
	private SitioResumen crearResumen(Element elemento) {
		SitioResumen resumenSitio = new SitioResumen();
		
		String titulo = elemento.getElementsByTagName("title").item(0).getTextContent();
		String resumen = elemento.getElementsByTagName("summary").item(0).getTextContent();
		String distancia = elemento.getElementsByTagName("distance").item(0).getTextContent();
		String url = elemento.getElementsByTagName("wikipediaUrl").item(0).getTextContent();
		
		resumenSitio.setNombre(titulo);
		resumenSitio.setDescripcion(resumen);
		resumenSitio.setDistancia(distancia);
		resumenSitio.setUrl(url);
		
		return resumenSitio;
	}
	
	private String generar_Url_DBPedia(String nombre) {
		
		return this.URL_DBPEDIA + nombre.replace(" ", "_") + ".json";
	}
	
	private String generar_Url_wiki(String nombre) {
		
		return this.URL_WIKI + nombre.replace(" ", "_");
	}

}
