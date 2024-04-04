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
import estaciones.dominio.Historico;
import estaciones.repositorios.*;

@RestController
@RequestMapping("/historico")

public class HistoricoController {

    @Autowired
    private RepositorioHistorico repositorioHistorico;

    @GetMapping("/movimiento")
    public Historico registrarMovimiento(@RequestParam String idBici, @RequestParam String idEstacion) {
        return repositorioHistorico.registrarMovimiento(idBici, idEstacion);
    }

    @GetMapping("/finalizarMovimiento")
    public void finalizarMovimiento(@RequestParam String idBici, @RequestParam String idEstacion) {
    	repositorioHistorico.finalizarMovimiento(idBici, idEstacion);
    }

    @GetMapping("/{idBici}/{idEstacion}")
    public Historico consultarMovimientoActivo(@PathVariable String idBici, @PathVariable String idEstacion) {
        return repositorioHistorico.consultarMovimientoActivo(idBici, idEstacion);
    }

}