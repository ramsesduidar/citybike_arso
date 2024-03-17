package repositorios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import estaciones.dominio.Estacion;

public class RepositorioMemoria<T extends Identificable> implements Repositorio<T, String>{
	
	private int id = 0;
	private HashMap<String, T> entidades = new HashMap<>();

	@Override
	public String add(T entity) {
		
		String id = String.valueOf(this.id++);
		entity.setId(id);
		this.entidades.put(id, entity);	
		
		return id;
	}

	@Override
	public void update(T entity) throws EntidadNoEncontrada {
		
		if (! this.entidades.containsKey(entity.getId()))
			throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
		
		this.entidades.put(entity.getId(), entity);
	}

	@Override
	public void delete(T entity) throws EntidadNoEncontrada {
		
		if (! this.entidades.containsKey(entity.getId()))
			throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
		
		this.entidades.remove(entity.getId());
	}

	@Override
	public T getById(String id) throws EntidadNoEncontrada {
		
		if (! this.entidades.containsKey(id))
			throw new EntidadNoEncontrada(id + " no existe en el repositorio");
		
		return this.entidades.get(id);
	}

	@Override
	public List<T> getAll() {
		
		return new ArrayList<>(this.entidades.values());
	}

	@Override
	public List<String> getIds() {
		
		return new ArrayList<>(this.entidades.keySet());
	}
}
