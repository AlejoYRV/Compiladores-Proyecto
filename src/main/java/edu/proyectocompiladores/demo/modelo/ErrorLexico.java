package edu.proyectocompiladores.demo.modelo;


//Nueva clase para manejar los errores de forma más completa
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

