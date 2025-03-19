package edu.proyectocompiladores.demo.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String tipo;
    private String valor;

    @Override
    public String toString() {
        return "Token{" + "tipo='" + tipo + '\'' + ", valor='" + valor + '\'' + '}';
    }
}

