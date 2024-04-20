namespace Usuarios.Modelo;

public class Usuario
{
    public string Id {get; set;}
    public string Nombre {get; set;}

    public Usuario(string id, string nombre)
    {
        this.Id = id;
        this.Nombre = nombre;
    }
}