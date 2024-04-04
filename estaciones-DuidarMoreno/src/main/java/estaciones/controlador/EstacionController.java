package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import estaciones.dominio.Bici;
import estaciones.dominio.Estacion;
import estaciones.dominio.EstadoBici;
import estaciones.repositorios.*;

@RestController
@RequestMapping("/estaciones")

public class EstacionController {

    @Autowired
    private RepositorioEstacion repositorioEstacion;

    @GetMapping("/")
    public List<Estacion> getAllEstaciones() {
        return repositorioEstacion.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Estacion> getEstacionById(@PathVariable String id) {
        return repositorioEstacion.findById(id);
    }

    @PostMapping("/")
    public Estacion createEstacion(@RequestBody Estacion estacion) {
        return repositorioEstacion.create(estacion);
    }

    @PutMapping("/{id}")
    public Estacion updateEstacion(@PathVariable String id, @RequestBody Estacion estacion) {
        return repositorioEstacion.update(id, estacion);
    }

    @DeleteMapping("/{id}")
    public void deleteEstacion(@PathVariable String id) {
    	repositorioEstacion.delete(id);
    }

    @GetMapping("/cercanas")
    public List<Estacion> getEstacionesCercanas(@RequestParam Double latitud, @RequestParam Double longitud) {
        return repositorioEstacion.findCercanas(latitud, longitud);
    }

    @GetMapping("/{id}/bicis")
    public List<Bici> getBicisEnEstacion(@PathVariable String id) {
        return repositorioEstacion.findBicisEnEstacion(id);
    }

    @GetMapping("/disponibles")
    public List<Estacion> getEstacionesDisponibles() {
        return repositorioEstacion.findDisponibles();
    }

    @GetMapping("/porCapacidad")
    public List<Estacion> getEstacionesPorCapacidad(@RequestParam int capacidadMinima) {
        return repositorioEstacion.findByCapacidadGreaterThan(capacidadMinima);
    }

    @GetMapping("/porBicisDisponibles")
    public List<Estacion> getEstacionesPorBicisDisponibles(@RequestParam int bicicletasDisponiblesMin) {
        return repositorioEstacion.findByBicisDisponiblesGreaterThan(bicicletasDisponiblesMin);
    }

    @GetMapping("/porEstadoBici")
    public List<Estacion> getEstacionesPorEstadoBici(@RequestParam EstadoBici estadoBici) {
        return repositorioEstacion.findByEstadoBici(estadoBici);
    }
}