package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import alquileres.dominio.Alquiler;

@Entity
@Table(name="AlquilerEntidad")
@IdClass(AlquilerId.class)
public class AlquilerEntidad implements Serializable{
	
	@JoinColumn(name = "idUsuario")
	@ManyToOne
	private UsuarioEntidad usuario;
	
	@Id
	@Column(name = "idBici")
	private String idBici;
	
	@Id
	@Column(name = "inicio", columnDefinition = "TIMESTAMP")
	private LocalDateTime inicio;
	
	@Column(name = "fin", columnDefinition = "TIMESTAMP")
	private LocalDateTime fin;
	
	public AlquilerEntidad() {
		
	}
	
	public AlquilerEntidad(Alquiler alq, UsuarioEntidad user) {
		this.usuario = user;
		this.idBici = alq.getIdBicicleta();
		this.inicio = alq.getInicio();
		this.fin = alq.getFin();
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
	
	@Override
	public String toString() {
		return "Alquiler [idUsuario=" + usuario.getIdUsuario() + "idBici=" + idBici + ", inicio=" + inicio + ", fin=" + fin + "]";
	}
	
}
