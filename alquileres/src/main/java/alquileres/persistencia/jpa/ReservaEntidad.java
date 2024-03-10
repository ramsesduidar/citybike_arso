package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import alquileres.dominio.Reserva;

@Entity
@Table(name="ReservaEntidad")
@IdClass(ReservaId.class)
public class ReservaEntidad implements Serializable{
	
	@JoinColumn(name = "idUsuario")
	@ManyToOne
	private UsuarioEntidad usuario;
	
	@Id
	@Column(name = "idBici")
	private String idBici;
	
	@Id
	@Column(name = "creada", columnDefinition = "TIMESTAMP")
	private LocalDateTime creada;
	
	@Column(name = "caducidad", columnDefinition = "TIMESTAMP")
	private LocalDateTime caducidad;
	
	public ReservaEntidad() {
		
	}
	
	public ReservaEntidad(Reserva reserva, UsuarioEntidad idU) {
		this.usuario = idU;
		this.idBici = reserva.getIdBicicleta();
		this.creada = reserva.getCreada();
		this.caducidad = reserva.getCaducidad();
	}


	public UsuarioEntidad getIdUsuario() {
		return usuario;
	}

	public void setIdUsuario(UsuarioEntidad usuario) {
		this.usuario = usuario;
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
	
	@Override
	public String toString() {
		return "Reserva [idUsuario=" + usuario.getIdUsuario() + ", idBicicleta=" + idBici + ", creada=" + creada + ", caducidad=" + caducidad + "]";
	}
	
	
}
