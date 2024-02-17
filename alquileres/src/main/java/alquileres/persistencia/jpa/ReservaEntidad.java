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

import alquileres.dominio.Reserva;

@Entity
@Table(name="ReservaEntidad")
public class ReservaEntidad implements Serializable{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String idReserva;
	
	@JoinColumn(name = "idUsuario")
	@ManyToOne
	private String idUsuario;
	
	@Column(name = "idBici")
	private String idBici;
	
	@Column(name = "creada", columnDefinition = "TIMESTAMP")
	private LocalDateTime creada;
	
	@Column(name = "caducidad", columnDefinition = "TIMESTAMP")
	private LocalDateTime caducidad;
	
	public ReservaEntidad() {
		
	}
	
	public ReservaEntidad(Reserva reserva, String idU) {
		this.idUsuario = idU;
		this.idBici = reserva.getIdBicicleta();
		this.creada = reserva.getCreada();
		this.caducidad = reserva.getCaducidad();
	}

	public String getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(String idReserva) {
		this.idReserva = idReserva;
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

	public LocalDateTime getCreada() {
		return creada;
	}

	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}

	public LocalDateTime getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}
	
	
}
