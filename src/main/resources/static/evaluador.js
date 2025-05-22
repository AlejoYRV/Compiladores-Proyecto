// Define la clase Evaluador, encargada de validar y evaluar expresiones matemáticas con asignaciones.
class Evaluador {
    constructor() {
        // Diccionario donde se almacenan las variables y sus valores.
        this.variables = {};
        // Lista que almacenará los errores detectados durante la evaluación.
        this.errores = [];
    }

    // Método para evaluar un conjunto de asignaciones en forma de texto.
    evaluarAsignaciones(asignaciones) {
        // Limpia errores anteriores antes de comenzar la evaluación.
        this.errores = [];

        // Recorre cada línea del código fuente recibido.
        asignaciones.forEach((linea, index) => {
            // Calcula el número de línea (se suma 1 porque el índice inicia en 0).
            const numeroLinea = index + 1;

            // Verifica si la línea contiene el operador de asignación '<-'.
            if (!linea.includes('<-')) {
                // Si no lo contiene, se registra un error y se omite la línea.
                this.errores.push(`Línea ${numeroLinea}: Asignación inválida. Se esperaba '<-' según el Grupo 8.`);
                return;
            }

            // Separa la línea en dos partes: nombre de la variable y expresión matemática.
            const [nombre, expresion] = linea.split('<-').map(e => e.trim());

            // Verifica que ambos lados de la asignación estén presentes.
            if (!nombre || !expresion) {
                this.errores.push(`Línea ${numeroLinea}: Asignación incompleta.`);
                return;
            }

            // Verifica que no se estén usando agrupadores inválidos como () o {}.
            if (/[(){}]/.test(expresion)) {
                this.errores.push(`Línea ${numeroLinea}: Agrupación inválida. Solo se permiten corchetes '[ ]' en el Grupo 8.`);
                return;
            }

            try {
                // Evalúa la expresión matemática usando el método evaluarExpresion.
                const resultado = this.evaluarExpresion(expresion);

                // Verifica si el resultado es inválido (null, undefined o NaN).
                if (resultado === null || resultado === undefined || Number.isNaN(resultado)) {
                    throw new Error("Expresión inválida.");
                }

                // Si todo está bien, guarda el resultado en el diccionario de variables.
                this.variables[nombre] = resultado;
            } catch (err) {
                // Si ocurre un error al evaluar, lo registra con el número de línea.
                this.errores.push(`Línea ${numeroLinea}: ${err.message}`);
            }
        });
    }

    // Método para evaluar una expresión matemática que puede contener variables.
    evaluarExpresion(expr) {
        // Reemplaza corchetes '[' y ']' por paréntesis '(' y ')' para que eval los entienda.
        let exprModificada = expr.replace(/\[/g, '(').replace(/\]/g, ')');

        // Reemplaza el operador de potencia '^' por '**' que es válido en JavaScript.
        exprModificada = exprModificada.replace(/\^/g, '**');

        // Reemplaza los nombres de variables por sus valores correspondientes.
        exprModificada = exprModificada.replace(/\b[a-zA-Z_][a-zA-Z0-9_]*\b/g, match => {
            // Si la variable existe en el diccionario, la reemplaza.
            if (this.variables.hasOwnProperty(match)) {
                return this.variables[match];
            } else {
                // Si no existe, lanza un error indicando que la variable no está definida.
                throw new Error(`Variable no definida: '${match}'`);
            }
        });

        // Evalúa la expresión matemática modificada con 'eval' de JavaScript.
        return eval(exprModificada);
    }

    // Método para generar la salida final después de procesar las líneas.
    generarSalida() {
        // Si hay errores, devuelve el listado de errores detectados.
        if (this.errores.length > 0) {
            return "== Errores Detectados ==\n" + this.errores.join("\n");
        } else {
            // Si no hay errores, imprime los valores de las variables evaluadas.
            let salida = "== Resultado ==\n";
            for (const [clave, valor] of Object.entries(this.variables)) {
                salida += `${clave} <- ${valor}\n`;
            }
            return salida;
        }
    }
}
