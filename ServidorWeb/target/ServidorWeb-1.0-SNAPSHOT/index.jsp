<%@page import= "java.util.List"%>
<%@page import= "java.util.Vector"%>
<%@page import= "datatypes.DataTema"%>
<%@page import= "datatypes.DataGenero"%>
<%@page import= "datatypes.DataUsuario"%>
<%@page import= "datatypes.DataAlbum"%>
<%@page import= "datatypes.DataLista"%>
<%@page import= "controladores.Fabrica"%>
<%@page import= "controladores.iSistema"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Espotify</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!--Boostrap-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/boostrap/css/bootstrap.min.css">
        <script src="${pageContext.request.contextPath}/boostrap/js/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/boostrap/js/bootstrap.bundle.min.js"></script>
    </head>
    
    <body class="bg-light">
        <jsp:include page="/template/header.jsp" />
        <div class="container">  
            <h1></h1>
        </div>
        
        <div class="content-wrapper">
            <div class="sidebar">
                <div class="sidebar-option" data-option="generos">Generos</div>
                <div class="sidebar-option" data-option="artistas">Artistas</div>
                <div class="sidebar-option" data-option="albumes">Albumes</div>
                <div class="sidebar-option" data-option="listas">Listas de Reproducción</div>
            </div>
               <div class="main-content">
            <h2 id="content-title"></h2>
            <div id="album-grid" class="album-grid mt-4"></div>
            <div id="album-details" class="mt-4" style="display: none;">
                <div class="row">
                    <div class="col-md-4">
                        <img id="album-cover" src="${pageContext.request.contextPath}/imagenes_album/fotoAlbum.jpg" alt="Foto Album" class="img-fluid">
                    </div>
                    <div class="col-md-8">
                        <h3 id="album-title"></h3>
                        <a href="" id="album-artist"></a>
                        <p id="album-genre"></p>
                        <button class="btn btn-primary me-2">Play</button>
                        <button class="btn btn-secondary">Download</button>
                        <button class="star-btn" id="album-star"><i class="fas fa-star"></i></button>
                        <div class="track-list mt-4">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Tema</th>
                                        <th>Duración</th>
                                    </tr>
                                </thead>
                                <tbody id="track-list-body"></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <div class="player">
                <img src="" alt="Album cover">
                <div class="controls">
                    <button id="prevBtn">⏮</button>
                    <button id="playBtn">▶</button>
                    <button id="nextBtn">⏭</button>
                </div>
                <input type="range" class="volume" min="0" max="100" value="50">
            </div>
        </div>

        <script>
            const playBtn = document.getElementById('playBtn');
            const contentTitle = document.getElementById('content-title');
            const sidebarOptions = document.querySelectorAll('.sidebar-option');
            let isPlaying = false;
            let albumSelected;
            let listaSelected;
            let generoSelected = "";
            let artistaSelected = "";
             <% 
             iSistema sys = new Fabrica().getSistema();
             ObjectMapper mapper = new ObjectMapper();
             mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
             List<DataAlbum> albums = sys.getAllAlbums();
             DataGenero[] generos = sys.getGeneros2(); 
             DataUsuario[] artistas = sys.getArtistas(); 
             Vector<DataLista> listaPartPublicas = sys.traerListasParticularesPublicas();
             List<DataLista> listaDefecto = sys.traerListasPorDefecto();
             String albumsJson = mapper.writeValueAsString(albums);
             String generosJson = mapper.writeValueAsString(generos);
             String artistasJson = mapper.writeValueAsString(artistas);
             String listaPartPublicasJson = mapper.writeValueAsString(listaPartPublicas);
             String listaDefectoJson = mapper.writeValueAsString(listaDefecto);
             %>
            const artistas = <%= artistasJson %>;
            const generos = <%= generosJson %>;
            const albums = <%= albumsJson %>;
            let albumsGen = [];
            let albumsArt = [];
            let tracks = [];
            let listaPartPublicas = <%= listaPartPublicasJson %>;
            let listaDefecto = <%= listaDefectoJson %>;

            playBtn.addEventListener('click', () => {
                if (isPlaying) {
                    playBtn.textContent = '▶';
                    isPlaying = false;
                } else {
                    playBtn.textContent = '⏸';
                    isPlaying = true;
                }
            });

        sidebarOptions.forEach(option => {
        option.addEventListener('click', () => {
            const selectedOption = option.dataset.option;
            
            if (selectedOption === 'generos' || selectedOption === 'artistas' || selectedOption === 'listas') {
                displayExtra(selectedOption); 
            } else if (selectedOption === 'albumes') {
                displayAlbums(albums); 
            }
            document.getElementById("searchBar").value = "";
            document.getElementById('album-details').style.display = 'none';
            document.getElementById('album-grid').style.display = 'grid';
            document.getElementById('content-title').textContent = option.textContent;
            
            });
        });
        
        function getAlbumsGen(){

        let value = generoSelected;
        checkType = 'Genero';
        $.ajax({
                url: 'SvGetAlbumBy',
                method: 'POST',
                data: { action: checkType, value: value },
                success: function(response) {

                        albumsGen = JSON.parse(response);
                        displayAlbums(albumsGen);
                },
                error: function() {
                   alert("Error");
                }
            });
        }
        
        function getAlbumsArt(){

        let value = artistaSelected;
        checkType = 'Artista';
        $.ajax({
                url: 'SvGetAlbumBy',
                method: 'POST',
                data: { action: checkType, value: value },
                success: function(response) {

                    albumsArt = JSON.parse(response);
                    displayAlbums(albumsArt);
                    
                },
                error: function() {
                   alert("Error");
                }
            });
        }
        
        function displaySearchBar(){
            const albumGrid = document.getElementById('album-grid');
            albumGrid.innerHTML = '';
            document.getElementById('content-title').textContent = "";
            //ARTISTAS
            if(artistasAjax.length !== 0){
                const titleNameArt = document.createElement('h3');
                titleNameArt.className = 'tituloSearch';
                titleNameArt.textContent = "Artistas";
                albumGrid.appendChild(titleNameArt);
            }
            artistasAjax.forEach(artista => {
                const artistaCard = document.createElement('div');
                artistaCard.className = 'artista-card';

                if(artista.imagen !== null ){
                const imgElement = document.createElement('img');

                imgElement.src = "${pageContext.request.contextPath}/imagenes_perfil/" + artista.imagen; 
                imgElement.className = "albumImg";
                imgElement.alt = artista.nombre;
                artistaCard.appendChild(imgElement);  
                }else{
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/assets/user.svg";
                    imgElement.className = "albumImg";
                    artistaCard.appendChild(imgElement);  
                }

                const titleElementArt = document.createElement('h5');
                titleElementArt.textContent = artista.nick;
                artistaCard.appendChild(titleElementArt);


                artistaCard.addEventListener('click', function() {
                    
                    
                });
                albumGrid.appendChild(artistaCard);
            });
            
            //CLIENTES
            if(clientesAjax.length !== 0){
                const titleNameCli = document.createElement('h3');
                titleNameCli.className = 'tituloSearch';
                titleNameCli.textContent = "Clientes";
                albumGrid.appendChild(titleNameCli);
            }
            clientesAjax.forEach(cliente => {
                const clienteCard = document.createElement('div');
                clienteCard.className = 'cliente-card';

                if(cliente.imagen !== null) {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_perfil/" + cliente.imagen; 
                    imgElement.className = "albumImg";
                    imgElement.alt = cliente.nombre;
                    clienteCard.appendChild(imgElement);  
                } else {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/assets/user.svg";
                    imgElement.className = "albumImg";
                    clienteCard.appendChild(imgElement);  
                }

                const titleElementCliente = document.createElement('h5');
                titleElementCliente.textContent = cliente.nick;
                clienteCard.appendChild(titleElementCliente);

                clienteCard.addEventListener('click', function() {
                    
                });
                albumGrid.appendChild(clienteCard);
            });

            //LISTAS PARTICULARES
            if(listasPAjax.length !== 0){
                const titleNameLP = document.createElement('h3');
                titleNameLP.className = 'tituloSearch';
                titleNameLP.textContent = "Listas Particulares";
                albumGrid.appendChild(titleNameLP);
            }
            listasPAjax.forEach(listaP => {
                const listaPCard = document.createElement('div');
                listaPCard.className = 'listaP-card';

                if(listaP.imagen !== null && listaP.imagen !== "-") {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/" + listaP.imagen; 
                    imgElement.className = "albumImg";
                    imgElement.alt = listaP.nombre;
                    listaPCard.appendChild(imgElement);  
                } else {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/lista.png";
                    imgElement.className = "albumImg";
                    listaPCard.appendChild(imgElement);  
                }

                const titleElementListaP = document.createElement('h5');
                titleElementListaP.textContent = listaP.nombre;
                listaPCard.appendChild(titleElementListaP);

                listaPCard.addEventListener('click', function() {
                    listaSelected = listaP;
                    displayListaDetails(listaSelected);
                });
                albumGrid.appendChild(listaPCard);
            });

            // LISTAS DEFECTO
            if(listasDAjax.length !== 0){
                const titleNameLD = document.createElement('h3');
                titleNameLD.className = 'tituloSearch';
                titleNameLD.textContent = "Listas Por Defecto";
                albumGrid.appendChild(titleNameLD);
            }
            listasDAjax.forEach(listaD => {
                const listaDCard = document.createElement('div');
                listaDCard.className = 'listaD-card';

                if(listaD.imagen !== null && listaD.imagen !== "-") {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/" + listaD.imagen; 
                    imgElement.className = "albumImg";
                    imgElement.alt = listaD.nombre;
                    listaDCard.appendChild(imgElement);  
                } else {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/lista.png";
                    imgElement.className = "albumImg";
                    listaDCard.appendChild(imgElement);  
                }

                const titleElementListaD = document.createElement('h5');
                titleElementListaD.textContent = listaD.nombre;
                listaDCard.appendChild(titleElementListaD);

                listaDCard.addEventListener('click', function() {
                    listaSelected = listaD;
                    displayListaDetails(listaSelected);
                });
                albumGrid.appendChild(listaDCard);
            });

            // TEMAS
            if(temasAjax.length !== 0){
            const titleNameTemas = document.createElement('h3');
            titleNameTemas.className = 'tituloSearch';
            titleNameTemas.textContent = "Temas";
            albumGrid.appendChild(titleNameTemas);
            }
            temasAjax.forEach(tema => {
                const temaCard = document.createElement('div');
                temaCard.className = 'tema-card';
                const imgElement = document.createElement('img');
                imgElement.src = "${pageContext.request.contextPath}/imagenes_album/tema.jpg"; 
                imgElement.className = "albumImg";
                imgElement.alt = tema.nombre;
                temaCard.appendChild(imgElement);  
                const titleElementTema = document.createElement('h5');
                titleElementTema.textContent = tema.nombre;
                temaCard.appendChild(titleElementTema);

                temaCard.addEventListener('click', function() {
                    
                });
                albumGrid.appendChild(temaCard);
            });

            // ALBUMS
            if(albumsAjax.length !== 0){
                const titleNameAlbum = document.createElement('h3');
                titleNameAlbum.className = 'tituloSearch';
                titleNameAlbum.textContent = "Albums";
                albumGrid.appendChild(titleNameAlbum);
            }
            albumsAjax.forEach(album => {
                const albumCard = document.createElement('div');
                albumCard.className = 'album-card';

                if(album.imagenAlbum !== null) {
                    const imgElement = document.createElement('img');
                    imgElement.className = "albumImg";
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_album/" + album.imagenAlbum; 
                    imgElement.alt = album.nombre;
                    albumCard.appendChild(imgElement);  
                } else {
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_album/fotoAlbum.jpg";
                    imgElement.className = "albumImg";
                    albumCard.appendChild(imgElement);  
                }

                const titleElementAlbum = document.createElement('h5');
                titleElementAlbum.textContent = album.nombre;
                albumCard.appendChild(titleElementAlbum);

                // Crear y agregar el artista
                const artistElement = document.createElement('a');
                artistElement.textContent = album.nombreArt;
                albumCard.appendChild(artistElement);

                albumCard.addEventListener('click', function() {
                    albumSelected = album;
                    displayAlbumDetails(albumSelected);
                });

                albumGrid.appendChild(albumCard);
            });
        }
        
        function displayExtra(selectedOption) {
            const albumGrid = document.getElementById('album-grid');
            albumGrid.innerHTML = '';
            let titulo = document.getElementById('content-title');
            if (selectedOption === 'generos'){
                generos.forEach(genero => {
                    const generoCard = document.createElement('div');
                    generoCard.className = 'genero-card';
                    const titleElement = document.createElement('h5');
                    titleElement.textContent = genero.nombre;
                    generoCard.appendChild(titleElement);


                    generoCard.addEventListener('click', function() {
                        generoSelected = genero.nombre;
                        getAlbumsGen(); 
                    });
                    albumGrid.appendChild(generoCard);
                });
            }else if (selectedOption === 'artistas'){
                    artistas.forEach(artista => {
                    const artistaCard = document.createElement('div');
                    artistaCard.className = 'artista-card';

                    if(artista.imagen !== null ){
                    const imgElement = document.createElement('img');
                    
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_perfil/" + artista.imagen; 
                    imgElement.className = "albumImg";
                    imgElement.alt = artista.nombre;
                    artistaCard.appendChild(imgElement);  
                    }else{
                        const imgElement = document.createElement('img');
                        imgElement.src = "${pageContext.request.contextPath}/assets/user.svg";
                        imgElement.className = "albumImg";
                        artistaCard.appendChild(imgElement);  
                    }

                    const titleElementArt = document.createElement('h5');
                    titleElementArt.textContent = artista.nick;
                    artistaCard.appendChild(titleElementArt);


                    artistaCard.addEventListener('click', function() {
                        artistaSelected = artista.nick;
                        getAlbumsArt(); 
                    });
                    albumGrid.appendChild(artistaCard);
                });
            }else if(selectedOption === 'listas'){
                if(listaDefecto.length !== 0){
                    const titleNameAlbum = document.createElement('h3');
                    titleNameAlbum.className = 'tituloSearch';
                    titleNameAlbum.textContent = "Listas Por Defecto";
                    albumGrid.appendChild(titleNameAlbum);
                }
                listaDefecto.forEach(listaD => {
                    const listaDCard = document.createElement('div');
                    listaDCard.className = 'listaD-card';

                    if(listaD.imagen !== null && listaD.imagen !== "-") {
                        const imgElement = document.createElement('img');
                        imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/" + listaD.imagen; 
                        imgElement.className = "albumImg";
                        imgElement.alt = listaD.nombre;
                        listaDCard.appendChild(imgElement);  
                    } else {
                        const imgElement = document.createElement('img');
                        imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/lista.png";
                        imgElement.className = "albumImg";
                        listaDCard.appendChild(imgElement);  
                    }

                    const titleElementListaD = document.createElement('h5');
                    titleElementListaD.textContent = listaD.nombre;
                    listaDCard.appendChild(titleElementListaD);

                    listaDCard.addEventListener('click', function() {
                        listaSelected = listaD;
                        displayListaDetails(listaSelected);
                    });
                    albumGrid.appendChild(listaDCard);
                });

                if(listaPartPublicas.length !== 0){
                    const titleNameAlbum = document.createElement('h3');
                    titleNameAlbum.className = 'tituloSearch';
                    titleNameAlbum.textContent = "Listas Particulares";
                    albumGrid.appendChild(titleNameAlbum);
                }

                listaPartPublicas.forEach(listaP => {
                    const listaPCard = document.createElement('div');
                    listaPCard.className = 'listaD-card';

                    if(listaP.imagen !== null && listaP.imagen !== "-") {
                        const imgElement = document.createElement('img');
                        imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/" + listaP.imagen; 
                        imgElement.className = "albumImg";
                        imgElement.alt = listaP.nombre;
                        listaPCard.appendChild(imgElement);  
                    } else {
                        const imgElement = document.createElement('img');
                        imgElement.src = "${pageContext.request.contextPath}/imagenes_lista/lista.png";
                        imgElement.className = "albumImg";
                        listaPCard.appendChild(imgElement);  
                    }

                    const titleElementListaP = document.createElement('h5');  
                    titleElementListaP.textContent = listaP.nombre;
                    listaPCard.appendChild(titleElementListaP);

                    listaPCard.addEventListener('click', function() {
                        listaSelected = listaP;
                        displayListaDetails(listaSelected);
                    });
                    albumGrid.appendChild(listaPCard);
                });
            }
            }

        function displayAlbums(dtList) {
            const albumGrid = document.getElementById('album-grid');
            albumGrid.innerHTML = '';
            if(dtList !== null){
            dtList.forEach(album => {
                const albumCard = document.createElement('div');
                console.log(album);
                console.log(album.imagenAlbum);
                albumCard.className = 'album-card';
                if(album.imagenAlbum !== null ){
                    const imgElement = document.createElement('img');
                    imgElement.className = "albumImg";
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_album/" + album.imagenAlbum; 
                    imgElement.alt = album.nombre;
                    albumCard.appendChild(imgElement);  
                }else{
                    const imgElement = document.createElement('img');
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_album/fotoAlbum.jpg";
                    imgElement.className = "albumImg";
                    albumCard.appendChild(imgElement);  
                }
                const titleElement = document.createElement('h5');
                titleElement.textContent = album.nombre;
                albumCard.appendChild(titleElement);

                // Crear y agregar el artista
                const artistElement = document.createElement('a');
                artistElement.textContent = album.nombreArt;
                albumCard.appendChild(artistElement);


                albumCard.addEventListener('click', function() {
                    albumSelected = album;
                    displayAlbumDetails(albumSelected);
                    
                });
                albumGrid.appendChild(albumCard);
            });
            }
        }
        

        function displayAlbumDetails(album) {
            document.getElementById('album-grid').style.display = 'none';
            document.getElementById('album-details').style.display = 'block';
            if(album.imagenAlbum !== null){
                document.getElementById('album-cover').src = "${pageContext.request.contextPath}/imagenes_album/" + album.imagenAlbum; 
            }else{
                document.getElementById('album-cover').src = "${pageContext.request.contextPath}/imagenes_album/fotoAlbum.jpg"; 
            }
            document.getElementById('album-title').textContent = album.nombre;
            document.getElementById('album-artist').textContent = album.nombreArt;
            document.getElementById('album-genre').textContent = album.Generos;
            tracks = album.temas;
            const trackListBody = document.getElementById('track-list-body');
            trackListBody.innerHTML = '';
            tracks.forEach((track) => {
                const row = trackListBody.insertRow();
                row.className = "trackRow";
                row.insertCell(0).textContent = track.posicion;
                row.insertCell(1).textContent = track.nombre;
                row.insertCell(2).textContent = track.duracion;
                
                const buttonCell = row.insertCell(3);

                const button = document.createElement("button");
                button.className = "btnFav"; 
                button.textContent = "☆"; 

                const buttons = document.getElementsByClassName("btnFav");

                for (let button of buttons) {
                    button.addEventListener("click", () => {
                        $(this).toggleClass('active');
                    });
                }
                buttonCell.appendChild(button);
            });
        }

        function displayListaDetails(lista) {
            document.getElementById('album-grid').style.display = 'none';
            document.getElementById('album-details').style.display = 'block';
            if(lista.imagenAlbum !== null && lista.imagen !== "-"){
                document.getElementById('album-cover').src = "${pageContext.request.contextPath}/imagenes_lista/" + lista.imagen; 
            }else{
                document.getElementById('album-cover').src = "${pageContext.request.contextPath}/imagenes_lista/lista.png"; 
            }
            document.getElementById('album-title').textContent = lista.nombre;
            document.getElementById('album-artist').textContent = lista.cliente;
            document.getElementById('album-genre').textContent = lista.Generos;
            tracks = lista.temas;
            const trackListBody = document.getElementById('track-list-body');
            trackListBody.innerHTML = '';
            tracks.forEach((track) => {
                const row = trackListBody.insertRow();
                row.className = "trackRow";
                row.insertCell(0).textContent = track.posicion;
                row.insertCell(1).textContent = track.nombre;
                row.insertCell(2).textContent = track.duracion;
                
                const buttonCell = row.insertCell(3);

                const button = document.createElement("button");
                button.className = "btnFav"; 
                button.textContent = "☆"; 

                const buttons = document.getElementsByClassName("btnFav");

                for (let button of buttons) {
                    button.addEventListener("click", () => {
                        $(this).toggleClass('active');
                    });
                }
                buttonCell.appendChild(button);
            });
        }
//        document.addEventListener('DOMContentLoaded', () => {
//            displayAlbums();
//
//            const sidebarLinks = document.querySelectorAll('.sidebar .list-group-item');
//            sidebarLinks.forEach(link => {
//                link.addEventListener('click', (e) => {
//                    document.getElementById('content-title').textContent = e.target.textContent;
//                    document.getElementById('album-grid').style.display = 'grid';
//                    document.getElementById('album-details').style.display = 'none';
//                    document.getElementById('searchBar').textContent = "";
//                });
//            });
//        });
        </script>
    </body>
</html>

<style>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
body {
    font-family: Arial, sans-serif;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

.tituloSearch {
    grid-column: 1 / -1; 

    margin: 0; 
    padding: 0.5em 0;
    font-size: 1.5em;
}

.trackRow {
    transition: background-color 0.3s ease;
}

table.table tbody tr:hover {
    background-color: transparent; /* O el color de fondo predeterminado */
}

table.table tbody tr.trackRow:hover {
    background-color: #1ed760;
}
.albumImg {
    height: 150px;
    width: 150px;
    object-fit: cover;
}

.album-cover {
    height: 250px;
    width: 250px;
    object-fit: cover;
}

.btnFav{
    cursor: pointer;
    color: #000;
    transition: color 0.3s ease;
    background: none;
    border: none;
    font-size: 1.2em;
}

.btnFav.active {
    color: #ffc107;
}

.content-wrapper {
    display: flex;
    flex-grow: 1;
}
.sidebar {
    width: 200px;
    background-color: #f0f0f0;
    padding: 20px;
}
.sidebar-option {
    cursor: pointer;
    padding: 10px;
    margin-bottom: 10px;
}
.sidebar-option:hover {
    background-color: #e0e0e0;
}
.main-content {
    flex-grow: 1;
    padding: 20px;
    display: flex;
    flex-direction: column;
}
.content-title {
    font-size: 24px;
    margin-bottom: 20px;
}
.genres-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}
.genre {
    background-color: #e0e0e0;
    padding: 20px;
    text-align: center;
    cursor: pointer;
}
.player {
    width: 200px;
    background-color: #f0f0f0;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.controls {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}
.controls button {
    margin: 0 10px;
    font-size: 24px;
    background: none;
    border: none;
    cursor: pointer;
}
.volume {
    width: 100%;
    margin-top: 20px;
}
  
.album-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    gap: 20px;
}
.album-card,
.genero-card,
.artista-card,
.tema-card,
.listaP-card,
.listaD-card,
.cliente-card{
    cursor: pointer;
    transition: transform 0.3s;
    border: 1px solid #ccc;
    padding: 10px;
    background-color: #fff;
    text-align: center;
}
.album-card:hover {
    transform: scale(1.05);
}
.track-list {
    max-height: 300px;
    overflow-y: auto;
}
</style>