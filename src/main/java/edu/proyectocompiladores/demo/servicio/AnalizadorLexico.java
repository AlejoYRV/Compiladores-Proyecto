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
    //Clases y atributos
    private ArrayList<Token> tokens;
    private List<simbolo> simbolos;
    private ArrayList<ErrorLexico> errores;
    private HashMap<String, String> tiposIdentificadores; // Mapa para los tipos de variables

    // Expresiones regulares para la detección de tokens en el código fuente
    private static final String COMENTARIO_UNA_LINEA = "//.*";
    private static final String COMENTARIO_MULTILINEA = "/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/";
    private static final String PALABRA_RESERVADA = "\\b(Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena|Funcion|Retorno|Verdadero|Falso|Nulo)\\b";
    private static final String IDENTIFICADOR = "\\b(?!Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena|Funcion|Retorno|Verdadero|Falso|Nulo)[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMERO = "-?\\d+(\\.\\d+)?(?:[eE][+-]?\\d+)?";
    private static final String HEXADECIMAL = "0[xX][0-9a-fA-F]+";
    private static final String OPERADOR = "(\\+\\+|--|==|!=|>=|<=|&&|\\|\\||[+\\-*/=<>!&|^#])";
    private static final String SIMBOLO = "[{}();,\\[\\]:?]";
    private static final String CADENA = "\"[^\"]*\"";

    //Patrón de tokens usando las expresiones regulares
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            String.join("|", COMENTARIO_UNA_LINEA, COMENTARIO_MULTILINEA, PALABRA_RESERVADA, IDENTIFICADOR,
                    NUMERO, HEXADECIMAL, OPERADOR, SIMBOLO, CADENA));

    
    //Constructor
    public AnalizadorLexico() {
        tokens = new ArrayList<>();
        simbolos = new ArrayList<>();
        errores = new ArrayList<>();
        tiposIdentificadores = new HashMap<>();
    }

    //Analizar código; detecta errores, clasifica tokens, asocia identificadores
    public void analizarCodigo(String codigo) {
        tokens.clear();
        simbolos.clear();
        errores.clear();
        tiposIdentificadores.clear();

        Matcher matcher = TOKEN_PATTERN.matcher(codigo); //para buscar tokens
        int lastIndex = 0;
        String ultimoTipo = null;

        while (matcher.find()) { //itera sobre todos los tokens encontrados
            int tokenStart = matcher.start();
            // Procesa cualquier fragmento (entre tokens) en busca de errores
            if (tokenStart > lastIndex) {
                String fragmento = codigo.substring(lastIndex, tokenStart);
                detectarErrores(fragmento, codigo, lastIndex);
            }
            String tokenEncontrado = matcher.group();
            // Calcula la posición (línea y columna) usando el índice global
            int[] pos = obtenerLineaColumna(codigo, tokenStart);
            int linea = pos[0];
            int columna = pos[1];

            Token token = clasificarToken(tokenEncontrado, linea, columna); //Clasifica token
            if (token != null) {
                tokens.add(token);
                // Si el token es un identificador y se detectó previamente un tipo de dato, se asocia
                if (ultimoTipo != null && token.getTipo().equals("Identificador")) {
                    tiposIdentificadores.put(token.getValor(), ultimoTipo);
                    simbolos.add(new simbolo(token.getValor(), "Identificador", ultimoTipo, 
                            String.valueOf(linea), String.valueOf(columna)));
                    ultimoTipo = null;
                } else if (token.getTipo().equals("Identificador")) {
                    String tipo = tiposIdentificadores.getOrDefault(token.getValor(), "VARIABLE");
                    simbolos.add(new simbolo(token.getValor(), "Identificador", tipo, 
                            String.valueOf(linea), String.valueOf(columna)));
                }
                // Si el token es una palabra reservada que define tipo, se almacena para asociarlo al siguiente identificador
                if (token.getTipo().equals("Palabra Reservada") &&
                        (token.getValor().equals("Entero") || token.getValor().equals("Real") ||
                         token.getValor().equals("Cadena") || token.getValor().equals("Booleano"))) {
                    ultimoTipo = token.getValor().toUpperCase();
                }
            }
            lastIndex = matcher.end();
        }

        // Procesa posibles errores al final del código
        if (lastIndex < codigo.length()) {
            String fragmento = codigo.substring(lastIndex);
            detectarErrores(fragmento, codigo, lastIndex);
        }
    }

    // Calcula la línea y columna basándose en la posición global en el código
    private int[] obtenerLineaColumna(String codigo, int pos) {
        int linea = 1;
        int ultimaPos = -1;
        for (int i = 0; i < pos; i++) {
            if (codigo.charAt(i) == '\n') {
                linea++;
                ultimaPos = i;
            }
        }
        int columna = pos - ultimaPos;
        return new int[]{linea, columna};
    }

    // Detecta errores en un fragmento del código y calcula su posición global
    private void detectarErrores(String fragmento, String codigo, int offset) {
        for (int i = 0; i < fragmento.length(); i++) {
            char c = fragmento.charAt(i);
            int globalPos = offset + i;
            int[] pos = obtenerLineaColumna(codigo, globalPos);
            int linea = pos[0];
            int columna = pos[1];
            if (!Character.isWhitespace(c) &&
                !String.valueOf(c).matches("[a-zA-Z0-9_{}();,+\\-*/=<>!&|^#.\\[\\]:?\"]")) {
                errores.add(new ErrorLexico(linea, columna, "Carácter inválido '" + c + "'"));
            }
        }
    }

    //Clasifica un token según su tipo, si no coincide con ninguno, lo registra como error
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
        errores.add(new ErrorLexico(linea, columna, "Token inválido '" + tokenEncontrado + "'"));
        return null;
    }

    //Devuelve el resultado del análisis; tokens, símbolos y errores encontrados
    public ResultadoAnalisis resultadoAnalisis(String codigo) {
        analizarCodigo(codigo);
        return new ResultadoAnalisis(tokens, simbolos, errores);
    }
}
