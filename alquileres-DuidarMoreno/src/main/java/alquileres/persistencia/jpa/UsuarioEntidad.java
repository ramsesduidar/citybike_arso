package alquileres.persistencia.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alquileres.dominio.Alquiler;
import alquileres.dominio.Reserva;
import alquileres.dominio.Usuario;

@Entity
@Table(name="UsuarioEntidad")
public class UsuarioEntidad implements Serializable{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String idUsuario;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<ReservaEntidad> reservas;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<AlquilerEntidad> alquileres;
	
	
	public UsuarioEntidad() {
		
	}

	public UsuarioEntidad(Usuario user) {
		this.idUsuario = user.getId();
		this.reservas = transformReservas(user.getReservas(), this.idUsuario);
		this.alquileres = transformAlquileres(user.getAlquileres(), this.idUsuario);
	}

	private List<AlquilerEntidad> transformAlquileres(List<Alquiler> alquileres2, String idU) {
		
		return alquileres2.stream()
				.map(a -> new AlquilerEntidad(a, this))
				.collect(Collectors.toList());
	}

	private List<ReservaEntidad> transformReservas(List<Reserva> reservas2, String idU) {
		
		return reservas2.stream()
				.map(r -> new ReservaEntidad(r, this))
				.collect(Collectors.toList());
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<ReservaEntidad> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaEntidad> reservas) {
		this.reservas = reservas;
	}

	public List<AlquilerEntidad> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<AlquilerEntidad> alquileres) {
		this.alquileres = alquileres;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + idUsuario + ", reservas=" + reservas + ", alquileres=" + alquileres + "]";
	}
	
}
