package edu.proyectocompiladores.demo.modelo;

public class ErrorLexico {
    private int linea;
    private String mensaje;

    public ErrorLexico(int linea, String mensaje) {
        this.linea = linea;
        this.mensaje = mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

