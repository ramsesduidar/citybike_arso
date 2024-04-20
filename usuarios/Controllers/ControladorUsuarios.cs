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

    [HttpPut("{id}")]
    public ActionResult<string> SolicitudCodigoActivacion(string id)
    {
        return "hola";
    }

    [HttpPost()]
    public IActionResult AltaUsuario(AltaDTO alta)
    {
        return NoContent();
    }

    [HttpDelete("{id}")]
    public IActionResult BajaUsuario(string id)
    {
        return NoContent();
    }

    [HttpGet("{id}")]
    public ActionResult<Dictionary<string, string>> VerificarUsuarioContrasena(string usuario, string contrasena)
    {
        return NoContent();
    }
    
    [HttpGet("{id}")]
    public ActionResult<Dictionary<string, string>> VerificarUsuarioOAuth2(string oauth2)
    {
            return NoContent();
    }

    [HttpGet]
    public ActionResult<List<Usuario>> GetUsuarios()
    {
        return NoContent();
    }
}