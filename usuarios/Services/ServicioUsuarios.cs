using Repositorios;
using Usuarios.Modelo;
using Usuarios.Repositorios;

namespace Usuarios.Servicios;

public class ServicioUsuarios : IServicioUsuarios
{
    private RepositorioUsuariosMongoDB repositorio;

    public ServicioUsuarios(RepositorioUsuariosMongoDB repositorio)
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
        
        Usuario usuario = this.repositorio.GetById(identificador);
        
        // Generamos el código de activación
        CodigoActivacion codigo = new CodigoActivacion(Guid.NewGuid());
        
        if (usuario == null)
        {
            // Creamos el nuevo usuario si no esta creado y le asignamos el código.
            usuario = new Usuario(identificador, codigo);

            // Guardmaos el usuario
            this.repositorio.Add(usuario);
            
            // Devolvemos el código
            return codigo.Valor.ToString();
        }

        if (usuario.Estado == EstadoUsuario.EN_SOLICITUD)
        {
            // Asignamos el nuevo código
            usuario.CodigoActivacion = codigo;

            // Actualizamos el usuario
            this.repositorio.Update(usuario);

            // Devolvemos el código
            return codigo.Valor.ToString();
        }
        else{
            throw new ArgumentException("El usuario ya está dado de alta");
        }
        
        
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
    public void AltaUsuario(string codigoActivacion, string id, string usuario, 
                    string contraseña, string nombre, string apellidos, string direccion)
    {
        
        Usuario usuario1 = this.repositorio.GetById(id);
        if (usuario1 == null) 
        {
            throw new ArgumentException("No existe una clave de activación asociada al id: " + id);
        }

        if(!usuario1.CodigoActivacion.Valor.ToString().Equals(codigoActivacion))
        {
            throw new ArgumentException("El identificador: " + id + " ,no está asociado a la clave: " + codigoActivacion);
        }

        if(usuario1.Estado != EstadoUsuario.EN_SOLICITUD)
        {
            throw new ArgumentException("El usuario con " + id + " , ya está dado de alta");
        }
        usuario1.Estado = EstadoUsuario.DE_ALTA;
        usuario1.Username = usuario;
        usuario1.Contrasena = contraseña;
        usuario1.Nombre = nombre;
        usuario1.Apellidos = apellidos;
        usuario1.Direccion = direccion;

        this.repositorio.Update(usuario1);
    }

    public void AltaUsuarioOAuth2(string codigoActivacion, string id, string usuario, 
                    string oauth2, string nombre, string apellidos, string direccion)
    {
        Usuario usuario1 = this.repositorio.GetById(id);
        if (usuario1 == null) 
        {
            throw new ArgumentException("No existe una clave de activación asociada al id: " + id);
        }

        if(!usuario1.CodigoActivacion.Valor.ToString().Equals(codigoActivacion))
        {
            throw new ArgumentException("El identificador: " + id + " ,no está asociado a la clave: " + codigoActivacion);
        }
        
        if(usuario1.Estado != EstadoUsuario.EN_SOLICITUD)
        {
            throw new ArgumentException("El usuario con " + id + " , ya está dado de alta");
        }

        usuario1.Estado = EstadoUsuario.DE_ALTA;
        usuario1.Username = usuario;
        usuario1.OAuth2 = oauth2;
        usuario1.Nombre = nombre;
        usuario1.Apellidos = apellidos;
        usuario1.Direccion = direccion;

        this.repositorio.Update(usuario1);
    }

/*
-Baja de un usuario (ofrecida al gestor).
*/
    public void BajaUsuario(string idUsuario)
    {
        Usuario usuario1 = this.repositorio.GetById(idUsuario);
        if (usuario1 == null) 
        {
            throw new EntidadNoEncontradaException("No existe el usuario con id: " + idUsuario);
        }

        usuario1.Estado = EstadoUsuario.DE_BAJA;

        this.repositorio.Update(usuario1);
    }

/*
-Verificar las credenciales de un usuario. En el proceso de login con usuario/contraseña este operación 
comprueba que exista el usuario y que coincida la contraseña.
*/
    public Dictionary<string, string> VerificarUsuarioContrasena(string usuario, string contrasena)
    {
        
        Usuario usuario1 = this.repositorio.GetByUsuarioContraseña(usuario, contrasena);
        if (usuario1 == null) 
        {
            throw new ArgumentException("Usuario o contraseña erroneos");
        }
        
        Dictionary<string, string> claims = new Dictionary<string, string>
        {
            { "Id", usuario1.Id },
            { "sub", usuario1.Username},
            { "Nombre", usuario1.Nombre + " " + usuario1.Apellidos },
            { "Roles", usuario1.Rol.ToString() }
        };

        return claims;
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
        Usuario usuario1 = this.repositorio.GetByOAuth2(oauth2);
        if (usuario1 == null) 
        {
            throw new ArgumentException("No hay usuarios asociados al oauth2: " + oauth2);
        }
        
        Dictionary<string, string> claims = new Dictionary<string, string>
        {
            { "Id", usuario1.Id },
            { "sub", usuario1.Username},
            { "Nombre", usuario1.Nombre + " " + usuario1.Apellidos },
            { "Roles", usuario1.Rol.ToString() }
        };

        return claims;
    }

/*
-Listado de todos los usuarios (para el gestor).
*/

    public List<Usuario> GetUsuarios()
    {
        return this.repositorio.GetAll();
    }

    public Usuario GetUsuario(string id)
    {
        return this.repositorio.GetById(id);
    }
}