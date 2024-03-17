package estaciones.dominio;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;

import repositorios.Identificable;

public class SitioTuristico implements Identificable {
	
	private String nombre;
	
	@BsonId
	private String id;
	
	private String resumen;
	
	private List<String> categorias = new LinkedList<>();
	
	private List<String> enlaces = new LinkedList<>();
	
	private String imagen;
	
	private String url;
	
	public SitioTuristico() {
		
	}
	
	public SitioTuristico (String nombre, String resumen, List<String> categorias, List<String> enlaces, String imagen, String url) {
		
		this.nombre = nombre;
		
		this.id = nombre;
		
		this.resumen = resumen;
		
		this.categorias = categorias;
		
		this.enlaces = enlaces;
		
		this.imagen = imagen;
		
		this.url = url;
		
	}

	public String getId() {
		return nombre;
	}

	public void setId(String id) {
		this.nombre = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public List<String> getEnlaces() {
		return enlaces;
	}

	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void addEnlace(String enlace) {
		this.enlaces.add(enlace);
	}
	
	public void addCategoria(String categoria) {
		this.categorias.add(categoria);
	}

	@Override
	public String toString() {
		return "\r\nSitioTuristico: [\r\n\tnombre=" + nombre + "\r\n\tresumen=" + resumen + "\r\n\tcategorias=" + categorias
				+ "\r\n\tenlaces=" + enlaces + "\r\n\timagen=" + imagen + "\r\n\turl=" + url + "]";
	}
	
	
}

