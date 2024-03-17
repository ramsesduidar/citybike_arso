package estaciones.dominio;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.geo.Point;
import com.mongodb.client.model.geojson.Position;

import repositorios.Identificable;

@Document(collection ="estacion")
public class Estacion implements Identificable {
	
	@Id
	private String id;
	
	private String nombre;
	
	private LocalDateTime fechaDeAlta;
	
	private Integer numPuestos;
	
	private String direccion;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private Point coordenadas;
	
	// Usaremos documentos embebidos ya que no necesitamos acceder a los
	// sitios de manera independiente ni hacer operaciones sobre ellos.
	private List<SitioTuristico> sitiosTuristicos = new LinkedList<>();
	
	private List<String> idBicis = new LinkedList<>();
	
	public Estacion() {
		
	}
	
	public Estacion (String nombre, Integer numPuestos, String direccion, Double latitud, Double longitud) {
		
		this.nombre = nombre;
		
		this.fechaDeAlta = LocalDateTime.now();
		
		this.numPuestos = numPuestos;
		
		this.direccion = direccion;
		
		this.coordenadas = new Point(latitud, longitud);
	}

	public Point getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Point coord) {
		this.coordenadas = coord;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDateTime getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(LocalDateTime fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public Integer getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(Integer numPuestos) {
		this.numPuestos = numPuestos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getLatitud() {
		return this.coordenadas.getX();
	}


	public Double getLongitud() {
		return this.coordenadas.getY();
	}
	
	public List<String> getIdBicis() {
		return idBicis;
	}

	public void setIdBicis(List<String> idBicis) {
		this.idBicis = idBicis;
	}

	public List<SitioTuristico> getSitiosTuristicos() {
		return this.sitiosTuristicos;
	}

	public void setSitiosTuristicos(List<SitioTuristico> sitios) {
		this.sitiosTuristicos = sitios;
	}
	
	@Override
	public String toString() {
		return "Estacion [id=" + id + ", nombre=" + nombre + ", npuestos=" + numPuestos + ", fecha_Creacion=" + this.fechaDeAlta
				+ "\r\n\tdireccion=" + direccion + ", coordenadas=" + coordenadas + ", sitios=" + sitiosTuristicos +"]";
	}

	public boolean containsBici(String id_bici) {
		return this.idBicis.contains(id_bici);
	}
	
	public boolean estaLlena() {
		int contador = 0;
		for(String b: this.idBicis)
			contador++;
		
		return this.numPuestos == contador;
	}
	public boolean estacionarBici(String id_bici) {
		if(!this.idBicis.contains(id_bici) && !this.estaLlena())
			return this.idBicis.add(id_bici);
		return false;
		
	}

	public boolean retirarBici(String id_bici) {
		if(this.idBicis.contains(id_bici))
			return this.idBicis.remove(id_bici);
		return false;
	}

	
	
}

