package edu.proyectocompiladores.demo.servicio;

import java.util.ArrayList;
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

    // Expresiones regulares mejoradas
    private static final String PALABRA_RESERVADA = "\\b(Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena|Funcion|Retorno|Verdadero|Falso|Nulo)\\b";
    private static final String IDENTIFICADOR = "\\b(?!Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena|Funcion|Retorno|Verdadero|Falso|Nulo)[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMERO = "-?\\d+(\\.\\d+)?(?:[eE][+-]?\\d+)?";
    private static final String HEXADECIMAL = "0[xX][0-9a-fA-F]+";
    private static final String OPERADOR = "[+\\-/=<>!&|]+|\\+=|\\-=|\\=|/=|==|!=|>=|<=|&&|\\|\\|";
    private static final String SIMBOLO = "[{}();,\\[\\]:?]";
    private static final String CADENA = "\"[^\"]*\"";
    private static final String COMENTARIO = "//.*|/\\*.*?\\*/"; // Soporta comentarios multilínea

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            String.join("|", PALABRA_RESERVADA, IDENTIFICADOR, NUMERO, HEXADECIMAL, OPERADOR, SIMBOLO, CADENA));

    public AnalizadorLexico() {
        tokens = new ArrayList<>();
        simbolos = new ArrayList<>();
        errores = new ArrayList<>();
    }

    public void analizarCodigo(String codigo) {
        tokens.clear();
        simbolos.clear();
        errores.clear();
        ArrayList<String> identificadoresUnicos = new ArrayList<>();

        // Remover comentarios antes de analizar
        codigo = codigo.replaceAll(COMENTARIO, "");

        Matcher matcher = TOKEN_PATTERN.matcher(codigo);
        int lastIndex = 0;
        int linea = 1;
        int columna = 1;

        while (matcher.find()) {
            // Detectar caracteres no reconocidos entre tokens
            if (matcher.start() > lastIndex) {
                detectarErrores(codigo.substring(lastIndex, matcher.start()), linea, columna);
            }

            String tokenEncontrado = matcher.group();
            Token token = clasificarToken(tokenEncontrado, linea, columna);
            if (token != null) {
                tokens.add(token);
                if (token.getTipo().equals("Identificador") && !identificadoresUnicos.contains(token.getValor())) {
                    identificadoresUnicos.add(token.getValor());
                    simbolos.add(new simbolo(token.getValor(), "Identificador", String.valueOf(linea), String.valueOf(columna)));
                }
            }

            lastIndex = matcher.end();
            columna = matcher.end() + 1;

            // Ajustar líneas y columnas correctamente
            linea += contarSaltosDeLinea(tokenEncontrado);
            columna = calcularNuevaColumna(tokenEncontrado, columna);
        }

        // Revisar si quedaron caracteres no reconocidos al final
        if (lastIndex < codigo.length()) {
            detectarErrores(codigo.substring(lastIndex), linea, columna);
        }
    }

    private Token clasificarToken(String tokenEncontrado, int linea, int columna) {
        if (tokenEncontrado.matches(PALABRA_RESERVADA))
            return new Token("Palabra Reservada", tokenEncontrado);
        if (tokenEncontrado.matches(IDENTIFICADOR))
            return new Token("Identificador", tokenEncontrado);
        if (tokenEncontrado.matches(NUMERO))
            return new Token("Número", tokenEncontrado);
        if (tokenEncontrado.matches(HEXADECIMAL))
            return new Token("Hexadecimal", tokenEncontrado);
        if (tokenEncontrado.matches(OPERADOR))
            return new Token("Operador", tokenEncontrado);
        if (tokenEncontrado.matches(SIMBOLO))
            return new Token("Símbolo Especial", tokenEncontrado);
        if (tokenEncontrado.matches(CADENA))
            return new Token("Cadena", tokenEncontrado);

        errores.add(new ErrorLexico(linea, columna, "Token inválido '" + tokenEncontrado + "'"));
        return null;
    }

    private void detectarErrores(String fragmento, int linea, int columna) {
        for (char c : fragmento.toCharArray()) {
            if (!Character.isWhitespace(c) && !Character.toString(c).matches("[a-zA-Z0-9_{}();,+\\-*/=<>!&|^#.\\[\\]:?\"]")) {
                errores.add(new ErrorLexico(linea, columna, "Carácter inválido '" + c + "'"));
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

    private int calcularNuevaColumna(String token, int columnaActual) {
        int lastNewline = token.lastIndexOf('\n');
        return (lastNewline == -1) ? columnaActual + token.length() : token.length() - lastNewline;
    }

    public ResultadoAnalisis resultadoAnalisis(String codigo) {
        analizarCodigo(codigo);
        return new ResultadoAnalisis(tokens, simbolos, errores);
    }
}







