package estaciones.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import estaciones.dominio.Estacion;
import estaciones.repositorios.RepositorioEstacion;

@RestController
@RequestMapping("/estaciones")
public class EstacionesControlador {

    @Autowired
    private RepositorioEstacion repositorioEstacion;

    @GetMapping
    public List<Estacion> getAll() {
        return repositorioEstacion.findAll();
    }

    @GetMapping("/{id}")
    public Estacion getById(@PathVariable String id) {
        return repositorioEstacion.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estacion no encontrada"));
    }

    @PostMapping
    public Estacion create(@RequestBody Estacion estacion) {
        return repositorioEstacion.save(estacion);
    }

    @PutMapping("/{id}")
    public Estacion update(@PathVariable String id, @RequestBody Estacion estacion) {
        Estacion estacionToUpdate = repositorioEstacion.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estacion no encontrada"));
        // ... (actualizar los campos de la estacionToUpdate)
        return repositorioEstacion.save(estacionToUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repositorioEstacion.deleteById(id);
    }
}