package estaciones;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.dominio.Bici;
import estaciones.dominio.Historico;
import estaciones.dominio.Incidencia;
import estaciones.repositorios.RepositorioBici;
import estaciones.repositorios.RepositorioHistorico;
import estaciones.repositorios.RepositorioIncidencia;

public class PruebaRepoBiciEIncidenciaMongo {

	public static void main(String[] args) {
		ConfigurableApplicationContext contexto =
				SpringApplication.run(EstacionesApp.class, args);
		RepositorioBici repoBici = contexto.getBean(RepositorioBici.class);
		
		RepositorioIncidencia repoIncidencia = contexto.getBean(RepositorioIncidencia.class);
		
		Bici bici1 = new Bici("Modelo1");
		bici1.setIdEstacion("Estacion1");
		
		Incidencia i1 = new Incidencia(bici1, "rueda pinchada");
		Incidencia i2 = new Incidencia(bici1, "silla rota");
		
		i1 = repoIncidencia.save(i1);
		i2 = repoIncidencia.save(i2);
		bici1 = repoBici.save(bici1);
		System.out.println("Incidencia1.id: " + i1.getId());
		System.out.println("Incidencia2.id: " + i2.getId());
		System.out.println("Bici1.id: " + bici1.getId());
		
		
		bici1.addIncidencia(i1);
		bici1.addIncidencia(i2);
		
		System.out.println("Incidencia.bici.id: " + i1.getBici().getId());
		
		i1 = repoIncidencia.save(i1);
		i2 = repoIncidencia.save(i2);
		
		bici1 = repoBici.save(bici1);
		
		
		
		Optional<Bici> bici = repoBici.findById(bici1.getId());
		
		if(bici.isPresent()) {
			System.out.println("bici.id: " + bici.get().getId());
			System.out.println("bici.incidencias: " + bici.get().getIncidencias());
			bici.get().getIncidencias().forEach(i -> System.out.println("Incidencia: "+i.getId() + "motivo: " + i.getDescripcion()));
		}
		
		bici1.cancelarIncidencia(i2.getId(), "nada");
		repoIncidencia.save(i2);
		
		repoIncidencia.findIncidenciasAbiertas().forEach(i -> System.out.println("Incidencia abierta: " + i.getId()));
		
		System.out.println("Bici en Estacion1 " + repoBici.findByIdEstacion("Estacion1").get(0).getModelo());
		
		System.out.println("Bici de la Incidencia1 " + repoBici.findByIncidencias(i1.getId()).get().getModelo());
		
		repoIncidencia.delete(i1);
		repoIncidencia.delete(i2);
		repoBici.delete(bici1);
		
		contexto.close();

	}

}
