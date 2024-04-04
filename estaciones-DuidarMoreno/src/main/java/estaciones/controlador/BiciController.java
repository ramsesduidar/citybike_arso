package estaciones.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import estaciones.dominio.Bici;
import estaciones.repositorios.*;

@RestController
@RequestMapping("/bicis")
public class BiciController {

    @Autowired
    private RepositorioBici repositorioBici;

    @GetMapping("/")
    public List<Bici> getAllBicis() {
        return repositorioBici.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Bici> getBiciById(@PathVariable String id) {
        return repositorioBici.findById(id);
    }

    @PostMapping("/")
    public Bici createBici(@RequestBody Bici bici) {
        return repositorioBici.create(bici);
    }

    @PutMapping("/{id}")
    public Bici updateBici(@PathVariable String id, @RequestBody Bici bici) {
        return repositorioBici.update(id, bici);
    }

    @DeleteMapping("/{id}")
    public void deleteBici(@PathVariable String id) {
    	repositorioBici.delete(id);
    }
}