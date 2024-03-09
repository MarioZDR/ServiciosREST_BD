
idVideojuegoEdicionActual = -1;
const formulario = document.querySelector('#formulario');

const formularioNombre = document.querySelector('#nombre');
const formularioAnio = document.querySelector('#anio');
const botonFormulario = document.querySelector('#btnAgregar');

const consultarTodos = document.querySelector('#consultarTodos');
const consultarPorId = document.querySelector('#consultarPorId');
const consultarPorFiltrosBoton = document.querySelector('#consultarPorFiltros');

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
        cargarBotones(nuevaFila, editarCell, tabla, eliminarCell)
    });

}

function cargarBotones(nuevaFila, editarCell, tabla, eliminarCell) {
    const btnEditar = document.createElement('button');
    btnEditar.textContent = 'Editar';
    btnEditar.addEventListener('click', () => {
        const idFila = nuevaFila.cells[0].textContent;
        const nombreFila = nuevaFila.cells[1].textContent;
        const anioFila = nuevaFila.cells[2].textContent;

        formularioNombre.value = nombreFila;
        formularioAnio.value = anioFila;
        botonFormulario.value = "Editar";
        idVideojuegoEdicionActual = idFila;
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

    if (idVideojuegoEdicionActual === -1) {
        const videojuego = crearVideojuego(nombre, anio);
        formulario.reset();
        agregar(videojuego);
    } else {
        const videojuegoEditado = editarVideojuego(nombre, anio);
        editar(videojuegoEditado);

        botonFormulario.value = "Agregar";
        idVideojuegoEdicionActual = -1;
    }

});


consultarTodos.addEventListener('click', (e) => {
    e.preventDefault();
    consultarTodosVideojuegos();

});

consultarPorFiltrosBoton.addEventListener('click', (e) => {
    e.preventDefault();

    const anio = document.getElementById('anioLanzamiento').value;
    const nombre = document.getElementById('nombreVideojuego').value;

    if (anio.trim() !== "" && nombre.trim() !== "") {
        consultarPorFiltros(anio, nombre);
    }

});

consultarPorId.addEventListener('click', (e) => {
    e.preventDefault();

    const id = document.getElementById('idConsultar').value;

    if (id.trim() !== "") {
        consultarPorIdVideojuego(id);
    }
});

function vaciarTabla() {
    var tbody = document.getElementById('tabla').getElementsByTagName('tbody')[0];
    tbody.innerHTML = '';
}

function crearVideojuego(nombre, anio) {
    const videojuego = {
        "anioLanzamiento": parseInt(anio),
        "id": null,
        "nombre": nombre
    };
    return videojuego;
}

function editarVideojuego(nombre, anio) {
    const videojuego = {
        "anioLanzamiento": parseInt(anio),
        "id": parseInt(idVideojuegoEdicionActual),
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
    agregarFilasATabla(resultado);
}

async function consultarPorIdVideojuego(id) {
    const response = await fetch(`http://localhost:8080/ServicioREST/resources/Videojuego/${id}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    });

    vaciarTabla();
    if (response.ok) {
        const resultado = await response.json();
        if (resultado) {
            agregarFilaATabla(resultado);
        }
    } else if (response.status === 404) {
        alert("No se encontro un videojuego con ese id");
    }
}

async function consultarPorFiltros(anio, nombre) {
    const response = await fetch(`http://localhost:8080/ServicioREST/resources/Videojuego/query?anioLanzamiento=${anio}&inicioNombre=${nombre}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    });

    vaciarTabla();
    if (response.ok) {
        const resultados = await response.json();
        if (resultados) {
            if (resultados.length > 1) {
                agregarFilasATabla(resultados);
            } else {
                agregarFilaATabla(Object.values(resultados)[0]);
            }
        }
    } else if (response.status === 404) {
        alert("No se encontro un videojuego con esos filtros");
    }
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

async function editar(videojuego) {

    const response = await fetch("http://localhost:8080/ServicioREST/resources/Videojuego", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(videojuego),
    });

    const resultado = await response.json();
    modificarFilaTabla(resultado);
    formulario.reset();
}

function modificarFilaTabla(videojuegoModificado) {
    const tabla = document.getElementById('tabla').getElementsByTagName('tbody')[0];
    for (let i = 0; i < tabla.rows.length; i++) {
        const fila = tabla.rows[i];
        const idFila = fila.cells[0].textContent;
        if (idFila === videojuegoModificado.id.toString()) {
            fila.cells[1].textContent = videojuegoModificado.nombre;
            fila.cells[2].textContent = videojuegoModificado.anioLanzamiento;
            break;
        }
    }
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
    cargarBotones(nuevaFila, editarCell, tabla, eliminarCell);
}



