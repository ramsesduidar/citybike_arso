using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Usuarios.Modelo;

public enum EstadoUsuario{
    EN_SOLICITUD,
    DE_ALTA,
    DE_BAJA
}

public enum RolUsuario{
    Usuario,
    Gestor
}

public class Usuario
{
    public EstadoUsuario Estado {get; set;}
    public RolUsuario Rol {get; set;}

    [BsonId]
    [BsonRepresentation(BsonType.String)]
    public string Id {get; set;}
    public string? Username {get; set;}
    public string? Contrasena {get; set;}
    public string? OAuth2 {get; set;}
    public string? Nombre {get; set;}
    public string? Apellidos {get; set;}
    public string? Direccion {get; set;}

    public CodigoActivacion CodigoActivacion {get; set;}

    public Usuario(string id, CodigoActivacion codigo)
    {
        this.Id = id;
        this.CodigoActivacion = codigo;
        this.Estado = EstadoUsuario.EN_SOLICITUD;
        this.Rol = RolUsuario.Usuario;
    }
}