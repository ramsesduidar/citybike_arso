package estaciones;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.geo.Point;

import estaciones.dominio.Estacion;
import estaciones.dominio.Historico;
import estaciones.repositorios.RepositorioHistorico;

public class PruebaRepoHistoricoMongo {

	public static void main(String[] args) {
		ConfigurableApplicationContext contexto =
				SpringApplication.run(EstacionesApp.class, args);
		RepositorioHistorico repo = contexto.getBean(RepositorioHistorico.class);
		
		Historico hist = new Historico("Bici1", "Estacion1");
		Historico hist2 = new Historico("Bici1", "Estacion1");
		repo.save(hist);
		repo.save(hist2);
		
		List<Historico> historicos = repo.findByIdBiciAndIdEstacion("Bici1", "Estacion1");
		
		System.out.println("Historicos:");
		historicos.forEach(System.out::println);
		System.out.println("Fin-Historicos:");
		
		hist.setFechaFin(LocalDateTime.now());
		repo.save(hist);
		
		System.out.println(repo.findByIdBiciAndIdEstacionAndFechaFinNull("Bici1", "Estacion1"));
		
		repo.delete(hist2);
		repo.delete(hist);

		contexto.close();


	}

}
