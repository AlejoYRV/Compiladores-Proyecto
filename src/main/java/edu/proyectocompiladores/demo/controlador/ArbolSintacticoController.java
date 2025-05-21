package edu.proyectocompiladores.demo.controlador;

import edu.proyectocompiladores.demo.parser.*;
import edu.proyectocompiladores.demo.servicio.ArbolSintacticoService;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/arbol")
public class ArbolSintacticoController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerArbol(@RequestParam String codigo) {
        // 1. Crear lexer y parser
        CharStream input = CharStreams.fromString(codigo);
        AlgebraGrupo8Lexer lexer = new AlgebraGrupo8Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AlgebraGrupo8Parser parser = new AlgebraGrupo8Parser(tokens);

        // 2. Crear árbol
        ParseTree tree = parser.program();

        // 3. Convertir a JSON con el servicio
        Map<String, Object> arbolJson = ArbolSintacticoService.generarJson(tree);

        // 4. Devolver JSON al frontend
        return ResponseEntity.ok(arbolJson);
    }
}

