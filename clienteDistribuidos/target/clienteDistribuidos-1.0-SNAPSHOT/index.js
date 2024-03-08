



const formulario = document.querySelector('#formulario');
const consultarTodos = document.querySelector('#consultarTodos');

function agregarFilasATabla(resultados) {
    const tabla = document.getElementById('tabla').getElementsByTagName('tbody')[0];

    tabla.innerHTML = '';

    resultados.forEach(resultado => {
        const nuevaFila = tabla.insertRow();
        const idCell = nuevaFila.insertCell(0);
        const nombreCell = nuevaFila.insertCell(1);
        const anioCell = nuevaFila.insertCell(2);
        const editarCell = nuevaFila.insertCell(3);
        const eliminarCell = nuevaFila.insertCell(4);

        idCell.textContent = resultado.id;
        nombreCell.textContent = resultado.nombre;
        anioCell.textContent = resultado.anioLanzamiento;
        cargarBotones(nuevaFila,editarCell,tabla,eliminarCell)
    });

}

function cargarBotones(nuevaFila,editarCell,tabla,eliminarCell) {
    const btnEditar = document.createElement('button');
    btnEditar.textContent = 'Editar';
    btnEditar.addEventListener('click', () => {
        console.log('Editar videojuego de la fila:', nuevaFila.rowIndex);
    });
    editarCell.appendChild(btnEditar);

    const btnEliminar = document.createElement('button');
    btnEliminar.textContent = 'Eliminar';
    btnEliminar.addEventListener('click', () => {
        const idFila = nuevaFila.cells[0].textContent;
        console.log(idFila)
        const objetoEliminado = eliminarVideojuego(idFila);
        if (objetoEliminado) {
            tabla.deleteRow(nuevaFila.rowIndex - 1);
        }
    });
    eliminarCell.appendChild(btnEliminar);

}



formulario.addEventListener('submit', (e) => {
    e.preventDefault();

    const nombre = document.getElementById('nombre').value;
    const anio = document.getElementById('anio').value;

    const videojuego = crearVideojuego(nombre, anio);
    agregar(videojuego);
    formulario.reset();

});


consultarTodos.addEventListener('click', (e) => {
    e.preventDefault();
    consultarTodosVideojuegos();

});

function crearVideojuego(nombre, anio) {
    const videojuego = {
        "anioLanzamiento": parseInt(anio),
        "id": null,
        "nombre": nombre
    };
    return videojuego;
}

async function eliminarVideojuego(id) {
    const response = await fetch(`http://localhost:8080/ServicioREST/resources/Videojuego/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    });
    return resultado = await response.json();
}

async function consultarTodosVideojuegos() {

    const response = await fetch("http://localhost:8080/ServicioREST/resources/Videojuego", {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    });

    const resultado = await response.json();
    agregarFilasATabla(resultado)
}

async function agregar(videojuego) {

    const response = await fetch("http://localhost:8080/ServicioREST/resources/Videojuego", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(videojuego),
    });

    const resultado = await response.json();
    agregarFilaATabla(resultado)
}

function agregarFilaATabla(videojuego) {
    const tabla = document.getElementById('tabla').getElementsByTagName('tbody')[0];
    const nuevaFila = tabla.insertRow();
    const idCell = nuevaFila.insertCell(0);
    const nombreCell = nuevaFila.insertCell(1);
    const anioCell = nuevaFila.insertCell(2);
    const editarCell = nuevaFila.insertCell(3);
    const eliminarCell = nuevaFila.insertCell(4);

    idCell.textContent = videojuego.id;
    nombreCell.textContent = videojuego.nombre;
    anioCell.textContent = videojuego.anioLanzamiento;
    cargarBotones(nuevaFila,editarCell,tabla,eliminarCell);
}



