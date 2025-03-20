package edu.proyectocompiladores.demo.controlador;

import edu.proyectocompiladores.demo.modelo.*;
import edu.proyectocompiladores.demo.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controlador {
    @Autowired
    private AnalizadorLexico analizadorLexico;//conexión entre backend y frontend

    //Método para analizar código
    @PostMapping("/analizar")
    private ResultadoAnalisis analizarCodigo(@RequestBody String codigo)
    {
        return analizadorLexico.resultadoAnalisis(codigo);
    }
}
