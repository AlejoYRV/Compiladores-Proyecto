document.addEventListener('DOMContentLoaded', ()=>{

    const btnCompilar=document.getElementById("analizarBtn")
    const btnTokens=document.getElementById("bttokens")
    const btnsmbolos=document.getElementById("btsimbolos")
    const btnerrores=document.getElementById("bterrores")
    const textarearesultado=document.getElementById("tokensArea")

    let resultadoAnalisis = null;
    
    btnCompilar.addEventListener('click', async()=>{
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
            
            if(resultadoAnalisis){
                alert("Se ha compilado el código exitosamente");
            }else{
                alert("No se ha logrado compilar el código");
            }
        } catch (error) {
            console.error("Error al analizar el código:", error);
            alert("Error al analizar el código. Revisa la consola para más detalles.");
        }
        
    });

    btnTokens.addEventListener('click', ()=>{
        if(resultadoAnalisis){
            mostrarTokens(resultadoAnalisis.tokens);
        }
    });

    function mostrarTokens(tokens){
        const tokensTexto = tokens.map(token =>  `<${token.tipo}> ${token.valor}`).join("\n");
        textarearesultado.value = `${tokensTexto}`;
    }
});