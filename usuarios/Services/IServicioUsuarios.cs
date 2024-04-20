using Usuarios.Modelo;

namespace Usuarios.Servicios;

public interface IServicioUsuarios
{
    string SolicitudCodigoActivacion(string identificador);

    bool AltaUsuario(string codigoActivacion, string usuario, string contraseña, string nombre, string apellidos, string direccion);
    bool AltaUsuarioOAuth2(string codigoActivacion, string usuario, string oauth2, string nombre, string apellidos, string direccion);

    void BajaUsuario(string idUsuario);

    Dictionary<string, string> VerificarUsuarioContrasena(string usuario, string contrasena);
    Dictionary<string, string> VerificarUsuarioOAuth2(string oauth2);

    List<Usuario> GetUsuarios();
}