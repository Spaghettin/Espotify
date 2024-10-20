<%@page import= "java.util.List"%>
<%@page import= "datatypes.DataTema"%>
<%@page import= "datatypes.DataGenero"%>
<%@page import= "datatypes.DataUsuario"%>
<%@page import= "datatypes.DataAlbum"%>
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
                        <button class="btnFav">☆</button>
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
            let generoSelected = "";
            let artistaSelected = "";
             <% 
             iSistema sys = new Fabrica().getSistema();
             ObjectMapper mapper = new ObjectMapper();
             mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
             List<DataAlbum> albums = sys.getAllAlbums();
             DataGenero[] generos = sys.getGeneros2(); 
             DataUsuario[] artistas = sys.getArtistas(); 
             String albumsJson = mapper.writeValueAsString(albums);
             String generosJson = mapper.writeValueAsString(generos);
             String artistasJson = mapper.writeValueAsString(artistas);
             %>
            const artistas = <%= artistasJson %>;
            const generos = <%= generosJson %>;
            const albums = <%= albumsJson %>;
            let albumsGen = [];
            let albumsArt = [];
            let tracks = [];

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
            
             if (selectedOption === 'generos' || selectedOption === 'artistas') {
                displayExtra(selectedOption); 
            } else if (selectedOption === 'albumes') {
                displayAlbums(albums); 
            }
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
                    imgElement.src = "${pageContext.request.contextPath}/imagenes_album/fotoAlbum.jpg"
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
            tracks.forEach((track, index) => {
                const row = trackListBody.insertRow();
                row.insertCell(0).textContent = track.id;
                row.insertCell(1).textContent = track.nombre;
                row.insertCell(2).textContent = track.duracion;
                const buttonCell = row.insertCell(3);

                const button = document.createElement("button");
                button.className = "btnFav"; // Asignar la clase
                button.textContent = "☆"; // Texto del botón

                const buttons = document.getElementsByClassName("btnFav");

                for (let button of buttons) {
                    button.addEventListener("click", () => {

                    });
}
                buttonCell.appendChild(button);
            });
        }

        document.addEventListener('DOMContentLoaded', () => {
            displayAlbums();

            const sidebarLinks = document.querySelectorAll('.sidebar .list-group-item');
            sidebarLinks.forEach(link => {
                link.addEventListener('click', (e) => {
                    document.getElementById('content-title').textContent = e.target.textContent;
                    document.getElementById('album-grid').style.display = 'grid';
                    document.getElementById('album-details').style.display = 'none';
                });
            });
        });
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
    border: none;         
    background: none;     
    padding: 0;           
    margin: 0;            
    cursor: pointer;     
    outline: none; 
    font-size: 35px;
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
.artista-card {
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