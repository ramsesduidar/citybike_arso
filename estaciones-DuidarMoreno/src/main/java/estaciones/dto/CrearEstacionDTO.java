package estaciones.dto;

public class CrearEstacionDTO {
	
	private String nombre;
	
	private int numPuestos; 
	
	private String direccion;
	
	private long latitud;
	
	private long longitud;

	public CrearEstacionDTO(String nombre, int numPuestos, String direccion, long latitud, long longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public CrearEstacionDTO() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getLatitud() {
		return latitud;
	}

	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}

	public long getLongitud() {
		return longitud;
	}

	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}
	
	
	
	

}
