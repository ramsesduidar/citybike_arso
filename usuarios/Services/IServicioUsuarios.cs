using Usuarios.Modelo;

namespace Usuarios.Servicios;

public interface IServicioUsuarios
{
    string SolicitudCodigoActivacion(string identificador);

    void AltaUsuario(string codigoActivacion, string id, string usuario, 
                string contraseña, string nombre, string apellidos, string direccion);
    void AltaUsuarioOAuth2(string codigoActivacion, string id, string usuario, 
                string oauth2, string nombre, string apellidos, string direccion);

    void BajaUsuario(string idUsuario);

    Dictionary<string, string> VerificarUsuarioContrasena(string usuario, string contrasena);
    Dictionary<string, string> VerificarUsuarioOAuth2(string oauth2);

    List<Usuario> GetUsuarios();
    Usuario GetUsuario(string id);
}