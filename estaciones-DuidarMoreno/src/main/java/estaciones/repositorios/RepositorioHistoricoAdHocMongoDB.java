package estaciones.repositorios;

import java.io.IOException;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import estaciones.dominio.Historico;
import repositorios.RepositorioException;
import repositorios.RepositorioMongoDB;

@Repository
public class RepositorioHistoricoAdHocMongoDB extends RepositorioMongoDB<Historico>
											implements RepositorioHistoricoAdHoc{

	private MongoCollection<Historico> coleccion;
	
	@Override
    public MongoCollection<Historico> getColeccion() {
        return this.coleccion;
    }
	
	public RepositorioHistoricoAdHocMongoDB() {

		String connectionString = "mongodb://ramsesdm:1hnmV75Fz2EXd44Y@ac-cnlmv4x-shard-00-00.8o0l6d2.mongodb.net:27017,ac-cnlmv4x-shard-00-01.8o0l6d2.mongodb.net:27017,ac-cnlmv4x-shard-00-02.8o0l6d2.mongodb.net:27017/?ssl=true&replicaSet=atlas-pwuan5-shard-0&authSource=admin&retryWrites=true&w=majority";
		String databaseString = "Cluster0";


		MongoClient mongoClient = MongoClients.create(connectionString);

		MongoDatabase database = mongoClient.getDatabase(databaseString);

		CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		coleccion = database.getCollection("historico", Historico.class)
				.withCodecRegistry(defaultCodecRegistry);

	}

	@Override
	public Historico getHistorico(String id_bici, String id_estacion) throws RepositorioException {
		Historico historico = null;
        try {
            Bson query = Filters.and(Filters.eq("idEstacion", id_estacion), Filters.eq("idBici", id_bici)); 
            historico = getColeccion().find(query).first();
        }
        catch(Exception e) {
            throw new RepositorioException("error getHistorico", e);
        }
        return historico;
	}
	
}
