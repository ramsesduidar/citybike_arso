namespace Usuarios.DTOs;

public class AltaDTO
{
    public String CodigoActivacion {get; set;}
    public String? Usuario {get; set;}
    public String? Contraseña {get; set;}
    public String? Oauth2 {get; set;}
    public String Nombre {get; set;}
    public String Apellidos {get; set;}
    public String Direccion {get; set;}
}