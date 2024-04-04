package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.EstadoBici;
import estaciones.dominio.Historico;
import estaciones.dominio.Incidencia;
import estaciones.dto.IncidenciaDTO;
import estaciones.repositorios.*;
import estaciones.servicios.ServicioIncidencias;
import repositorios.EntidadNoEncontrada;
import servicios.ServicioException;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {

    @Autowired
    private ServicioIncidencias servicioIncidencias;

    @PostMapping("/")
    public IncidenciaDTO crearIncidencia(@RequestBody IncidenciaDTO incidenciaDTO) throws ServicioException, DataAccessException, EntidadNoEncontrada {
        String idBici = incidenciaDTO.getId_bici();
        String descripcion = incidenciaDTO.getDescripcion();
        servicioIncidencias.crearIncidencia(idBici, descripcion);
        return incidenciaDTO;
    }

    @GetMapping("/{id}")
    public IncidenciaDTO recuperarIncidencia(@PathVariable String id) throws ServicioException, DataAccessException, EntidadNoEncontrada {
        return servicioIncidencias.recuperarIncidencia(id);
    }

    @GetMapping("/abiertas")
    public List<IncidenciaDTO> recuperarIncidenciasAbiertas() throws ServicioException {
        List<Incidencia> incidenciasAbiertas = servicioIncidencias.recuperarIncidenciasAbiertas();
        List<IncidenciaDTO> incidenciasDTO = new ArrayList<>();
        for (Incidencia incidencia : incidenciasAbiertas) {
            incidenciasDTO.add(new IncidenciaDTO(incidencia));
        }
        return incidenciasDTO;
    }

    @PutMapping("/{id}/cancelar")
    public void cancelarIncidencia(@PathVariable String id, @RequestParam String motivoCierre) throws ServicioException, DataAccessException, EntidadNoEncontrada {
        servicioIncidencias.cancelarIncidencia(id, motivoCierre);
    }

    @PutMapping("/{id}/asignar")
    public void asignarIncidencia(@PathVariable String id, @RequestParam String operario) throws ServicioException, DataAccessException, EntidadNoEncontrada {
        servicioIncidencias.asignarIncidencia(id, operario);
    }

    @PutMapping("/{id}/resolver/reparada")
    public void resolverIncidenciaReparada(@PathVariable String id, @RequestParam String motivoCierre) throws ServicioException, DataAccessException, EntidadNoEncontrada {
        servicioIncidencias.resolverIncidenciaReparada(id, motivoCierre);
    }

    @PutMapping("/{id}/resolver/no-reparada")
    public void resolverIncidenciaNoReparada(@PathVariable String id, @RequestParam String motivoCierre) throws ServicioException, DataAccessException, EntidadNoEncontrada {
        servicioIncidencias.resolverIncidenciaNoReparada(id, motivoCierre);
    }

}