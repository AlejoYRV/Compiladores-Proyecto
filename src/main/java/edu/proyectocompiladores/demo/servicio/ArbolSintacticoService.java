package edu.proyectocompiladores.demo.servicio;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArbolSintacticoService {

    public static Map<String, Object> generarJson(ParseTree tree) {
        return recorrer(tree);
    }

    private static Map<String, Object> recorrer(ParseTree node) {
        Map<String, Object> json = new HashMap<>();
        String nodeName = node.getClass().getSimpleName().replaceAll("Context$", "");
        json.put("name", nodeName);

        if (node.getChildCount() > 0) {
            List<Map<String, Object>> children = new ArrayList<>();
            for (int i = 0; i < node.getChildCount(); i++) {
                children.add(recorrer(node.getChild(i)));
            }
            json.put("children", children);
        }

        return json;
    }
}

