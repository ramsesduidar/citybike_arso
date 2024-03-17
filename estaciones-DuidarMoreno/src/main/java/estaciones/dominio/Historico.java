package estaciones.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorios.Identificable;

public class Historico implements Identificable{

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID) 
	private String id;
	
	private String idBici;
	
	private String idEstacion;
	
	private LocalDateTime fechaInicio;
	
	private LocalDateTime fechaFin;
	
	public Historico() {
		
	}

	public Historico(String idBici, String idEstacion) {
		super();
		this.idBici = idBici;
		this.idEstacion = idEstacion;
		this.fechaInicio = LocalDateTime.now();
	}

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}
