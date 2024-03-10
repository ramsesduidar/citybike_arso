package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


public class ReservaId implements Serializable {
	
	private String idBici;
	
	private LocalDateTime creada;

	
	public ReservaId() {
        
    }

    public ReservaId(String idBici, LocalDateTime creada) {
        this.idBici = idBici;
        this.creada = creada;
    }

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public LocalDateTime getCreada() {
		return creada;
	}

	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creada, idBici);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservaId other = (ReservaId) obj;
		return Objects.equals(creada, other.creada) && Objects.equals(idBici, other.idBici);
	}

	
}
