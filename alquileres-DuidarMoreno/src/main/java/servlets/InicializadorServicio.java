package servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import alquileres.servicios.IServicioAlquileres;
import servicios.FactoriaServicios;

@WebListener
public class InicializadorServicio implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Inicia tu servicio de alquileres aquí
    	System.out.println("Servicio inciando...");
        IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Puedes detener tu servicio aquí si es necesario
    }
}
