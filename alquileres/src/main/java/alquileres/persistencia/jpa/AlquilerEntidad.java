package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import alquileres.dominio.Alquiler;

@Entity
@Table(name="AlquilerEntidad")
public class AlquilerEntidad implements Serializable{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String idAlquiler;
	
	@JoinColumn(name = "idUsuario")
	@ManyToOne
	private String idUsuario;
	
	@Column(name = "idBici")
	private String idBici;
	
	@Column(name = "inicio", columnDefinition = "TIMESTAMP")
	private LocalDateTime inicio;
	
	@Column(name = "fin", columnDefinition = "TIMESTAMP")
	private LocalDateTime fin;
	
	public AlquilerEntidad() {
		
	}
	
	public AlquilerEntidad(Alquiler alq, String idU) {
		this.idUsuario = idU;
		this.idBici = alq.getIdBicicleta();
		this.inicio = alq.getInicio();
		this.fin = alq.getFin();
	}

	public String getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(String idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBicicleta) {
		this.idBici = idBicicleta;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	
	
}
