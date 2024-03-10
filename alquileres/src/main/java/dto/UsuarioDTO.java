package dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import alquileres.dominio.Usuario;


public class UsuarioDTO implements Serializable{
	
	private String id;
	private List<ReservaDTO> reservas;
	private List<AlquilerDTO> alquileres;
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Usuario user) {
		
		this.id = user.getId();
		this.reservas = user.getReservas()
				.stream()
				.map(r -> new ReservaDTO(r))
				.collect(Collectors.toList());
		
		this.alquileres = user.getAlquileres()
				.stream()
				.map(r -> new AlquilerDTO(r))
				.collect(Collectors.toList());;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ReservaDTO> getReservas() {
		return reservas;
	}
	public void setReservas(List<ReservaDTO> reservas) {
		this.reservas = reservas;
	}
	public List<AlquilerDTO> getAlquileres() {
		return alquileres;
	}
	public void setAlquileres(List<AlquilerDTO> alquileres) {
		this.alquileres = alquileres;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", reservas=" + reservas + ", alquileres=" + alquileres + "]";
	}

}
