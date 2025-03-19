package edu.proyectocompiladores.demo.modelo;

import java.util.List;
import java.util.ArrayList;

public class ResultadoAnalisis {

    private List<Token> tokens;
    private List<simbolo> simbolos;

    public ResultadoAnalisis(List<Token> tokens){
        this.tokens = tokens;
    }

    public List<Token> getTokens(){
        return this.tokens;
    }

    public void setTokens(List<Token> tokens){
        this.tokens = tokens;
    }
    
}