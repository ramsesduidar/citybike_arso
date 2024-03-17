package encuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import encuestas.repositorio.RepositorioEncuestas;

public class ProgramaRepositorio {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = 
				SpringApplication.run(EncuestasApp.class, args);
		
		
		RepositorioEncuestas repo = 
				contexto.getBean(RepositorioEncuestas.class);
		
		System.out.println(repo.count());
		
		contexto.close();
	}
}
