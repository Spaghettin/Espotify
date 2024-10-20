
<%@page import= "datatypes.DataTema"%>
<%@page import= "java.util.Set"%>
<%@page import= "java.util.HashSet"%>
<%@page import= "controladores.Fabrica"%>
<%@page import= "controladores.iSistema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boostrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/boostrap/js/jquery-3.7.1.min.js"></script>
    <title>Alta Album</title>
</head>
<body class="bg-light">
    
 <jsp:include page="/template/header.jsp"/>
<div class="container d-flex justify-content-center align-items-start">
    <div class="login-container bg-white p-4 rounded shadow">
        <h1 class="text-center">Alta Album</h1>
        <form class="login-form" id="mainForm" enctype="multipart/form-data">
            <!--ALBUM-->
            <div class="form-group">
                <label for="nombreA">Nombre del Álbum:</label>
                <input type="text" name="nombreA" id="nombreA" class="form-control" required>
                <small id="albumError" class="text-danger" style="display:none;"></small>
            </div>
            <div class="form-group">
                <label for="anio">Año:</label>
                <input type="text" name="anio" id="anio" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="imagen">Cargar Imagen:</label>
                <input type="file" id="imagen" name="imagen" class="form-control" accept="image/*">
            </div>
            <!--GENERO-->
            <div class="form-group">
                <label for="nombreG">Género:</label>
                <input type="text" name="nombreG" id="nombreG" class="form-control">
                <small id="generoError" class="text-danger" style="display:none;"></small>
            </div>
            <button type="button" id="sendGenre" class="btn btn-outline-success">Añadir Género</button>
            <!--TEMA-->
            <div class="form-group">
                <label for="nombreT">Nombre del Tema:</label>
                <input type="text" name="nombreT" id="nombreT" class="form-control">
                <small id="temaError" class="text-danger" style="display:none;"></small>
            </div>
            <div class="form-group">
                <label for="duracion">Duración:</label>
                <input type="text" name="duracion" id="duracion" class="form-control">
            </div>
            <div class="form-group">
                <label for="pos">Posición:</label>
                <input type="text" name="pos" id="pos" class="form-control">
                <small id="posTemaError" class="text-danger" style="display:none;"></small>
            </div>
            <!--RADIO BUTTON-->
            <div class="form-group">
                <label>Tipo de dato:</label><br>
                <input type="radio" name="rbutton" id="direccionWebR" value="dirW">
                <label for="direccionWeb">Dirección Web:</label><br>
                <input type="radio" name="rbutton" id="archivoR"  value="archivo">
                <label for="archivo">Archivo:</label>
            </div>
            <!--INPUT DE RADIO BUTTON-->
            <div id="dirWRadio" style="display: none;">
                <div class="form-group">
                    <label for="direccionWeb">Dirección Web:</label>
                    <input type="url" name="direccionWeb" id="direccionWeb" class="form-control">
                </div>
            </div>
            <div id="archivoRadio" style="display: none;">
                <div class="form-group">
                    <label for="archivo">Archivo:</label>
                    <input type="text" name="archivo" id="archivo" class="form-control">
                </div>
            </div>
            
            <div><button type="button" id="sendTema" class="btn btn-outline-success">Añadir Tema</button></div>
            <button type="button" id="sendAlbum" class="btn btn-outline-success">Enviar</button>
        </form>
    </div>
    
     <div class="container" id="containerListas">
            <div class="login-container bg-white p-4 rounded shadow" id="generos-seleccionados">
                <h3>Géneros seleccionados:</h3>
                <ul id="listaGeneros"></ul>
            </div>
            <div class="login-container bg-white p-4 rounded shadow" id="temas-seleccionados">
                <h3>Temas seleccionados:</h3>
                <ul id="listaTemas"></ul>
            </div>
            <div class="login-container bg-white p-4 rounded shadow" id="arbol-generos">
                <h3>Árbol de Géneros:</h3>
                <ul id="genreTree">
                    <li>
                        <span class="toggle-tree">Géneros</span>
                        <ul style="display: none;">
                            <%
                                iSistema sys = new Fabrica().getSistema();
            HashSet<String> generosCargados = sys.getGeneros();
            session.setAttribute("generosCargados",generosCargados);
            String padre = "Genero";
            for (String nombreGenero : generosCargados){
            padre = sys.getPadre(nombreGenero);
            if(padre.equals("Genero")){
            %>
            <li><%=nombreGenero%></li><%
                        } else {
                %>
                        <li>
                            <span class="toggle-tree"><%= nombreGenero %></span>
                        </li>
                <%
                        }
                    }
                %>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>

<script src="${pageContext.request.contextPath}/boostrap/js/bootstrap.bundle.min.js"></script>
<script>
    const datodirW = document.getElementById("dirWRadio");
    const datoArch = document.getElementById("archivoRadio");
    
        document.querySelectorAll('input[name="rbutton"]').forEach((elem) => {
            elem.addEventListener("change", function() {
                if (document.getElementById("direccionWebR").checked) {
                    datodirW.style.display = "block";
                } else {
                    datodirW.style.display = "none";
                    datodirW.value = "";
                }
                
                if (document.getElementById("archivoR").checked) {
                    datoArch.style.display = "block";
                } else {
                    datoArch.style.display = "none";
                    datoArch.value = "";
                }
            });
        });

    let generos = [];
    let temas = [];

    
    const form = document.getElementById('mainForm');

    // Añadir género
    document.getElementById('sendGenre').addEventListener('click', function(event) { 
        event.preventDefault();
        var nombreG = document.getElementById('nombreG').value;

        if (nombreG && !generos.includes(nombreG)) {
            let formData = new FormData(form);

            fetch('SvAgregarGeneroAlbum', {
                method: 'POST',
                body: formData
            }).then(response => {
                if (response.ok) {
                    generos.push(nombreG);
                    document.getElementById('nombreG').value = "";
                    mostrarGeneros(); 
                } else {
                    alert("Error al añadir género.");
                }
            }).catch(error => {
                console.log('Error:', error);
            });
        } else {
            alert("El género ya está en la lista o el campo está vacío.");
        }
    });

    // Añadir tema
    document.getElementById('sendTema').addEventListener('click', function(event) {
        event.preventDefault();

        let nombreT = document.getElementById('nombreT').value;
        let duracion = document.getElementById('duracion').value;
        let pos = document.getElementById('pos').value;
        let direccionWeb = document.getElementById('direccionWeb').value;
        let archivo = document.getElementById('archivo').value;

        let temaExiste = temas.some(tema => tema.nombreT === nombreT || tema.pos === pos);

        if (temaExiste) {
            alert("El nombre del tema o la posición ya están en la lista.");
            return; // Detener el proceso si ya existe
        }

        if (nombreT && duracion && pos && (!direccionWeb || !archivo)) {  // Verifica las condiciones
            let formData = new FormData(form);

            fetch('SvAgregarTemaAlbum', {
                method: 'POST',
                body: formData
            }).then(response => {
                if (response.ok) {
                    let tema = {
                        nombreT: nombreT,
                        duracion: duracion,
                        pos: pos,
                        direccionWeb: direccionWeb,
                        archivo: archivo
                    };

                    temas.push(tema); 
                    document.getElementById('nombreT').value = "";
                    document.getElementById('duracion').value = "";
                    document.getElementById('pos').value = "";
                    document.getElementById('direccionWeb').value = "";
                    document.getElementById('archivo').value = "";
                    mostrarTemas(); 
                } else {
                    alert("Error al añadir tema.");
                }
            }).catch(error => {
                console.log('Error:', error);
            });
        } else {
            alert("Debe llenar los campos de tema correctamente.");
        }
    });
        
        // Función para mostrar géneros seleccionados en pantalla
        function mostrarGeneros() {
            let generosDiv = document.getElementById('listaGeneros');
            generosDiv.innerHTML = ""; // Limpiar contenido previo
            generos.forEach(function(genero) {
                let li = document.createElement('li');
                li.textContent = genero;
                generosDiv.appendChild(li); 
            });
        }

        // Función para mostrar temas seleccionados en pantalla
        function mostrarTemas() {
            let temasDiv = document.getElementById('listaTemas');
            temasDiv.innerHTML = "";
            temas.forEach(function(tema) {
                let li = document.createElement('li');
                li.textContent = "Tema: " + tema.nombreT + ", Posición: " + tema.pos;
                temasDiv.appendChild(li);
            });
        }

        document.getElementById('sendAlbum').addEventListener('click', function(event) {
            event.preventDefault();
            let formData = new FormData(form);
            
            let nombreA = document.getElementById('nombreA').value;
            let anio = document.getElementById('anio').value;
            let generosValidos = generos.length > 0; 
            let temasValidos = temas.length > 0; 
            
            const albumError = document.getElementById('albumError');
    
            if (albumError.style.display !== 'none') {
                alert('El nombre del álbum ya existe. Por favor, corrige el error antes de continuar.');
                return;
            }
            
            if(nombreA && anio && generosValidos && temasValidos){
              fetch('SvAltaAlbum', {
                method: 'POST',
                body: formData
            }).then(response => {
                if (response.ok) {
                    document.getElementById('nombreA').value = "";
                    document.getElementById('anio').value = "";
                    document.getElementById('imagen').value = "";
                    document.getElementById('nombreG').value = "";
                    document.getElementById('nombreT').value = "";
                    document.getElementById('duracion').value = "";
                    document.getElementById('pos').value = "";
                    document.getElementById('direccionWeb').value = "";
                    document.getElementById('archivo').value = "";
                    
                    document.getElementById('listaGeneros').innerHTML = "";
                    document.getElementById('listaTemas').innerHTML = "";
                    generos = [];
                    temas = [];

                } else {
                    alert("Error al añadir album.");
                }
            }).catch(error => {
                console.log('Error:', error);
            });
        }else {
                alert("Debe llenar los campos del album correctamente.");
            }
            
        });

        genreTree.addEventListener('click', function(e) {
                if (e.target.classList.contains('toggle-tree')) {
                    const subList = e.target.nextElementSibling;
                    if (subList) {
                        subList.style.display = subList.style.display === 'none' ? 'block' : 'none';
                        e.target.classList.toggle('toggle-tree-down');
                    }
                }
            });
            
            
//        let generos = document.getElementsByClassName('toggle-tree');
//                for let (genero : generos){
//                    genero.addEventListener('click', function() {
//                    document.getElementById('nombreG').value = this.value;  
//                )}
//            }

        // Verificación de campos y duplicados con AJAX
        $(document).ready(function() {
            
            let albumTimer, genreTimer, temaTimer, posTimer;
            const doneTypingInterval = 10;
            
            function checkField(field, errorElement, checkType) {
            const value = $(field).val();
            if (value.length > 0) {
                let url = '';
                let errorMessage = '';

                // Definir la URL y el mensaje de error basado en checkType
                if (checkType === 'checkAlbumName') {
                    url = 'SvAltaAlbum';
                    errorMessage = 'Ya existe';
                } else if (checkType === 'checkGenre') {
                    url = 'SvAgregarGeneroAlbum';
                    errorMessage = 'Género ya existe';
                } else if (checkType === 'checkTema') {
                    url = 'SvAgregarTemaAlbum';
                    errorMessage = 'Tema ya existe';
                } else if (checkType === 'checkTemaPos') {
                    url = 'SvAgregarTemaAlbum';
                    errorMessage = 'Posición ya está en uso';
                }

                $.ajax({
                    url: url,
                    method: 'POST',
                    data: { action: checkType, value: value },
                    success: function(response) {
                        
                        if (response === 'exists') {
                            $(errorElement).text(errorMessage).show();
                        } else {
                            $(errorElement).hide();
                        }
                    },
                    error: function() {
                        $(errorElement).text('Error al verificar').show();
                    }
                });
            } else {
                $(errorElement).hide();
            }
    }

           // Verificar el nombre del álbum
            $('#nombreA').on('input', function() {
                clearTimeout(albumTimer);
                albumTimer = setTimeout(() => checkField('#nombreA', '#albumError', 'checkAlbumName'), doneTypingInterval);
            });-

            // Verificar género duplicado
            $('#nombreG').on('input', function() {
                clearTimeout(genreTimer);
                genreTimer = setTimeout(() => checkField('#nombreG', '#generoError', 'checkGenre'), doneTypingInterval);
            });

            // Verificar tema duplicado
            $('#nombreT').on('input', function() {
                clearTimeout(temaTimer);
                temaTimer = setTimeout(() => checkField('#nombreT', '#temaError', 'checkTema'), doneTypingInterval);
            });

            // Verificar posición del tema duplicada
            $('#pos').on('input', function() {
                clearTimeout(posTimer);
                posTimer = setTimeout(() => checkField('#pos', '#posTemaError', 'checkTemaPos'), doneTypingInterval);
            });

        });
    </script>
    
</body>

</html>

<style>

.login-container {
          max-width: 500px;
          margin: 20px;
      }
      #containerListas {
          max-width: 500px;
      }
      #genreTree {
          list-style-type: none;
          padding-left: 0;
      }
      #genreTree ul {
          list-style-type: none;
          padding-left: 20px;
      }
      .toggle-tree {
          cursor: pointer;
          user-select: none;
      }
      .toggle-tree::before {
          content: '\25B6';
          color: black;
          display: inline-block;
          margin-right: 6px;
      }
      .toggle-tree-down::before {
          transform: rotate(90deg);
      }
</style>