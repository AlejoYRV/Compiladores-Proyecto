package edu.proyectocompiladores.demo.controlador;

import edu.proyectocompiladores.demo.modelo.*;
import edu.proyectocompiladores.demo.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/evaluar")
    public ResponseEntity<?> evaluarExpresion(@RequestBody String input){
       
        try{
            Map<String, Double> resultado = analizadorLexico.evaluarExpresion(input);
            return ResponseEntity.ok(resultado);
        }catch(RuntimeException e){
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
}
