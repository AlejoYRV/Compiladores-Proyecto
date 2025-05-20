class Evaluador {
    constructor() {
        this.variables = {};
        this.errores = [];
    }

    evaluarAsignaciones(asignaciones) {
        this.errores = []; // Limpia errores anteriores

        asignaciones.forEach((linea, index) => {
            const numeroLinea = index + 1;

            if (!linea.includes('<-')) {
                this.errores.push(`Línea ${numeroLinea}: Asignación inválida. Se esperaba '<-' según el Grupo 8.`);
                return;
            }

            const [nombre, expresion] = linea.split('<-').map(e => e.trim());

            if (!nombre || !expresion) {
                this.errores.push(`Línea ${numeroLinea}: Asignación incompleta.`);
                return;
            }

            if (/[(){}]/.test(expresion)) {
                this.errores.push(`Línea ${numeroLinea}: Agrupación inválida. Solo se permiten corchetes '[ ]' en el Grupo 8.`);
                return;
            }

            try {
                const resultado = this.evaluarExpresion(expresion);
                if (resultado === null || resultado === undefined || Number.isNaN(resultado)) {
                    throw new Error("Expresión inválida.");
                }
                this.variables[nombre] = resultado;
            } catch (err) {
                this.errores.push(`Línea ${numeroLinea}: ${err.message}`);
            }
        });
    }

    evaluarExpresion(expr) {
        // Reemplaza corchetes por paréntesis válidos
        let exprModificada = expr.replace(/\[/g, '(').replace(/\]/g, ')');

        // Reemplaza el operador de potencia
        exprModificada = exprModificada.replace(/\^/g, '**');

        // Reemplaza variables
        exprModificada = exprModificada.replace(/\b[a-zA-Z_][a-zA-Z0-9_]*\b/g, match => {
            if (this.variables.hasOwnProperty(match)) {
                return this.variables[match];
            } else {
                throw new Error(`Variable no definida: '${match}'`);
            }
        });

        // Evalúa la expresión
        return eval(exprModificada);
    }

    generarSalida() {
        if (this.errores.length > 0) {
            return "== Errores Detectados ==\n" + this.errores.join("\n");
        } else {
            let salida = "== Resultado ==\n";
            for (const [clave, valor] of Object.entries(this.variables)) {
                salida += `${clave} <- ${valor}\n`;
            }
            return salida;
        }
    }
}
