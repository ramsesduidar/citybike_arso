package alquileres.persistencia.jpa;

import java.time.LocalDateTime;
import java.util.Objects;

public class AlquilerId {
	
	private String idBici;
	
	private LocalDateTime inicio;

	public AlquilerId(String idBici, LocalDateTime inicio) {
		super();
		this.idBici = idBici;
		this.inicio = inicio;
	}

	public AlquilerId() {
		super();
	}

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBici, inicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlquilerId other = (AlquilerId) obj;
		return Objects.equals(idBici, other.idBici) && Objects.equals(inicio, other.inicio);
	}
	
	
}
