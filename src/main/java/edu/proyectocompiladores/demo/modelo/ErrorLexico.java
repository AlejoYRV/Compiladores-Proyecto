package edu.proyectocompiladores.demo.modelo;

import java.io.Serializable;

public class ErrorLexico implements Serializable {
    private int linea;
    private int columna;
    private String descripcion;

    // Constructor
    public ErrorLexico(int linea, int columna, String descripcion) {
        this.linea = linea;
        this.columna = columna;
        this.descripcion = descripcion;
    }

    // Constructor vacío (para frameworks que lo requieran)
    public ErrorLexico() {
    }

    // Getters y Setters
    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Devuelve información sobre ErrorLéxico, en cadena
    @Override
    public String toString() {
        return "Error en línea " + linea + ", columna " + columna + ": " + descripcion;
    }
}

