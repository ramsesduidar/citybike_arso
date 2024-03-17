package encuestas.servicio;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description ="DTO de la entidad Encuesta")
public class EncuestaResumen {

	@Schema(description ="Identificador de la encuesta")
	private String id;
	
	@Schema(
			description ="Título de la encuesta",
			example ="Fechas examen")
	private String titulo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	@Override
	public String toString() {
		return "EncuestaResumen [id=" + id + ", titulo=" + titulo + "]";
	}	
	
}
