using MongoDB.Driver;
using Repositorios;
using Usuarios.Modelo;

namespace Usuarios.Repositorios;

public interface IRepositorioUsuarios : IRepositorio<Usuario, string>{
    public Usuario GetByUsuarioContraseña(string username, string constraseña);
    
    public Usuario GetByOAuth2(string oauth2);

}

public class RepositorioUsuariosMongoDB : IRepositorioUsuarios
{
    private readonly IMongoCollection<Usuario> usuarios;

    public RepositorioUsuariosMongoDB()
    {
        // Leer la URI de conexión desde la variable de entorno
        string? mongoDbUri = Environment.GetEnvironmentVariable("MONGODB_URI");
        string? mongodatabase = Environment.GetEnvironmentVariable("MONGODB_DATABASE");

        Console.WriteLine("Variables de entorno: -" + mongoDbUri + "-" + mongoDbUri + "-");

        if (string.IsNullOrWhiteSpace(mongoDbUri) || string.IsNullOrWhiteSpace(mongodatabase))
        {
            Console.WriteLine("no variables de entorno para mongo");
            mongoDbUri = "mongodb+srv://ramsesdm:1hnmV75Fz2EXd44Y@cluster0.8o0l6d2.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
            mongodatabase = "Cluster0";
        }


        var client = new MongoClient(mongoDbUri);
        var database = client.GetDatabase(mongodatabase);

        usuarios = database.GetCollection<Usuario>("usuario");
    }

    public string Add(Usuario entity)
    {
        usuarios.InsertOne(entity);

        return entity.Id;
    }

    public void Update(Usuario entity)
    {
        usuarios.ReplaceOne(usuario => usuario.Id == entity.Id, entity);
    }

    public void Delete(Usuario entity)
    {
        usuarios.DeleteOne(usuario => usuario.Id == entity.Id);
    }

    public List<Usuario> GetAll()
    {
        return usuarios.Find(_ => true).ToList();
    }

    public Usuario GetById(string id)
    {
        return usuarios
            .Find(Usuario => Usuario.Id == id)
            .FirstOrDefault();
    }

    public Usuario GetByUsuarioContraseña(string username, string constraseña)
    {
        return usuarios
            .Find(usuario => usuario.Estado == EstadoUsuario.DE_ALTA
                            && usuario.Username == username 
                            && usuario.Contrasena == constraseña)
            .FirstOrDefault();
    }

    public Usuario GetByOAuth2(string oauth2)
    {
        return usuarios
            .Find(usuario => usuario.Estado == EstadoUsuario.DE_ALTA 
                            && usuario.OAuth2 == oauth2)
            .FirstOrDefault();
    }

    public List<string> GetIds()
    {
        var todas =  usuarios.Find(_ => true).ToList();

        return todas.Select(p => p.Id).ToList();

    }
}