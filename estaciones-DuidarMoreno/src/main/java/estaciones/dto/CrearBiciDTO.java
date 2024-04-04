package estaciones.dto;

public class CrearBiciDTO {
	
	private String idEstacion;
	
	private String modelo;

	public CrearBiciDTO(String idEstacion, String modelo) {
		this.idEstacion = idEstacion;
		this.modelo = modelo;
	}
	
	public CrearBiciDTO() {
		
	}

	public String getIdEstacion() {
		return idEstacion;
	}

	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	
	

}
