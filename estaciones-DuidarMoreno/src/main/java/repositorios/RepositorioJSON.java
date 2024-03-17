package repositorios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.spi.JsonbProvider;
import javax.json.stream.JsonGenerator;

import estaciones.dominio.SitioTuristico;

public abstract class RepositorioJSON<T extends Identificable> implements Repositorio<T, String>{

	public final static String DIRECTORIO = "repositorio-JSON-B/";

	static {

		File directorio = new File(DIRECTORIO);

		if (!directorio.exists())
			directorio.mkdir();
	}
	
	public abstract Class<T> getClase();
	
	/*** Métodos de apoyo ***/

	protected String getDocumento(String id) {

		return DIRECTORIO + getClase().getSimpleName() + "-" + id.replace(" ", "_") + ".json";
	}

	protected boolean checkDocumento(String id) {

		final String documento = getDocumento(id);

		File fichero = new File(documento);

		return fichero.exists();
	}

	protected void save(T entity) throws RepositorioException {

		final String documento = getDocumento(entity.getId());

		final File fichero = new File(documento);

		try {

			Jsonb contexto = JsonbProvider.provider().create().build();
			
			String cadenaJSON = contexto.toJson(entity);
			/*OutputStreamWriter writer = new FileWriter(fichero);
			JsonGenerator generador = Json.createGenerator(writer);
			generador.write(cadenaJSON);
			generador.close();*/
			BufferedWriter writer = new BufferedWriter(new FileWriter(fichero));
		    writer.write(cadenaJSON);
		    
		    writer.close();

		} catch (Exception e) {

			throw new RepositorioException("Error al guardar la entidad con id: " + entity.getId(), e);
		}
	}

	protected T load(String id) throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocumento(id))
			throw new EntidadNoEncontrada("La entidad no existe, id: " + id);

		final String documento = getDocumento(id);

		try {
			Jsonb contexto = JsonbProvider.provider().create().build();
			Reader entrada = new FileReader(documento);
			T entity = contexto.fromJson(entrada, this.getClase());
			
			return entity;

		} catch (Exception e) {
			throw new RepositorioException("Error al cargar la entidad con id: " + id, e);
		}
	}

	/*** Fin métodos de apoyo ***/

	@Override
	public String add(T entity) throws RepositorioException {
		save(entity);

		return entity.getId();
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		
		if (!checkDocumento(entity.getId()))
			throw new EntidadNoEncontrada("La entidad no existe, id: " + entity.getId());

		save(entity);
		
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		
		if (!checkDocumento(entity.getId()))
			throw new EntidadNoEncontrada("La entidad no existe, id: " + entity.getId());

		final String documento = getDocumento(entity.getId());

		File fichero = new File(documento);

		fichero.delete();
		
	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		
		
		return load(id);
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		
		LinkedList<T> resultado = new LinkedList<T>();

		for (String id : getIds()) {
			
			try {
				resultado.add(load(id));
			} catch (EntidadNoEncontrada e) {
				// no debería suceder
				e.printStackTrace();
			} 
		}
		
		return resultado;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		LinkedList<String> resultado = new LinkedList<>();

		File directorio = new File(DIRECTORIO);

		File[] entidades = directorio.listFiles(f -> f.isFile() && f.getName().endsWith(".json"));

		final String prefijo = getClase().getSimpleName() + "-";
		for (File file : entidades) {

			String id = file.getName().substring(prefijo.length(), file.getName().length() - 4);

			resultado.add(id);
		}

		return resultado;
	}
}
