package repositorios;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public abstract class RepositorioMongoDB<T extends Identificable> 
											implements Repositorio<T, String>{

	public abstract MongoCollection<T> getColeccion();
	
	@Override
    public String add(T entity) throws RepositorioException {
        
        InsertOneResult resultado = this.getColeccion().insertOne(entity);
        
        if (resultado.getInsertedId() == null) {
            throw new RepositorioException("Error insertando documento");
        }
        
        return resultado.getInsertedId().asObjectId().getValue().toString();
        
    }

    @Override
    public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
        UpdateResult resultado = this.getColeccion().replaceOne(
        		new Document("_id", new ObjectId(entity.getId())), entity);
        
        if(resultado.getMatchedCount() == 0) {
            throw new EntidadNoEncontrada("El documento con _id: " + entity.getId() + ", no existe");
        }
        if(!resultado.wasAcknowledged()) {
            throw new RepositorioException("Error al actualizar el documento");
        }
        
    }

    @Override
    public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
        DeleteResult resultado = this.getColeccion().deleteOne(new Document("_id", new ObjectId(entity.getId())));
        
        if(resultado.getDeletedCount() == 0)
            throw new EntidadNoEncontrada("El documento con _id: " + entity.getId() + ", no existe");
        
        if(!resultado.wasAcknowledged())
            throw new RepositorioException("Error al borrar el documento");
        
    }

    @Override
    public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
    	T estacion = null;
        try {
            Bson query = Filters.eq("_id", new ObjectId(id)); 
            estacion = getColeccion().find(query).first();
        }
        catch(Exception e) {
            throw new RepositorioException("error getEditorialByPaises", e);
        }
        return estacion;
    }

    @Override
    public List<T> getAll() throws RepositorioException {
       
        return this.getColeccion().find().into(new ArrayList<>());
    }

    @Override
    public List<String> getIds() throws RepositorioException {
        
    	ArrayList<String> listado = new ArrayList<String>();
        try {
        	Document projection = new Document("_id", 1);
            FindIterable<T> resultados = getColeccion().find().projection(projection);

            resultados.forEach(doc -> listado.add(doc.getId()));
        }
        catch(Exception e) {
            throw new RepositorioException("error getIds", e);
        }
        return listado;
    }

}
