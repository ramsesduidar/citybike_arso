using Repositorios;
using Usuarios.Modelo;

namespace Usuarios.Servicios;

public class ServicioUsuarios : IServicioUsuarios
{
    private IRepositorio<Usuario, string> repositorio;

    public ServicioUsuarios(IRepositorio<Usuario, string> repositorio)
    {
        this.repositorio = repositorio;
    }
/*
-Solicitud código de activación (para el gestor): tiene como parámetro el identificador de un usuario. 
La operación genera un código asociado al usuario con un plazo de validez. Este código será requerido 
en el proceso de alta. Nota: existirá un proceso administrativo, ajeno a la aplicación, 
que permite al usuario solicitar el alta en la aplicación. Si cumple los requisitos, 
el gestor activa un código para que se proceda con el alta.
*/
    public string SolicitudCodigoActivacion(string identificador)
    {
        return "hola";
    }

/*
-Operación alta de un usuario. En la información del alta se proporciona un código de activación 
que se utiliza para comprobar que el usuario está autorizado a realizar el alta. 
Esta operación solo da de alta usuarios de la aplicación, ya que los gestores se dan de alta 
directamente en la base de datos. En relación a la información relativa al proceso de autenticación, 
se ofrecen dos alternativas: con usuario/contraseña y mediante OAuth2. Si se establece una constraseña, 
se opta por la primera opción. En cambio, si se elige OAuth2, se añadirá a la petición un campo 
con el identificador de usuario OAuth2 de GitHub. Los gestores tendrán autenticación con usuario/contraseña. 
El resto de información proporcionada en el proceso de alta se deja a criterio 
de cada grupo (nombre completo, correo electrónico, teléfono, dirección postal, etc.).
*/
    public bool AltaUsuario(string codigoActivacion, string usuario, 
                    string contraseña, string nombre, string apellidos, string direccion)
    {
        return true;
    }

    public bool AltaUsuarioOAuth2(string codigoActivacion, string usuario, 
                    string oauth2, string nombre, string apellidos, string direccion)
    {
        return true;
    }

/*
-Baja de un usuario (ofrecida al gestor).
*/
    public void BajaUsuario(string idUsuario)
    {

    }

/*
-Verificar las credenciales de un usuario. En el proceso de login con usuario/contraseña este operación 
comprueba que exista el usuario y que coincida la contraseña.
*/
    public Dictionary<string, string> VerificarUsuarioContrasena(string usuario, string contrasena)
    {
        return new Dictionary<string, string>();
    }

/*
-Verificar usuario OAuth2. En el proceso de autenticación OAuth2 esta operación comprueba 
si el identificador OAuth2 de GitHub corresponde con alguno de los usuarios registrados en la aplicación, 
es decir, de un usuario registrado con autenticación OAuth2.
-En las dos operaciones de verificación se retorna un mapa con los claims 
(identificador de usuario, nombre completo, rol) que se utilizarán para emitir un token JWT.
*/
    public Dictionary<string, string> VerificarUsuarioOAuth2(string oauth2)
    {
        return new Dictionary<string, string>();
    }

/*
-Listado de todos los usuarios (para el gestor).
*/

    public List<Usuario> GetUsuarios()
    {
        return new List<Usuario>();
    }
}