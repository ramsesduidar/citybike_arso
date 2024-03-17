package encuestas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import encuestas.modelo.Encuesta;

@NoRepositoryBean
public interface RepositorioEncuestas 
	extends PagingAndSortingRepository<Encuesta, String>{
//	extends CrudRepository<Encuesta, String>{
	
	List<Encuesta> findByTitulo(String titulo);
	
	
}
