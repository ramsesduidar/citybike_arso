package alquileres.dominio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import repositorios.Identificable;

public class Usuario implements Identificable{

	private String id;
	private List<Reserva> reservas;
	private List<Alquiler> alquileres;
	
	public Usuario() {
		
	}
	
	public Usuario(String id) {
		this.id = id;
		this.alquileres = new LinkedList<>();
		this.reservas = new LinkedList<>();
	}

	public int reservasCadudadas(){
		return (int) this.reservas.stream()
				.filter(Reserva::caducada)
				.count();
			
	}
	
	public int tiempoUsoHoy(){
		//Retorna el número de minutos de alquileres iniciados el día de hoy.
		return this.alquileres.stream()
				.filter(a -> a.getInicio().toLocalDate().isEqual(LocalDate.now()))
				.mapToInt(Alquiler::tiempo)
				.reduce(0, Integer::sum);
	}
	
	public int tiempoUsoSemana(){
		//Retorna el número de minutos de alquileres iniciados en la última semana.
		LocalDate inicioSemana = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return this.alquileres.stream()
				.filter(a -> {
					LocalDate fecha = a.getInicio().toLocalDate();
					return fecha.isAfter(inicioSemana.minusDays(1)) && fecha.isBefore(finSemana.plusDays(1));
				})
				.mapToInt(Alquiler::tiempo)
				.reduce(0, Integer::sum);
	}
	
	public boolean superaTiempo(){
		//Si tiempoUsoHoy >= 60 minutos o tiempoUsoSemana >= 180 minutos.
		return tiempoUsoHoy() >= 60  || tiempoUsoSemana() >= 180 ;
	}
	
	public Reserva reservaActiva(){
		//Retorna la última reserva, si está activa, nulo en caso contrario.
		Optional<Reserva> optional = this.reservas.stream()
				.filter(Reserva::activa)
				.findFirst();
		
		return optional.orElse(null);
				
	}
	
	public Alquiler alquilerActivo(){
		//Retorna el último alquiler, en caso estar activo, nulo en caso contrario.
		Optional<Alquiler> optional = this.alquileres.stream()
				.filter(Alquiler::activo)
				.findFirst();
		
		return optional.orElse(null);
	}
	
	public boolean bloqueado(){
		return this.reservasCadudadas() >= 3;
	}
	
	public void liberarBloqueo() {
		if(this.bloqueado()) {
			this.reservas = new LinkedList<>();
		}
		
	}
	
	public String terminarAlquilerActivo() {
		
		return this.alquilerActivo().terminar();
	}

	public void addReserva(String idB, LocalDateTime inicio, LocalDateTime fin) {
		if(this.reservaActiva() == null)
			this.reservas.add(new Reserva(idB, inicio, fin));
	}
	
	public void deleteReservaActiva() {
		Reserva reserva = this.reservaActiva();
		if(reserva != null) {
			this.reservas.removeIf(Reserva::activa);
		}
		
	}
	
	public void addAlquiler(String idB, LocalDateTime inicio) {
		if (this.alquilerActivo() == null)
			this.alquileres.add(new Alquiler(idB, inicio));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", reservas=" + reservas + ", alquileres=" + alquileres + "]";
	}
	
	

	/* Para probar la funcion tiempoUso().
	    
	    public static void main(String[] args) {
	        
	    	Usuario u = new Usuario("1");
	    	for(int i = 0; i<10; i++) {
	    		Alquiler a = new Alquiler(""+i);
	    		a.setInicio(LocalDateTime.now());
	    		a.setFin(LocalDateTime.now().plusMinutes(11));
	    		u.addAlquiler(a);
	    	}
	    	
	    	for(int i = 0; i<10; i++) {
	    		Alquiler a = new Alquiler(""+i);
	    		a.setInicio(LocalDateTime.now().plusDays(3));
	    		a.setFin(LocalDateTime.now().plusMinutes(11).plusDays(3));
	    		u.addAlquiler(a);
	    	}
	    	
	    	for(int i = 0; i<10; i++) {
	    		Alquiler a = new Alquiler(""+i);
	    		a.setInicio(LocalDateTime.now().plusDays(4));
	    		a.setFin(LocalDateTime.now().plusMinutes(11).plusDays(4));
	    		u.addAlquiler(a);
	    	}
	    	
	    	System.out.println("Tiempo: " + u.tiempoUsoSemana());
	    }
	*/
	
}
