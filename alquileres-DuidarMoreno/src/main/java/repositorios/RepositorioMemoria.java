package repositorios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RepositorioMemoria<T extends Identificable> implements Repositorio<T, String> {
	
	private HashMap<String, T> entidades = new HashMap<>();
	
	
	@Override
	public String add(T entity) throws RepositorioException {
		
		if(entity.getId() == null) {
			
			entity.setId(UUID.randomUUID().toString());
		}
		
		this.entidades.put(entity.getId(), entity);	
		
		return entity.getId();
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		
		if (! this.entidades.containsKey(entity.getId()))
			throw new EntidadNoEncontrada("La entidad con id: " + entity.getId() + " no existe en el repositorio");
		
		this.entidades.put(entity.getId(), entity);
		
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		
		if (! this.entidades.containsKey(entity.getId()))
			throw new EntidadNoEncontrada("La entidad con id: " + entity.getId() + " no existe en el repositorio");
		
		this.entidades.remove(entity.getId());
		
	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		
		T entidad = this.entidades.get(id);
		
		if(entidad == null)
			throw new EntidadNoEncontrada("La entidad con id: "+id+ ", no existe en el repositorio");
		
		return entidad;
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		
		return new ArrayList<T>( this.entidades.values() );
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		
		return new ArrayList<String>(this.entidades.keySet());
	}

}
