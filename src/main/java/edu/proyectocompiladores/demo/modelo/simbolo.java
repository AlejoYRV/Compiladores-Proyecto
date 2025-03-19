package edu.proyectocompiladores.demo.modelo;

public class simbolo {
    private String nombre;
    private String tipo;
    private String linea;
    private String columna;
    
    public simbolo(String nombre, String tipo, String linea, String columna){
        this.nombre=nombre;
        this.tipo=tipo;
        this.linea=linea;
        this.columna=columna;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }
}
