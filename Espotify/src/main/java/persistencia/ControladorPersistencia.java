/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import datatypes.DataAlbum;
import datatypes.DataUsuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Album;
import logica.Artista;
import logica.Cliente;
import logica.Genero;
import logica.ListaParticular;
import logica.ListaPorDefecto;
import logica.ListaReproduccion;
import logica.Tema;
import logica.Usuario;

public class ControladorPersistencia implements iControladorPersistencia {
    
    //Sgltn
    static ControladorPersistencia instancia = null;
    
    public static ControladorPersistencia getInstance(){
        if(instancia == null){
            instancia = new ControladorPersistencia();
        }
        return instancia;
    }
    //
    
    UsuarioJpaController usuarioController;
    ClienteJpaController clienteController;
    ArtistaJpaController artistaController;
    AlbumJpaController albumController;
    TemaJpaController temaController;
    GeneroJpaController generoController;
    ListaReproduccionJpaController listaController;
    ListaParticularJpaController listaParticularController;
    ListaReproduccionJpaController listaReproduccionController;
    ListaPorDefectoJpaController listaporDefectoController;
    
    public ControladorPersistencia() {
        usuarioController = new UsuarioJpaController();
        clienteController = new ClienteJpaController();
        artistaController = new ArtistaJpaController();
        albumController = new AlbumJpaController();
        temaController = new TemaJpaController();
        generoController = new GeneroJpaController();
        listaController = new ListaReproduccionJpaController();
        listaParticularController = new ListaParticularJpaController();
        listaReproduccionController = new ListaReproduccionJpaController();
        listaporDefectoController = new ListaPorDefectoJpaController();
    }

    // persistencia de usuarios
    public void crearCliente(Cliente cliente){
        clienteController.create(cliente);
    }
    
    public void crearUsuario(Usuario usuario) {
        if (usuario instanceof Cliente) {
            clienteController.create((Cliente) usuario);
        } else{
            artistaController.create((Artista) usuario);
        }
    }

    public Usuario obtenerUsuario(String nickname) {
        return usuarioController.findUsuarioByNickname(nickname);
    }
    
    public Usuario obtenerUsuarioMail(String email) {
        return usuarioController.findUsuarioByEmail(email);
    }

    public void actualizarUsuario(Usuario usuario) {
        try {
            if (usuario instanceof Cliente) {
                clienteController.edit((Cliente) usuario);
            } else{
                artistaController.edit((Artista) usuario);
            }       
        } catch (Exception e) {
            e.printStackTrace(); // excepcion
        }
    }

    public void eliminarUsuario(String email) {
        try {
            Usuario usuario = usuarioController.findUsuario(email);
            if (usuario instanceof Cliente) {
                clienteController.destroy(email);
            } else {
                artistaController.destroy(email);
            }
        } catch (Exception e) {
            e.printStackTrace(); // excepcion
        }
    }
    
    //mostrar usuario cliente

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteController.findAllClientes();
    }
    public List<Artista> obtenerTodosLosArtistas() {
        return artistaController.findAllArtistas();
    }
   public List<Usuario> obtenerTodosLosUsuarios(){
        return usuarioController.findAllUsers();
    }

   public boolean ExisteMail(String email){
       return usuarioController.existeEmail(email);
   }
    
    

//    public void cerrar() {
//        if (emf != null && emf.isOpen()) {
//            emf.close();
//        }
//    }

    
    public List<Usuario> getUsuarios(){
        
        List<Usuario> usrs = usuarioController.findUsuarioEntities();
        
        if(usrs.isEmpty()){
            return null;
        }else{
            return usrs;
        }
    
    }
     //Persistencia Album
public void crearAlbum(Album album) {
        albumController.create(album);
    }

    public Album obtenerAlbum(String nombreA) {
        return albumController.findAlbum(nombreA);
    }

    public void actualizarAlbum(Album album) {
        try {
            albumController.edit(album);
        } catch (Exception e) { 
        }
    }

    public void eliminarAlbum(String nombreA) {
        try {
            Album album = albumController.findAlbum(nombreA);
            if (album != null){
                albumController.destroy(nombreA);
            }
        } catch (Exception e) {
            e.printStackTrace(); // excepcion
        }
    }
    
    public Collection<Album> getAlbumesArt(Artista art){

        Collection <Album> albumes = albumController.findAlbumEntities(art);
        return albumes;
    }
    
    public Collection<Album> getAlbumesGen(String nombreGenero){
        Collection <Album> albumes = albumController.obtenerNombresAlbumesPorGenero(nombreGenero);
        return albumes;
    }
    
    public Album getAlbumArt(String nombreArtista, String nombreA){
        Artista ar = (Artista) obtenerUsuario(nombreArtista);
        Album album = artistaController.getAlbum(nombreA, ar);
        return album;
    }
    
    public Album getAlbumGen(String nombreGenero, String nombreA){
        Genero gen = obtenerGenero(nombreGenero);
        Album album = generoController.getAlbum(nombreA, gen);
        return album;
    }
    
    public List<DataAlbum> getAllAlbumes(){
        List<Album> albumes = albumController.findAllAlbumes();
        List<DataAlbum> dtAlbumes = new ArrayList<>();
        for (Album alb : albumes){
            dtAlbumes.add(alb.getDataAlbum());
        }
        return dtAlbumes;
    }
    
    public void detachAlbum(Album album){
        albumController.detach(album);
    }
    
        //Persistencia Tema
    public void crearTema(Tema tema) {
        temaController.create(tema);
    }

    public Tema obtenerTema(String nombreT) {
        return temaController.findTema(nombreT);
    }

    public void actualizarTema(Tema tema) {
        try {
            temaController.edit(tema);
        } catch (Exception e) { 
        }
    }

    public void eliminarTema(String nombreT) {
        try {
            Tema tema = temaController.findTema(nombreT);
            if (tema != null){
                temaController.destroy(nombreT);
            }
        } catch (Exception e) {
            e.printStackTrace(); // excepcion
        }
    }
    
            //Persistencia Genero
    public void crearGenero(Genero gen) {
        generoController.create(gen);
    }

    public Genero obtenerGenero(String nombreG) {
        return generoController.findGenero(nombreG);
    }

    public void actualizarGenero(Genero gen) {
        try {
            generoController.edit(gen);
        } catch (Exception e) { 
        }
    }

    public List<Genero> getGeneros(){
        return generoController.findGeneroEntities();
    }
    
    public void eliminarGenero(String nombreG) {
        try {
            Genero gen = generoController.findGenero(nombreG);
            if (gen != null){
                generoController.destroy(nombreG);
            }
        } catch (Exception e) {
            e.printStackTrace(); // excepcion
        }
    }
    
    //Listas
    //crearListaPart
    public void crearListarPar(ListaParticular lr){
        listaController.createListaPar(lr);
    }
    
    public void crearListaDef(ListaPorDefecto lr){
        listaController.createListaDef(lr);
    }
    
    public List<ListaReproduccion> listaListas(){
        return listaController.findListaEntities();
    }
    
    public ListaReproduccion findLista(String nombre){
        return listaController.findLista(nombre);
    }

    public List<Usuario> traerUsuariosClientes(){
         return usuarioController.findUsuarioByTipoCliente();
     }
    
    public List<Genero> traerGeneros() {
        //return generoController.findGeneroEntities();
        return generoController.findPadres();
    }

    public List<ListaParticular> traerListasParticularesPorCliente(String nickname) {
        return listaParticularController.findAllListasParticularesCliente(nickname);
    }
    
    public ListaParticular traerListaParticularPorId(int id){
        return listaParticularController.findListaParticularById(id);
    }
    
    public void modificarListaParticular(ListaParticular lista) {
        try {
            listaParticularController.edit(lista);
            
            
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<ListaPorDefecto> traerListasPorDefectoPorGenero(String genero) {
        return listaporDefectoController.findAllListaDefectoGenero(genero);
    }
    
    public Cliente obtenerCliente(String nickname) {
        return clienteController.findClienteByNickname(nickname);
    }
    
    public Artista obtenerArtista(String nickname) {
        return artistaController.findArtistaByNickname(nickname);
    }

    public List<Tema> Listar_Temas() {
        return temaController.findTemaEntities();
    }

    public List<Album> Listar_Albumes() {
        return albumController.findAllAlbumes();
    }
    
    public List<ListaPorDefecto> Listar_Listas_Por_Defecto() {
        return listaporDefectoController.findListaPorDefectoEntities();
    }
    
    public List<ListaParticular> Listar_Listas_Particulares() {
        return listaParticularController.findListaParticularEntities();
    }
    
    //PROBANDO
    public List<Genero> traerTodosGeneros(){
       return generoController.findGeneroEntities();
   }
    
    public List<Tema> traerTemasPorIdAlbum(Long id) {
        return temaController.findTemasByAlbumId(id);
    }
    
    public void editarListaReproduccion(ListaReproduccion lr){
        try {
            listaController.edit(lr);
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Tema traerTemaPorId(Long id){
        return temaController.findTema(id);
    }
    
    public List<ListaParticular> traerListasParticularesPublicas(){
        return listaParticularController.findListasParticularesPublicas();
    }

    public Set<Tema> traerTemasPorIdListaReproduccion(int id) {
        return listaReproduccionController.findTemasByListaReproduccionId(id);
    }
    
    public ListaPorDefecto traerListaPorDefectoPorId(int id){
        return listaporDefectoController.findListaPorDefectoById(id);
    }
    
    
    
    public ListaReproduccion traerListaReproduccionPorId(int id){
        return listaReproduccionController.findListaById(id);
    }

    public void editarCliente(Cliente cliente) {
        try {
            clienteController.edit(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Genero> obtenerTodosLosGeneros(){
        return generoController.findAllGeneros();
    }

    public List<Album> obtenerAlbumPorGenero(String nombreGenero){
        return albumController.obtenerNombresAlbumesPorGenero(nombreGenero);   
    }
    
    //Consulta album
    public List<Album> obtenerAlbumPorArtista(String nombreArtista){
        return albumController.obtenerNombresAlbumesPorArtista(nombreArtista);
    }
    
    public Album obtenerAlbumPorNombre(String nombreA) {
        return albumController.findAlbumNombre(nombreA);
    }
    
    public Tema obtenerTemaPorNombre(String nombreA) {
        return temaController.findTemaNombre(nombreA);
    }
    
    public ListaReproduccion obtenerListaPorNombre(String nombreL) {
        return listaController.findListaReproduccionByName(nombreL);
    }
    
    public List<ListaReproduccion> getListasAjax(String s){
        return listaController.findAllListasAjax(s);
    }
    
    public List<Tema> getTemasAjax(String s){
        return temaController.findAllTemasAjax(s);
    }
    
    public List<Album> getAlbumsAjax(String s){
        return albumController.findAllAlbumesAjax(s);
    }
    
    public List<Usuario> getUsuariosAjax(String s){
        return usuarioController.findAllUsuariosAjax(s);
    }
   
}