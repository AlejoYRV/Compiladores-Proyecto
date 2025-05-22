package edu.proyectocompiladores.demo.servicio;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArbolSintacticoService {

    public static Map<String, Object> generarJson(ParseTree tree) {
        return recorrer(tree);
    }

    private static Map<String, Object> recorrer(ParseTree node) {
        // Ignorar nodo <EOF>
        if (node.getText().equals("<EOF>")) {
            return null;
        }

        // Si es nodo terminal (hoja), usar el texto como nombre
        if (node instanceof TerminalNode) {
            Map<String, Object> hoja = new HashMap<>();
            hoja.put("name", node.getText());
            return hoja;
        }

        // Recorrer los hijos
        List<Map<String, Object>> hijos = new ArrayList<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            Map<String, Object> hijo = recorrer(node.getChild(i));
            if (hijo != null) {
                hijos.add(hijo);
            }
        }

        // Nombre del nodo sin "Context"
        String nombreNodo = node.getClass().getSimpleName().replaceAll("Context$", "");

        // Estructura del nodo actual
        Map<String, Object> json = new HashMap<>();
        json.put("name", nombreNodo);

        // Si solo tiene un hijo y el nombre es redundante, puedes opcionalmente omitir este nivel.
        // Pero aquí lo dejamos explícito para que el árbol sea claro y completo.
        if (!hijos.isEmpty()) {
            json.put("children", hijos);
        }

        return json;
    }
}

