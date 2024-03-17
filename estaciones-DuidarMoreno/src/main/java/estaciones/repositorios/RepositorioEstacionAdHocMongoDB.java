package estaciones.repositorios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import estaciones.dominio.Estacion;
import repositorios.RepositorioException;
import repositorios.RepositorioMongoDB;

@Repository
public class RepositorioEstacionAdHocMongoDB extends RepositorioMongoDB<Estacion>
													implements RepositorioEstacionAdHoc{

	private MongoCollection<Estacion> coleccion;
	
	@Override
    public MongoCollection<Estacion> getColeccion() {
        return this.coleccion;
    }
	
	public RepositorioEstacionAdHocMongoDB() {

		String connectionString = "mongodb://ramsesdm:1hnmV75Fz2EXd44Y@ac-cnlmv4x-shard-00-00.8o0l6d2.mongodb.net:27017,ac-cnlmv4x-shard-00-01.8o0l6d2.mongodb.net:27017,ac-cnlmv4x-shard-00-02.8o0l6d2.mongodb.net:27017/?ssl=true&replicaSet=atlas-pwuan5-shard-0&authSource=admin&retryWrites=true&w=majority";
		String databaseString = "Cluster0";


		MongoClient mongoClient = MongoClients.create(connectionString);

		MongoDatabase database = mongoClient.getDatabase(databaseString);

		CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		coleccion = database.getCollection("estacion", Estacion.class)
				.withCodecRegistry(defaultCodecRegistry);
		// creamos un indice para las consultas geoEspaciales
		coleccion.createIndex(Indexes.geo2dsphere("coordenadas"));


	}
public static void main(String[] args) {
	Bson addFiledsStage = Aggregates.addFields(new Field<Document>("arrSize", new Document("$size", "$sitiosTuristicos")));
	Bson sortStage = Aggregates.sort(Sorts.descending("arrSize"));
     
	System.out.println(addFiledsStage.toString());
	System.out.println(sortStage.toString());
}
	@Override
	public String buscarPrimeraLibre() throws RepositorioException {
		Document query = new Document("$expr", new Document("$lt", Arrays.asList(new Document("$size", "$idBicis"), "$numPuestos")));
		Estacion estacion = null;
        try {
            estacion = getColeccion().find(query).first();
        }
        catch(Exception e) {
            throw new RepositorioException("error buscarPrimeraLibre", e);
        }
        return estacion.getId();
	}

	@Override
	public List<Estacion> obtener3MasCercanas(Double latitud, Double longitud) throws RepositorioException {
		List<Estacion> estacionesCercanas = new ArrayList<>();

	    try {
	        Bson query = Filters.nearSphere("coordenadas", new Point(new Position(latitud, longitud)), null, null);
	        estacionesCercanas = coleccion.find(query).limit(3).into(new ArrayList<>());
	    } catch (Exception e) {
	    	throw new RepositorioException("error obtener3MasCercanas", e);
	    }

	    return estacionesCercanas;
	}

	@Override
	public List<Estacion> getEstacionesPorSitios() throws RepositorioException {
		List<Estacion> estacionesOrdenadas = new ArrayList<>();

	    try {
	    	Bson addFiledsStage = Aggregates.addFields(new Field<Document>("arrSize", new Document("$size", "$sitiosTuristicos")));
	    	Bson sortStage = Aggregates.sort(Sorts.descending("arrSize"));
	    	List<Bson> pipeline = Arrays.asList(addFiledsStage, sortStage);
	    	coleccion.aggregate(pipeline).into(estacionesOrdenadas);   

	    } catch (Exception e) {
	    	throw new RepositorioException("error getEstacionesPorSitios", e);
	    }

	    return estacionesOrdenadas;
	}
}
