package edu.proyectocompiladores.demo.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.proyectocompiladores.demo.modelo.*;
import org.springframework.stereotype.Service;

@Service
public class AnalizadorLexico {
    private ArrayList<Token> tokens;
    private List<simbolo> simbolos;
    private ArrayList<ErrorLexico> errores;
    private HashMap<String, String> tiposIdentificadores; // Mapa para los tipos de variables

    // Expresiones regulares corregidas
    private static final String COMENTARIO_UNA_LINEA = "//.*";
    private static final String COMENTARIO_MULTILINEA = "/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/";
    private static final String PALABRA_RESERVADA = "\\b(Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena|Funcion|Retorno|Verdadero|Falso|Nulo)\\b";
    private static final String IDENTIFICADOR = "\\b(?!Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena|Funcion|Retorno|Verdadero|Falso|Nulo)[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMERO = "-?\\d+(\\.\\d+)?(?:[eE][+-]?\\d+)?";
    private static final String HEXADECIMAL = "0[xX][0-9a-fA-F]+";
    private static final String OPERADOR = "(\\+\\+|--|==|!=|>=|<=|&&|\\|\\||[+\\-*/=<>!&|^#])";
    private static final String SIMBOLO = "[{}();,\\[\\]:?]";
    private static final String CADENA = "\"[^\"]*\"";

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            String.join("|", COMENTARIO_UNA_LINEA, COMENTARIO_MULTILINEA, PALABRA_RESERVADA, IDENTIFICADOR, NUMERO, HEXADECIMAL, OPERADOR, SIMBOLO, CADENA));

    public AnalizadorLexico() {
        tokens = new ArrayList<>();
        simbolos = new ArrayList<>();
        errores = new ArrayList<>();
        tiposIdentificadores = new HashMap<>();
    }

    public void analizarCodigo(String codigo) {
        tokens.clear();
        simbolos.clear();
        errores.clear();
        tiposIdentificadores.clear();

        Matcher matcher = TOKEN_PATTERN.matcher(codigo);
        int lastIndex = 0;
        int linea = 1;
        int columna = 1;
        String ultimoTipo = null;  // Variable para almacenar el tipo de dato

        while (matcher.find()) {
            // Detectar caracteres no reconocidos entre tokens
            if (matcher.start() > lastIndex) {
                detectarErrores(codigo.substring(lastIndex, matcher.start()), linea, columna);
            }

            String tokenEncontrado = matcher.group();
            Token token = clasificarToken(tokenEncontrado, linea, columna);
            if (token != null) {
                tokens.add(token);
                // Manejo de identificadores y tipos
                if (ultimoTipo != null && token.getTipo().equals("Identificador")) {
                    // Si venimos de un tipo de variable, se almacena el tipo
                    tiposIdentificadores.put(token.getValor(), ultimoTipo);
                    simbolos.add(new simbolo(token.getValor(), "Identificador", ultimoTipo, String.valueOf(linea), String.valueOf(columna)));
                    ultimoTipo = null;
                } else if (token.getTipo().equals("Identificador")) {
                    // Si ya existe, se marca como VARIABLE
                    String tipo = tiposIdentificadores.getOrDefault(token.getValor(), "VARIABLE");
                    simbolos.add(new simbolo(token.getValor(), "Identificador", tipo, String.valueOf(linea), String.valueOf(columna)));
                }

                // Guardar tipo de variable encontrado
                if (token.getTipo().equals("Palabra Reservada") && (
                        token.getValor().equals("Entero") || token.getValor().equals("Real") ||
                        token.getValor().equals("Cadena") || token.getValor().equals("Booleano"))) {
                    ultimoTipo = token.getValor().toUpperCase();
                }
            }

            lastIndex = matcher.end();
            columna += tokenEncontrado.length();

            // Ajustar líneas y columnas tomando en cuenta los saltos de línea del token encontrado
            int saltos = contarSaltosDeLinea(tokenEncontrado);
            linea += saltos;
            if (tokenEncontrado.contains("\n")) {
                int lastNewline = tokenEncontrado.lastIndexOf('\n');
                columna = tokenEncontrado.length() - lastNewline;
            }
        }

        // Revisar si quedaron caracteres no reconocidos al final
        if (lastIndex < codigo.length()) {
            detectarErrores(codigo.substring(lastIndex), linea, columna);
        }
    }

    private Token clasificarToken(String tokenEncontrado, int linea, int columna) {
        if (tokenEncontrado.matches(COMENTARIO_UNA_LINEA)) {
            return new Token("Comentario", tokenEncontrado);
        }
        if (tokenEncontrado.matches(COMENTARIO_MULTILINEA)) {
            return new Token("Comentario", tokenEncontrado);
        }
        if (tokenEncontrado.matches(PALABRA_RESERVADA)) {
            return new Token("Palabra Reservada", tokenEncontrado);
        }
        if (tokenEncontrado.matches(IDENTIFICADOR)) {
            return new Token("Identificador", tokenEncontrado);
        }
        if (tokenEncontrado.matches(NUMERO)) {
            return new Token("Número", tokenEncontrado);
        }
        if (tokenEncontrado.matches(HEXADECIMAL)) {
            return new Token("Hexadecimal", tokenEncontrado);
        }
        if (tokenEncontrado.matches(OPERADOR)) {
            return new Token("Operador", tokenEncontrado);
        }
        if (tokenEncontrado.matches(SIMBOLO)) {
            return new Token("Signo", tokenEncontrado);
        }
        if (tokenEncontrado.matches(CADENA)) {
            return new Token("Cadena", tokenEncontrado);
        }

        // En caso de llegar aquí, se trata de un token inválido
        errores.add(new ErrorLexico(linea, columna, "Carácter inválido '" + tokenEncontrado + "'"));
        return null;
    }

    // Función para detectar errores en fragmentos no reconocidos,
    // contando correctamente los saltos de línea y asignando la línea y columna correspondientes.
    private void detectarErrores(String fragmento, int linea, int columna) {
        int currentLine = linea;
        int currentColumn = columna;
        for (char c : fragmento.toCharArray()) {
            if (c == '\n') {
                currentLine++;
                currentColumn = 1;
            } else {
                // Si el caracter no es un espacio u otro caracter permitido, se marca como error
                if (!Character.isWhitespace(c) && !Character.toString(c).matches("[a-zA-Z0-9_{}();,+\\-*/=<>!&|^#.\\[\\]:?\"]")) {
                    errores.add(new ErrorLexico(currentLine, currentColumn, "Carácter inválido '" + c + "'"));
                }
                currentColumn++;
            }
        }
    }

    private int contarSaltosDeLinea(String token) {
        int count = 0;
        for (char c : token.toCharArray()) {
            if (c == '\n') {
                count++;
            }
        }
        return count;
    }

    public ResultadoAnalisis resultadoAnalisis(String codigo) {
        analizarCodigo(codigo);
        return new ResultadoAnalisis(tokens, simbolos, errores);
    }
}
