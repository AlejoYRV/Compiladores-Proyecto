package edu.proyectocompiladores.demo.modelo;

public class simbolo {
    private String nombre;
    private String tipo01;
    private String tipo02;
    private String linea;
    private String columna;

    //Constructor con cuatro parámetros
    public simbolo(String nombre, String tipo01, String tipo02, String linea, String columna) {
        this.nombre = nombre;
        this.tipo01 = tipo01;
        this.tipo02 = tipo02;
        this.linea = linea;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo01() {
        return tipo01;
    }

    public String getTipo02() {
        return tipo02;
    }

    public String getLinea() {
        return linea;
    }

    public String getColumna() {
        return columna;
    }

    //Devuelve una información sobre símbolo, en cadena
    @Override
    public String toString() {
        return "Simbolo{" +
                "nombre='" + nombre + '\'' +
                ", tipo01='" + tipo01 + '\'' +
                ", tipo02='" + tipo02 + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }
}
