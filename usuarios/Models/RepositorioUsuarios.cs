using MongoDB.Driver;
using Repositorios;
using Usuarios.Modelo;

namespace Usuarios.Repositorios;

public class RepositorioUsuariosMongoDB : IRepositorio<Usuario, string>
{
    private readonly IMongoCollection<Usuario> actividades;

    public RepositorioUsuariosMongoDB()
    {
        var client = new MongoClient("uri");
        var database = client.GetDatabase("arso");

        actividades = database.GetCollection<Usuario>("actividades.net");
    }

    public string Add(Usuario entity)
    {
        actividades.InsertOne(entity);

        return entity.Id;
    }

    public void Update(Usuario entity)
    {
        actividades.ReplaceOne(actividad => actividad.Id == entity.Id, entity);
    }

    public void Delete(Usuario entity)
    {
        actividades.DeleteOne(actividad => actividad.Id == entity.Id);
    }

    public List<Usuario> GetAll()
    {
        return actividades.Find(_ => true).ToList();
    }

    public Usuario GetById(string id)
    {
        return actividades
            .Find(actividad => actividad.Id == id)
            .FirstOrDefault();
    }

    public List<string> GetIds()
    {
        var todas =  actividades.Find(_ => true).ToList();

        return todas.Select(p => p.Id).ToList();

    }
}