package edu.proyectocompiladores.demo.servicio;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArbolSintacticoService {

    // Método principal que genera el JSON a partir del árbol sintáctico (ParseTree)
    public static Map<String, Object> generarJson(ParseTree tree) {
        return recorrer(tree);
    }

    // Método recursivo que convierte cada nodo del árbol en una estructura JSON
    private static Map<String, Object> recorrer(ParseTree node) {

        // 1. Eliminar nodos <EOF> que no aportan valor a la visualización
        if (node.getText().equals("<EOF>")) {
            return null;
        }

        // 2. Si es un token terminal (hoja), mostrar directamente su texto como nombre del nodo
        if (node instanceof TerminalNode) {
            Map<String, Object> json = new HashMap<>();
            json.put("name", node.getText()); // Ej: "10", "Resultado", "Var01"
            return json;
        }

        // 3. Recorrer recursivamente todos los hijos del nodo actual
        List<Map<String, Object>> children = new ArrayList<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            Map<String, Object> childJson = recorrer(node.getChild(i));
            if (childJson != null) {
                children.add(childJson);
            }
        }

        // 4. Obtener el nombre de la regla gramatical eliminando el sufijo "Context"
        String ruleName = node.getClass().getSimpleName().replaceAll("Context$", "");

        // 5. Si el nodo solo tiene un hijo y representa una regla poco útil, aplanamos ese nodo
        // Esto ayuda a que el árbol sea más claro visualmente, eliminando niveles innecesarios
        if (children.size() == 1 &&
                (ruleName.equals("Statement") ||
                 ruleName.equals("IdExpression") ||
                 ruleName.equals("NumberExpression"))) {
            return children.get(0);
        }

        // 6. Si tiene varios hijos, se mantiene el nodo como contenedor de esos hijos
        Map<String, Object> json = new HashMap<>();
        json.put("name", ruleName);       // Ej: "Program", "Expression", "Assignment"
        json.put("children", children);   // Lista de hijos en formato JSON

        return json;
    }
}


