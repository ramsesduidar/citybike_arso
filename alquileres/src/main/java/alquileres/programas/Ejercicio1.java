package alquileres.programas;

import alquileres.dominio.Usuario;
import alquileres.servicios.IServicioAlquileres;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import servicios.FactoriaServicios;

public class Ejercicio1 {

	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		
		IServicioAlquileres service = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
		service.alquilar("0", "10");
		
		Usuario user0 = service.historialUsuario("0");
		
		System.out.println("\n Despues de alquilar: "+user0);
		
		service.dejarBicicleta("0", "33");
		
		System.out.println("\n Despues de dejarBici: "+user0);
		
		try {
			service.confirmarReserva("0");
		} catch(Exception e) {
			System.out.println("\nComo esperamos, ha saltado el error al confirmarReserva: " + e);
		}
		
		service.reservar("0", "11");
		System.out.println("\nUsuario al reservar: " + user0);
		
		try {
			service.alquilar("0", "11");
		} catch(Exception e) {
			System.out.println("\nComo esperamos, ha saltado el error al alquilar: " + e);
		}
		
		
		service.confirmarReserva("0");
		System.out.println("\nUsuario despues de reservar: " + user0);
		
		
		

	}

}
