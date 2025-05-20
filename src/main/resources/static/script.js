document.addEventListener('DOMContentLoaded', () => {

    // Función para cambiar de pestaña
    function openTab(evt, tabName) {
        // Oculta todos los contenidos de las pestañas
        document.querySelectorAll(".tab-content").forEach(tab => {
            tab.style.display = "none";
        });

        // Quita la clase activa de todos los botones de pestañas
        document.querySelectorAll(".tab-button").forEach(button => {
            button.classList.remove("active");
        });

        // Muestra la pestaña seleccionada y marca el botón como activo
        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.classList.add("active");
    }

    // Obtener referencias a los elementos
    const btnCompilar = document.getElementById("analizarBtn");
    const btnTokens = document.getElementById("bttokens");
    const btnSimbolos = document.getElementById("btsimbolos");
    const btnErrores = document.getElementById("bterrores");
    const textareaTokens = document.getElementById("tokensArea");
    const textareaSimbolos = document.getElementById("tablaSimbolosArea");
    const textareaErrores = document.getElementById("erroresArea");

    const btnEvaluador = document.getElementById("bEvaluador");
    const textareaEvaluador = document.getElementById("evaluador-output");


    let resultadoAnalisis = null;
    
    // Evento para compilar y analizar el código
    btnCompilar.addEventListener('click', async () => {
        const codigo = document.getElementById("codigoFuente").value;

        try {
            const respuesta = await fetch("http://localhost:8080/api/analizar", {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: codigo,
            });

            resultadoAnalisis = await respuesta.json();
            
            if (resultadoAnalisis) {
                alert("Se ha compilado el código exitosamente");
            } else {
                alert("No se ha logrado compilar el código");
            }
        } catch (error) {
            console.error("Error al analizar el código:", error);
            alert("Error al analizar el código. Revisa la consola para más detalles.");
        }
    });

    // Evento para mostrar los Tokens
    btnTokens.addEventListener('click', (event) => {
        if (resultadoAnalisis) {
            mostrarTokens(resultadoAnalisis.tokens);
            openTab(event, "tokens");
        }
    });

    // Evento para mostrar la Tabla de Símbolos
    btnSimbolos.addEventListener('click', (event) => {
        if (resultadoAnalisis) {
            mostrarSimbolos(resultadoAnalisis.simbolos);
            openTab(event, "tablaSimbolos");
        }
    });

    // Evento para mostrar los Errores Léxicos
    btnErrores.addEventListener('click', (event) => {
        if (resultadoAnalisis) {
            mostrarErrores(resultadoAnalisis.errores);
            openTab(event, "errores");
        }
    });

    // Evento para mostrar Evaluador
    btnEvaluador.addEventListener("click", (event) => {
        openTab(event, "evaluador"); // Cambiar de pestaña

        const codigo = document.getElementById("codigoFuente").value;
        const lineas = codigo.split('\n');

        const evaluador = new Evaluador();
        evaluador.evaluarAsignaciones(lineas);

        // Mostrar resultados o errores
        textareaEvaluador.value = evaluador.generarSalida();
    });


    // Función para mostrar Tokens en el área de texto
    function mostrarTokens(tokens) {
        const tokensTexto = tokens.map(token => `<${token.tipo}> ${token.valor}`).join("\n");
        textareaTokens.value = tokensTexto;
    }

    // Función para mostrar la Tabla de Símbolos en el área de texto
    function mostrarSimbolos(simbolos) {
        let contador = 1;
        // Se construye el texto para cada símbolo
        const simbolosTexto = simbolos.map(sim => {
            // Índice con dos dígitos
            const indice = String(contador++).padStart(2, '0');
            // Formato: "num / contador / Identificador / ENTERO / Línea / Columna
            return `${indice} / ${sim.nombre} / ${sim.tipo01} / ${sim.tipo02} / Línea: ${sim.linea} / Columna: ${sim.columna}`;
        }).join("\n");

        textareaSimbolos.value = simbolosTexto;
    }

    // Función para mostrar Errores en el área de texto (ordenados por línea)
    function mostrarErrores(errores) {
        // Se ordenan los errores por número de línea
        errores.sort((a, b) => a.linea - b.linea);
        // Se construye el texto
        const erroresTexto = errores.map(error => `Línea ${error.linea}: ${error.descripcion}`).join("\n");
        textareaErrores.value = erroresTexto;
    }

});