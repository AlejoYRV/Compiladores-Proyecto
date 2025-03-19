package edu.proyectocompiladores.demo.modelo;

import java.util.List;

public class ResultadoAnalisis {

    private List<Token> tokens;
    private List<simbolo> simbolos;
    private List<ErrorLexico> errores; 

    public ResultadoAnalisis(List<Token> tokens, List<simbolo> simbolos, List<ErrorLexico> errores){
        this.tokens = tokens;
        this.simbolos = simbolos;
        this.errores = errores; 
    }

    public List<Token> getTokens(){
        return this.tokens;
    }

    public void setTokens(List<Token> tokens){
        this.tokens = tokens;
    }

    public List<simbolo> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(List<simbolo> simbolos) {
        this.simbolos = simbolos;
    }

    public List<ErrorLexico> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorLexico> errores) {
        this.errores = errores;
    }
    
}

