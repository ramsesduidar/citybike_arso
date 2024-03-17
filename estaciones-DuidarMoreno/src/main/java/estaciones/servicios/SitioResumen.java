package estaciones.servicios;

public class SitioResumen {
	
		private String nombre; 
		
		private String descripcion;
		
		private String distancia;
		
		private String url;
		
		
		public String getNombre() {
			return nombre;
		}
		
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		public String getDescripcion() {
			return descripcion;
		}
		
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
		public String getDistancia() {
			return distancia;
		}
		
		public void setDistancia(String distancia) {
			this.distancia = distancia;
		}
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return "SitioResumen [nombre=" + nombre + "\r\ndescripcion=" + descripcion + "\r\ndistancia=" + distancia
					+ "\r\nurl=" + url + "]";
		}
		
		

		
}
