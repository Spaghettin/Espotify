/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controladores;

import datatypes.DataUsuario;
import datatypes.DataLista;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;
import excepciones.ListaRepetidaException;
import excepciones.GeneroRepetidoException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import logica.Usuario;
import logica.Album;
import logica.Genero;
import datatypes.DataAlbum;
import datatypes.DataGenero;
import datatypes.DataTema;
import excepciones.AlbumNoExisteException;
import excepciones.GeneroNoExisteException;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import logica.Artista;
import logica.Cliente;
import logica.ListaParticular;
import logica.ListaPorDefecto;
import logica.ListaReproduccion;
import logica.Tema;


public interface iSistema {
    public abstract void cargarDatos() throws GeneroRepetidoException, UsuarioRepetidoException;
    
    //Alta Usuarios - Consulta Usuarios  - Preferencias - agustin
    public abstract void altaPerfil(String nickname, String nombre, String apellido, String password, String email, LocalDate fechaNac, String imagen, String sitioWeb, String biografia, String tipo) throws UsuarioRepetidoException;
    public abstract DataUsuario[] getDataUsuarios() throws UsuarioNoExisteException;
    public abstract DataUsuario[] getClientes() throws UsuarioNoExisteException;
    public abstract DataUsuario[] getArtistas() throws UsuarioNoExisteException;
    public abstract DataUsuario[] filtrarUsuariosPorTipo(Usuario[] usuarios, String tipo);
    public abstract boolean existeUsuario(String nickname);
    public abstract Usuario[] getUsuarios();
    public abstract Usuario obtenerUsuarioMail(String email);
    public abstract Usuario obtenerUsuario(String nick);
    public abstract Cliente ObtenerCliente(String nick);
    public abstract Artista ObtenerArtista(String nick);
    public abstract void addUsuario(Usuario user);
    //public abstract List<Cliente> getSeguidores(String nicknameArtista);
    public abstract List<DataUsuario> getSeguidores(String nicknameArtista);
    //public abstract Map<String, ArrayList<String>> UsuarioSeguidos(String cliente_seleccionado);
    public abstract Map<String, ArrayList<DataUsuario>> UsuarioSeguidos(String cliente_seleccionado);
    public abstract boolean existeMail(String email);
    public abstract DataAlbum[] getAlbumsByArtista(String artista) throws AlbumNoExisteException;
    public abstract Album getAlbum(String nombreA);
    public abstract Tema getTema(String nombreT);
    public abstract ListaReproduccion getLista(String nombreL);
    public abstract void AgregarListaFavoritaCliente(String nickname, ListaReproduccion lista);
    public abstract void AgregarAlbumFavoritoCliente(String nickname, Album album);
    public abstract void AgregarTemaFavoritoCliente(String nickname, Tema tema);
    public abstract DataAlbum getAlbumEspecificoGen2(String nombreA, String nombreGenero);
    public abstract DataAlbum getAlbumEspecificoArt2(String nombreA, String nombreArtista);
    
    
    //Album - michel
    //public abstract void actualizoAlbum();
    public abstract void altaGenero(String nombre);
    public abstract void addGenero(Genero genero);
    public abstract void addGeneroAlbum(String nombreGenero);
    public abstract void altaAlbum();
    public abstract Album getAlbumGenerico();
    public abstract Collection<DataAlbum> getAlbumesArt(String nombreArtista);
    public abstract Collection<DataAlbum> getAlbumesGen(String nombreGenero);
    //public abstract void desvinculaAlbum(String nombreA, String nombreArtista);
    public abstract boolean verificaAlbum(String nombreA, String nombreArtista);
    public abstract void cargarDatosAlbum(String nombre, String nombreArtista, Integer fechaCreacion, String imagen);
    public abstract void cargarGenerosSys();
    public abstract void altaTema(String nombreT, String duracion, Integer posicion, String direccionWeb, String archivo);
    public abstract HashSet<String> getGeneros();  
    public abstract Album getAlbumEspecificoArt(String nombreA, String nombreArtista);
    public abstract Album getAlbumEspecificoGen(String nombreA, String nombreGenero);
    public abstract List<DataAlbum> getAllAlbums();
    
    //genero - lista fran
    //Genero
    public abstract String getPadre(String nombreGenero); 
    public abstract Genero getGenero(String nombreGenero);
    
    public abstract boolean encontrarGenero(String nom);
    public abstract void altaGenero(String nom, String pad) throws GeneroRepetidoException;
    public abstract Set<String> listarGeneros();
    public abstract Set<String> listarGenerosSinPadre();
    //Listas
    public abstract boolean encontrarLista(String nom);
    public abstract void altaListaPart(DataLista dtl) throws ListaRepetidaException;
    public abstract void altaListaDef(DataLista dtl) throws ListaRepetidaException;
    public abstract Set<String> listarListas();
    public abstract Set<String> listarListasPar();
    public abstract Set<String> listarListasDef();
    public abstract DataLista darLista(String nom);
    
    public abstract List<DataLista> traerListasDefectoPorGenero(String genero);
    public abstract ListaParticular traerListaParticularPorId(int id);
    public abstract void modificarListaParticularPorId(int listaId);
    public abstract List<DataLista> traerListasParticularesPorClientes(String nickname);

    public abstract List<String> traerNickNamesClientes();
    public abstract DataLista traerListaDefGeneroNombre(String genero, String nombre);
    public abstract DataLista traerListaPartClienteNombre(String cliente, String nombre);

    
    
    //BRIAN
    public abstract ArrayList Nicknames_De_Clientes();
    public abstract ArrayList Listar_Usuarios_A_Seguir(String cliente_seleccionado);
    public abstract void Seguir_Usuario(String cliente_seleccionado, String nickname_usuario);
    public abstract ArrayList<String> Listar_Usuarios_A_DEJAR_DE_Seguir(String cliente_seleccionado);
    public abstract void Dejar_De_Seguir_Usuario(String cliente, String usuario);
    public abstract ArrayList<String> Listar_Temas_A_Seguir(String cliente_seleccionado);
    public abstract void Seguir_TEMA(String cliente_seleccionado, String tema_seleccionado);
    public abstract ArrayList<String> Listar_Albumes_A_Seguir(String cliente_seleccionado);
    public abstract void Seguir_ALBUM(String cliente_seleccionado,String album_seleccionado);
    public abstract ArrayList<String> Listar_Listas_A_Seguir(String cliente_seleccionado);
    public abstract void Seguir_LISTA(String cliente_seleccionado, String lista_seleccionada);
    
    //PROBANDO
    public abstract List<String> traerGeneros();
    public abstract List<String> traerArtistas();
    public abstract List<String> traerAlbumesGenero(String genero);
    public abstract List<String> traerAlbumesArtista(String artista);
    public abstract boolean existeTemaLista(Long idTema, int idLista);
    public abstract boolean esPublicaLista(int idLista);
    
    public abstract Vector<DataAlbum> traerAlbumes();
    public abstract List<DataTema> traerTemasPorIdAlbum(Long id);
    public abstract void agregarTemaLista(int idLista, Long idTema);
    public abstract Vector<DataLista> traerListasParticularesPublicas();
    public abstract List<DataTema> traerTemasPorIdListaReproduccion(int id);
    public abstract List<DataLista> traerListasPorDefecto();
    public abstract List<DataTema> traerTemasPorListasPorDefectoId(int listaid);
    public abstract List<DataLista> traerListasFavoritasPorIdCliente(String nickname);
    public abstract void eliminarListaFavoritaCliente(String nickname, int idLista);
    public abstract List<DataTema> traerTemasFavoritosPorIdCliente(String nickname);
    public abstract void eliminarTemasFavoritosCliente(String nickname, Long idTema);
    public abstract List<DataAlbum> traerAlbumesFavoritosPorIdCliente(String nickname);
    public abstract void eliminarAlbumesFavoritosCliente(String nickname, Long idAlbum);
    
    public abstract ListaParticular traerListaParticularPorCliente(String nombre, String nickname);
    public abstract ListaPorDefecto traerListaPorDefecto(String nombre);
    public abstract List<DataLista> getListasParticulares(String nickname);
    public abstract DataLista getListaDefecto(String nombre);
    public abstract List<Tema> getTemas(Album album);
    public abstract DataGenero[] getGeneros2() throws GeneroNoExisteException;
    public abstract DataAlbum[] getAlbumsByGenero(String genero) throws AlbumNoExisteException;
    public abstract void eliminarTemasListaPart(String nombre, String nombreL, Long idTema);
    public abstract void eliminarTemaListaDef(String nombre, Long idTema);
    
    public abstract boolean Encontrar_Lista_Por_Defecto(String nombre);
    public abstract boolean Encontrar_Lista_Particular(String nombre_cliente,String nombre_lista);
    
}


