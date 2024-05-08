using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using Usuarios.DTOs;
using Usuarios.Modelo;
using Usuarios.Servicios;

namespace Usuarios.Controlador;

[Route("api/usuarios")]
[ApiController]
public class UsuarioController : ControllerBase
{
    private readonly IServicioUsuarios servicio;

    public UsuarioController(IServicioUsuarios servicio)
    {
        this.servicio = servicio;
    }

    [HttpPost()]
    public ActionResult<string> SolicitudCodigoActivacion([FromBody]string id)
    {
        return this.servicio.SolicitudCodigoActivacion(id);
    }

    [HttpPost("{id}")]
    public IActionResult AltaUsuario(AltaDTO alta)
    {
        if(alta.Contraseña != null && alta.Oauth2 != null)
            return BadRequest("Contraseña y OAuth2 no pueden estar asignados a la vez"); 
        
        if(alta.Contraseña != null)
        {
            this.servicio.AltaUsuario(alta.CodigoActivacion, alta.Id, alta.Usuario,
                                    alta.Contraseña, alta.Nombre, alta.Apellidos, alta.Direccion);
        }
        if(alta.Oauth2 != null)
        {
            this.servicio.AltaUsuarioOAuth2(alta.CodigoActivacion, alta.Id, alta.Usuario,
                                    alta.Oauth2, alta.Nombre, alta.Apellidos, alta.Direccion);
        }
        return NoContent();
    }

    [HttpDelete("{id}")]
    public IActionResult BajaUsuario(string id)
    {
        this.servicio.BajaUsuario(id);

        return NoContent();
    }

    [HttpPost("{usuario}/contrasena")]
    public ActionResult<Dictionary<string, string>> VerificarUsuarioContrasena(string usuario, [FromBody]string contrasena)
    {
        return this.servicio.VerificarUsuarioContrasena(usuario, contrasena);
    }
    
    [HttpGet("{oauth2}/oauth2")]
    public ActionResult<Dictionary<string, string>> VerificarUsuarioOAuth2(string oauth2)
    {
        return this.servicio.VerificarUsuarioOAuth2(oauth2);
    }

    [HttpGet]
    public ActionResult<List<Usuario>> GetUsuarios()
    {
        return this.servicio.GetUsuarios();
    }
}