
package controladores;

import java.time.LocalDate;
import logica.Usuario;
import logica.Cliente;
import logica.Artista;
import logica.Genero;
import logica.Album;
import logica.ListaParticular;
import logica.ListaPorDefecto;
import logica.ListaReproduccion;
import excepciones.UsuarioRepetidoException;
import excepciones.UsuarioNoExisteException;
import excepciones.ListaRepetidaException;
import excepciones.GeneroRepetidoException;
import datatypes.DataUsuario;
import datatypes.DataAlbum;
import datatypes.DataGenero;
import datatypes.DataLista;
import datatypes.DataTema;
import excepciones.AlbumNoExisteException;
import excepciones.GeneroNoExisteException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import logica.Tema;
import persistencia.ControladorPersistencia;

public class Sistema implements iSistema{
    
    /*private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNac;
    private String imagenPerfil;
    private String tipo;*/
    
    //album
    private Map<String, Genero> Generos = new HashMap<>();
    private Album albumGenerico = new Album();
    //
    
    static Sistema instancia = null;
    
    public static Sistema getInstance(){
        if(instancia == null){
            instancia = new Sistema();
        }
        return instancia;
    }
    
    //iControladorPersistencia cpu = new Fabrica().getControladorPersistencia();
    ControladorPersistencia cpu = new ControladorPersistencia();
    
    public static final String CLIENTE = "cliente";
    public static final String ARTISTA = "artista";
    
    private Map<String, Usuario> usuariosNick = new HashMap<>();
    
    //Registrar usuario
        public void altaPerfil(String nickname, String nombre, String apellido, String password, String email, LocalDate fechaNac, String imagen, String biografia, String sitioWeb, String tipo) throws UsuarioRepetidoException{
            Usuario u = obtenerUsuario(email);
            
            if(u != null){
                throw new UsuarioRepetidoException("El usuario con el nickname" + nickname + " ya esta registrado");
            }else{
                if(CLIENTE.equals(tipo)){
                Cliente nuevoUsuario = new Cliente(nickname, nombre, apellido, password, email, fechaNac, imagen,tipo);
                //addUsuario(nuevoUsuario);
                cpu.crearCliente(nuevoUsuario);
                }else if(ARTISTA.equals(tipo)){
                    Artista nuevoArtista = new Artista(nickname, nombre, apellido, password, email, fechaNac, imagen, biografia, sitioWeb, tipo);
                    //addUsuario(nuevoArtista);
                    cpu.crearUsuario(nuevoArtista);
                }else{
                    throw new IllegalArgumentException("Tipo de usuario no reconocido: " + tipo);
                }
            }
        }
        
        public DataUsuario[] getDataUsuarios() throws UsuarioNoExisteException{
            
            Usuario[] usrs = getUsuarios();
            if (usrs != null) {
                DataUsuario[] du = new DataUsuario[usrs.length];
                Usuario usuario;

                // Para separar lógica de presentación, no se deben devolver los Usuario,
                // sino los DataUsuario
                for (int i = 0; i < usrs.length; i++) {
                    usuario = usrs[i];
                    du[i] = usuario.devolverData();
                }
                return du;
            } else {
                throw new UsuarioNoExisteException("No existen usuarios registrados");
            }
        }
        
        //getclientes con persistencia
    public DataUsuario[] getClientes() throws UsuarioNoExisteException {
    List<Cliente> clientes = cpu.obtenerTodosLosClientes();
    if (!clientes.isEmpty()) {
        DataUsuario[] dataClientes = new DataUsuario[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            dataClientes[i] = clientes.get(i).devolverData();
        }
        return dataClientes;
    } else {
        throw new UsuarioNoExisteException("No existen clientes registrados");
        }
    }

//    public DataUsuario[] getArtistas() throws UsuarioNoExisteException {
//        Usuario[] usrs = getUsuarios();
//        if (usrs != null) {
//            return filtrarUsuariosPorTipo(usrs, ARTISTA);
//        } else {
//            throw new UsuarioNoExisteException("No existen usuarios registrados");
//        }
//    }
    
    //getartistas con persistencia
    public DataUsuario[] getArtistas() throws UsuarioNoExisteException {
    List<Artista> artistas = cpu.obtenerTodosLosArtistas();
    if (!artistas.isEmpty()) {
        DataUsuario[] dataArtistas = new DataUsuario[artistas.size()];
        for (int i = 0; i < artistas.size(); i++) {
            dataArtistas[i] = artistas.get(i).devolverData();
        }
        return dataArtistas;
    } else {
        throw new UsuarioNoExisteException("No existen artistas registrados");
        }
    }
    
    

    public DataUsuario[] filtrarUsuariosPorTipo(Usuario[] usuarios, String tipo) {
        return Arrays.stream(usuarios)
                     .filter(usuario -> tipo.equals(usuario.getTipo()))
                     .map(Usuario::devolverData)
                     .toArray(DataUsuario[]::new);
    }
    
    
//    public boolean existeUsuario(String nickname){
//        Usuario usuario = obtenerUsuario(nickname);
//        return usuario != null;
//    }
    
    public boolean existeUsuario(String nickname){
        Usuario usuario = cpu.obtenerUsuario(nickname);
        return usuario != null;
    }
    
    public boolean existeMail(String email) {
        return cpu.ExisteMail(email);
    }
    
    
    public void addUsuario(Usuario user){
        String nick = user.getNickname();
        usuariosNick.put(nick, user);
    }

//    public Usuario obtenerUsuario(String nick){
//        return ((Usuario) usuariosNick.get(nick));
//    }
    
    public Usuario obtenerUsuario(String nick){
        return ((Usuario) cpu.obtenerUsuario(nick));
    }
    
    public Usuario obtenerUsuarioMail(String email){
        return ((Usuario) cpu.obtenerUsuarioMail(email));
    }
    
    public Cliente ObtenerCliente(String nick){
        return ((Cliente) cpu.obtenerCliente(nick));
        
    }
    
    public Artista ObtenerArtista(String nick){
        return ((Artista) cpu.obtenerArtista(nick));
    }
    
    
//    public Usuario[] getUsuarios(){
//        if(usuariosNick.isEmpty()){
//            return null;
//        }else{
//            Collection<Usuario> usrs = usuariosNick.values();
//            Object[] o = usrs.toArray();
//            Usuario[] usuarios = new Usuario[o.length];
//            for (int i = 0; i < o.length; i++){
//                usuarios[i] = (Usuario) o[i];
//            }
//            return usuarios;
//        }
//    }
    
    
    
    //Modificado para mostrar en sql //////////////////////////
    public Usuario[] getUsuarios(){
        if(cpu.getUsuarios().isEmpty()){
            return null;
        }else{
            List<Usuario> usrs = cpu.getUsuarios();
            Object[] o = usrs.toArray();
            Usuario[] usuarios = new Usuario[o.length];
            for (int i = 0; i < o.length; i++){
                usuarios[i] = (Usuario) o[i];
            }
            return usuarios;
        }
    }
    
    //Consultar perfil cliente/artista
//    public List<Cliente> getSeguidores(String nicknameArtista){
//        List<Cliente> seguidores = new ArrayList<>();
//        
//        List<Usuario> todosLosUsuarios = cpu.obtenerTodosLosUsuarios();
//    
//        for (Usuario usuario : todosLosUsuarios) {
//            if (usuario instanceof Cliente) {
//                Cliente cliente = (Cliente) usuario;
//                if (cliente.getUsuariosSiguiendo().stream()
//                        .anyMatch(usuarioSeguido -> usuarioSeguido.getNickname().equals(nicknameArtista))) {
//                    seguidores.add(cliente);
//                }
//            }
//        }
//    
//        return seguidores;
//    }
    
    public List<DataUsuario> getSeguidores(String nicknameArtista) {
    List<DataUsuario> seguidores = new ArrayList<>();
    
    // Obtener todos los usuarios como entidades originales
    List<Usuario> todosLosUsuarios = cpu.obtenerTodosLosUsuarios();

    for (Usuario usuario : todosLosUsuarios) {
        // Verificar si el Usuario es un Cliente
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;

            // Verificar si el cliente sigue al artista con el nickname especificado
            if (cliente.getUsuariosSiguiendo().stream()
                    .anyMatch(artistaSeguido -> artistaSeguido.getNickname().equals(nicknameArtista))) {
                // Convertir Cliente en DataUsuario y agregarlo a la lista
                DataUsuario dataUsuario = new DataUsuario(
                        cliente.getNickname(),
                        cliente.getNombre(),
                        cliente.getApellido(),
                        cliente.getEmail(),
                        cliente.getFechaNac(),
                        cliente.getImagenPerfil()
                );
                seguidores.add(dataUsuario);
            }
        }
    }

    return seguidores;
}

    
//    public List<DataUsuario> getSeguidores2(String nicknameArtista) {
//    List<DataUsuario> seguidores = new ArrayList<>();
//
//    List<DataUsuario> todosLosUsuarios = cpu.obtenerTodosLosDataUsuarios();
//
//    for (DataUsuario dataUsuario : todosLosUsuarios) {
//        if (dataUsuario instanceof DataCliente) {
//            DataCliente dataCliente = (DataCliente) dataUsuario;
//            if (dataCliente.getUsuariosSiguiendo().stream()
//                    .anyMatch(usuarioSeguido -> usuarioSeguido.getNickname().equals(nicknameArtista))) {
//                seguidores.add(dataCliente);
//            }
//        }
//    }
//
//    return seguidores;
//}
    
//    public Map<String, ArrayList<String>> UsuarioSeguidos(String cliente_seleccionado){
//        Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
//        ArrayList<Usuario> usuarios = cliente_elegido.getUsuariosSiguiendo();
//
//        ArrayList<String> clientesSeguidos = new ArrayList<>();
//        ArrayList<String> artistasSeguidos = new ArrayList<>();
//
//        for (Usuario usuario : usuarios) {
//            if (usuario instanceof Cliente) {
//                clientesSeguidos.add(usuario.getNickname());
//            } else if (usuario instanceof Artista) {
//                artistasSeguidos.add(usuario.getNickname());
//            }
//        }
//
//        Map<String, ArrayList<String>> usuariosMap = new HashMap<>();
//        usuariosMap.put("clientes", clientesSeguidos);
//        usuariosMap.put("artistas", artistasSeguidos);
//
//        return usuariosMap;
//    }
    
    public Map<String, ArrayList<DataUsuario>> UsuarioSeguidos(String cliente_seleccionado) {
    Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
    ArrayList<Usuario> usuarios = cliente_elegido.getUsuariosSiguiendo();

    ArrayList<DataUsuario> clientesSeguidos = new ArrayList<>();
    ArrayList<DataUsuario> artistasSeguidos = new ArrayList<>();

    for (Usuario usuario : usuarios) {
        DataUsuario dataUsuario = new DataUsuario(
            usuario.getNickname(), 
            usuario.getNombre(), 
            usuario.getApellido(), 
            usuario.getEmail(), 
            usuario.getFechaNac(), 
            usuario.getImagenPerfil()
        );

        if (usuario instanceof Cliente) {
            clientesSeguidos.add(dataUsuario); // Añadir DataUsuario en lugar de solo nickname
        } else if (usuario instanceof Artista) {
            artistasSeguidos.add(dataUsuario); // Añadir DataUsuario en lugar de solo nickname
        }
    }

    Map<String, ArrayList<DataUsuario>> usuariosMap = new HashMap<>();
    usuariosMap.put("clientes", clientesSeguidos);  // Ahora almacena DataUsuario
    usuariosMap.put("artistas", artistasSeguidos);  // Ahora almacena DataUsuario

    return usuariosMap;
}
    
    
    public boolean esPublicaLista(int idLista){
        ListaParticular lp = cpu.traerListaParticularPorId(idLista);

        if(lp.isPublica()){
            return true;
        }

        return false;
    }
    
    //michel
    //Registrar album
    
    public void altaAlbum(){
        Album nuevoAlbum = new Album(albumGenerico);
        //Album nuevoAlbum = new Album();
        cpu.crearAlbum(nuevoAlbum);
        albumGenerico.limpiarAlbum();
    }
    
//    public void actualizoAlbum(){
//        albumGenerico.setNombre()
//        Album alb = cpu.obtenerAlbum("nuevoAlbum");
//        cpu.actualizarAlbum(alb);
//    }
    
    public void cargarDatosAlbum(String nombre, String nombreArtista, Integer fechaCreacion, String imagen){
        Artista ar = (Artista) cpu.obtenerUsuario(nombreArtista);
        if (ar != null) {
        albumGenerico.setNombre(nombre);
        albumGenerico.setFechaCreacion(fechaCreacion);
        albumGenerico.setImagenAlbum(imagen);
        albumGenerico.setArtista(ar);  // Asocia el artista
    }
    }
    
    public void cargarGenerosSys(){
        List<Genero> listaGen = cpu.getGeneros();
        for (Genero gen : listaGen){
            this.Generos.put(gen.getNombre(), gen);
        }
    }
    
    public Album getAlbumEspecificoArt(String nombreA, String nombreArtista){
        Album album = cpu.getAlbumArt(nombreArtista, nombreA);
        return album;
    }
    
    public DataAlbum getAlbumEspecificoArt2(String nombreA, String nombreArtista){
        Album album = cpu.getAlbumArt(nombreArtista, nombreA);
        
        DataAlbum dataAlbum = new DataAlbum();

        dataAlbum.setNombre(album.getNombre());
        dataAlbum.setFechaCreacion(album.getFechaCreacion());
        dataAlbum.setImagenAlbum(album.getImagenAlbum());

        List<String> dataGeneros = album.getGeneros().stream()
            .map(Genero::getNombre)
            .collect(Collectors.toList());
        dataAlbum.setGeneros(dataGeneros);

        List<DataTema> dataTemas = album.getTemas().stream()
            .map(tema -> new DataTema(
                tema.getNombre(),
                tema.getDuracion(),
                tema.getPosicion(),
                tema.getArchivo(),
                tema.getDireccionWeb()
            ))
            .collect(Collectors.toList());
        dataAlbum.setTemas(dataTemas);

        return dataAlbum;
    }
    
    public Album getAlbumEspecificoGen(String nombreA, String nombreGenero){
        Album album = cpu.getAlbumGen(nombreGenero, nombreA);
        
        
        return album;
    }
    
    public DataAlbum getAlbumEspecificoGen2(String nombreA, String nombreGenero){
        Album album = cpu.getAlbumGen(nombreGenero, nombreA);

        DataAlbum dataAlbum = new DataAlbum();

        dataAlbum.setNombre(album.getNombre());
        dataAlbum.setFechaCreacion(album.getFechaCreacion());
        dataAlbum.setImagenAlbum(album.getImagenAlbum());

        List<String> dataGeneros = album.getGeneros().stream()
            .map(Genero::getNombre)
            .collect(Collectors.toList());
        dataAlbum.setGeneros(dataGeneros);

        List<DataTema> dataTemas = album.getTemas().stream()
            .map(tema -> new DataTema(
                tema.getNombre(),
                tema.getDuracion(),
                tema.getPosicion(),
                tema.getArchivo(),
                tema.getDireccionWeb()
            ))
            .collect(Collectors.toList());
        dataAlbum.setTemas(dataTemas);

        return dataAlbum;
    }
    
    public Album getAlbumGenerico(){
        return albumGenerico;
    }
       
    public boolean verificaAlbum(String nombreA, String nombreArtista){
        Album album = cpu.getAlbumArt(nombreArtista, nombreA);
        if(album != null){
        return true;
        }
        return false;
    }
    
    public List<DataAlbum> getAllAlbums(){
        return cpu.getAllAlbumes();
    }
    
//    public void desvinculaAlbum(String nombreA, String nombreArtista){
//        Artista ar = (Artista) Usuario.obtenerUsuario(nombreArtista);
//        ar.getAlbumesMap().remove(nombreA);
//    }
    
    public void altaGenero(String nombre){
        Genero nuevoGenero = new Genero(nombre);
        this.addGenero(nuevoGenero);
    }
    
    public void altaTema(String nombreT, String duracion, Integer posicion, String direccionWeb, String archivo){
        Tema tema = albumGenerico.altaTema(nombreT, duracion, posicion, direccionWeb, archivo);
        
        //cpu.crearTema(tema);
    }

    public void addGeneroAlbum(String nombreGenero){
        List<Genero> genList = cpu.getGeneros();
        Genero genAgregar = cpu.obtenerGenero(nombreGenero);
        
        if(genAgregar != null){
            for (Genero gen : genList){
                if (gen.getNombre().equals(nombreGenero)){
                    if (!albumGenerico.getGeneros().contains(gen)) {
                        albumGenerico.addGenero(genAgregar);
                        //addAlbumGenero(genAgregar.getNombre(),albumGenerico);

                    }
                    //cpu.actualizarAlbum(albumGenerico);
                }
            }
        }
    }
    
    
    public void addGenero(Genero genero){
        String nombreGenero = genero.getNombre();
        this.Generos.put(nombreGenero, genero);
    }
    
    public HashSet<String> getGeneros() {
        if (cpu.getGeneros().isEmpty()) {
            return new HashSet<String>();  
        }else {
            HashSet<String> generos = new HashSet<>(); 
            for (Genero gen : cpu.getGeneros()) {
                generos.add(gen.getNombre());
            }
        return generos;
        }
    }
    
    public Collection<DataAlbum> getAlbumesArt(String nombreArtista){
        Artista art = (Artista) cpu.obtenerUsuario(nombreArtista);
        
        Collection<Album> albumes = cpu.getAlbumesArt(art);
        Collection<DataAlbum> dtAlbum = new ArrayList<>();
        
        if (art != null){
            for(Album album : albumes){
                dtAlbum.add(album.getDataAlbum());
            }
        }
        return dtAlbum;
    }
    
    public Collection<DataAlbum> getAlbumesGen(String nombreGenero){
        Genero gen = cpu.obtenerGenero(nombreGenero);
        Collection<Album> albumes = cpu.getAlbumesGen(nombreGenero);
        Collection<DataAlbum> dtAlbum = new ArrayList<>();
        
        
        if (gen != null){
            for(Album album : albumes){
                dtAlbum.add(album.getDataAlbum());
            }
        }
        
        return dtAlbum;
    }
    
    public Genero getGenero(String nombreGenero){
        Genero gen = cpu.obtenerGenero(nombreGenero);
        return gen;
    }
    
    public String getPadre(String nombreGenero){
        Genero gen = getGenero(nombreGenero);
        return gen.getPadre();
    }
    //Fran
    //Generos
    public boolean encontrarGenero(String nom){
        if(cpu.obtenerGenero(nom) != null){
            return true;
        }else{ return false; }
    }
    
    public void altaGenero(String nom, String pad) throws GeneroRepetidoException{
        if(encontrarGenero(nom)){
            throw new GeneroRepetidoException("Ya existe un Genero con ese nombre, por favor corrijalo para continuar.");
        }else{
            Genero gen = new Genero(nom,pad);
            cpu.crearGenero(gen);
        }
    }
    
    public Set<String> listarGeneros(){
        Set<String> ret = new HashSet<>();
        List<Genero> listGen = new ArrayList<>();
        for(Genero gen : cpu.getGeneros()){
            ret.add(gen.getNombre());
        }
        return ret;
    }
    
    public Set<String> listarGenerosSinPadre() {
        Set<String> dtg = new HashSet<>();
        for (Genero gen:cpu.getGeneros()) {
            if (gen.getPadre()== "Genero"){
                dtg.add(gen.getNombre());
            }
        }
        return dtg;
    }
    
    
   //Listas de Reproduccion
    public boolean encontrarLista(String nom){
        List<ListaReproduccion> lr = cpu.listaListas();
        boolean control = false;
        for(ListaReproduccion dt:lr){
            if(dt.getNombre() == nom){
                control = true;
            }
        }
        return control;
    }
    
    public void altaListaPart(DataLista dtl) throws ListaRepetidaException {
        if (encontrarLista(dtl.getNombre())) {
            throw new ListaRepetidaException("Ya existe un Lista con ese nombre, por favor corrijalo para continuar.");
        } else {
            ListaParticular lp = new ListaParticular(dtl.getNombre(), dtl.getImagen(), dtl.getExtra());
            cpu.crearListarPar(lp);
            Cliente cliente_elegido = cpu.obtenerCliente(dtl.getExtra());
            cliente_elegido.agrgarListaAListasCreadas(lp);
            cpu.actualizarUsuario(cliente_elegido);
            
        }
    }

    public void altaListaDef(DataLista dtl) throws ListaRepetidaException {
        if (encontrarLista(dtl.getNombre())) {
            throw new ListaRepetidaException("Ya existe un Lista con ese nombre, por favor corrijalo para continuar.");
        } else {
            ListaPorDefecto lp = new ListaPorDefecto(dtl.getNombre(), dtl.getImagen(), dtl.getExtra());
            lp.setGenero(dtl.getExtra());
            cpu.crearListaDef(lp);
        }
    }
    
    public Set<String> listarListas(){
        Set<String> sl = new HashSet<>();
        for(ListaReproduccion lr: cpu.listaListas()){
            sl.add(lr.getNombre());
        }
        return sl;
    }
    
    public Set<String> listarListasPar(){
        Set<String> sl = new HashSet<>();
        for(ListaReproduccion lr: cpu.listaListas()){
            if(lr instanceof ListaParticular){
                sl.add(lr.getNombre());
            }
        }
        return sl;
    }
    
    public Set<String> listarListasDef(){
        Set<String> sl = new HashSet<>();
        for(ListaReproduccion lr: cpu.listaListas()){
            if(lr instanceof ListaPorDefecto){
                sl.add(lr.getNombre());
            }
        }
        return sl;
    }
    
    public DataLista darLista(String nom){
        DataLista dl = null;
        if(cpu.findLista(nom) instanceof ListaPorDefecto){
            ListaPorDefecto lr =(ListaPorDefecto)cpu.findLista(nom);
            dl = new DataLista(lr);
        }else{
            ListaParticular lr = (ListaParticular) cpu.findLista(nom);
            dl = new DataLista(lr);
        }
        return dl;
    }

    public List<DataLista> traerListasParticularesPorClientes(String nickname) {
        List<ListaParticular> lp = cpu.traerListasParticularesPorCliente(nickname);
        List<DataLista> DTListasP = new ArrayList();
        for(ListaParticular lp2: lp){
            DTListasP.add(new DataLista(lp2));
        }
        return DTListasP;

    }
    
    public List<DataLista> traerListasDefectoPorGenero(String genero){
        List<ListaPorDefecto> ld = cpu.traerListasPorDefectoPorGenero(genero);
        List<DataLista> DTListasD = new ArrayList();
        for(ListaPorDefecto lp2: ld){
            DTListasD.add(new DataLista(lp2));
        }
        return DTListasD;
    }

    public ListaParticular traerListaParticularPorId(int id) {
        return cpu.traerListaParticularPorId(id);
    }
    
    public ListaParticular traerListaParticularPorCliente(String nombre, String nickname) {
        List<ListaParticular> lp = cpu.traerListasParticularesPorCliente(nickname);
        List<DataLista> DTListasP = new ArrayList();
        ListaParticular list = null;
        for(ListaParticular lp2: lp){
            if(lp2.getNombre().equals(nombre)){
                list = lp2;
            }
        }
        return list;
    }


    public void modificarListaParticularPorId(int listaId) {
        ListaParticular listaP = cpu.traerListaParticularPorId(listaId);

        listaP.setPublica(true);

        cpu.modificarListaParticular(listaP);

    }
    
      
      public List<String> traerNickNamesClientes(){
          
          List<Usuario> listaUsers = cpu.traerUsuariosClientes();
          List<String> nickClientes= new ArrayList();
          for(Usuario user: listaUsers){
              nickClientes.add(user.getNickname());
          }
          return nickClientes;
      }
      
      /*public List<ListaParticular> traerListasParticularesPorClientes(String nickname){
          return cpu.traerListasParticularesPorCliente(nickname);
      }*/
      

    //brian
     
   public ArrayList Nicknames_De_Clientes(){
        List<Cliente> traigo = cpu.obtenerTodosLosClientes();
        ArrayList<Cliente> meto = new ArrayList<Cliente>(traigo);
        ArrayList<String> cambio = new ArrayList<>();
        for (int i = 0; i < meto.size(); i++) {
            cambio.add(meto.get(i).getNickname());
    }
        return cambio;
    }
    public ArrayList Listar_Usuarios_A_Seguir(String cliente_seleccionado){
        List<Usuario> traigo = cpu.getUsuarios();
        Cliente cliente_seguidor = cpu.obtenerCliente(cliente_seleccionado);
        
        ArrayList<Usuario> seguidos = cliente_seguidor.getUsuariosSiguiendo();
        ArrayList<String> cambio2 = new ArrayList<>();
        for (int i = 0; i < seguidos.size(); i++) {
            cambio2.add(seguidos.get(i).getNickname());
    }
       
        ArrayList<Usuario> meto = new ArrayList<Usuario>(traigo);
        ArrayList<String> cambio = new ArrayList<>();
        for (int i = 0; i < meto.size(); i++) {
            cambio.add(meto.get(i).getNickname());
    }
        for (int i = 0; i < cambio2.size(); i++) {
            cambio.remove(cambio2.get(i));
    }
        //cambio.remove(nickname);
        
        return cambio;
    }
    
    public  void Seguir_Usuario(String cliente_seleccionado, String nickname_usuario){
         Usuario usuario_A_Seguir = cpu.obtenerUsuario(nickname_usuario);
       Cliente cliente_Que_Sigue = cpu.obtenerCliente(cliente_seleccionado);
       cliente_Que_Sigue.getUsuariosSiguiendo().add(usuario_A_Seguir);
       cpu.actualizarUsuario(cliente_Que_Sigue);
    }

    public ArrayList<String> Listar_Usuarios_A_DEJAR_DE_Seguir(String cliente_seleccionado){
       Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
       ArrayList<Usuario> usuarios = cliente_elegido.getUsuariosSiguiendo();
       ArrayList<String> usuarios_string = new ArrayList<>();
       for(int i= 0; i < usuarios.size();i++){
           usuarios_string.add(usuarios.get(i).getNickname());
       }
       
       return usuarios_string;
    }
    
    public void Dejar_De_Seguir_Usuario(String cliente, String usuario){
        Cliente cliente_elegido = cpu.obtenerCliente(cliente);
        Usuario usuario_elegido = cpu.obtenerUsuario(usuario);
        String nombre_usuario = usuario_elegido.getNickname();
        for(int i = 0; i < cliente_elegido.getUsuariosSiguiendo().size(); i++){
           if( cliente_elegido.getUsuariosSiguiendo().get(i).getNickname() == nombre_usuario){
               cliente_elegido.getUsuariosSiguiendo().remove(i);
           }
        }
       
        cpu.actualizarUsuario(cliente_elegido);
    }
    
    public ArrayList<String> Listar_Temas_A_Seguir(String cliente_seleccionado){
        Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
       ArrayList<Tema> a_cambiar_objeto = cliente_elegido.getTemasFavoritos();
       ArrayList<String> a_mostrar = new ArrayList<>();
       List<Tema> temas = cpu.Listar_Temas();
       for(int i = 0; i < temas.size();i++){
           String albumConId = temas.get(i).getNombre() + " " + temas.get(i).getId();
           a_mostrar.add(albumConId);
          //System.out.println(a_mostrar.get(i).substring(0,a_mostrar.get(i).length() - 1));
       }


       for (Tema favorito : a_cambiar_objeto) {
        String albumConId = favorito.getNombre() + " " + favorito.getId();

        // Si el álbum está en a_mostrar, eliminarlo
        if (a_mostrar.contains(albumConId)) {
            a_mostrar.remove(albumConId);
        } else {
            System.out.println("No borra: " + albumConId);
        }
    }
       return a_mostrar;
    }
    
    public void Seguir_TEMA(String cliente_seleccionado, String tema_seleccionado){
       Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
        List<Tema> albumes = cpu.Listar_Temas();
        List<Tema> a_seguir = new ArrayList<>();

        for(int i = 0 ; i < albumes.size(); i++){
            String albumConId = albumes.get(i).getNombre() + " " + albumes.get(i).getId();
            System.out.println("Comparando con: " + albumConId); // Debug
            if (albumConId.equals(tema_seleccionado)) {
                a_seguir.add(albumes.get(i));
            }
        }

        for(int i = 0; i < a_seguir.size(); i++){
        cliente_elegido.getTemasFavoritos().add(a_seguir.get(i));

        }
        cpu.actualizarUsuario(cliente_elegido);
    }
    public ArrayList<String> Listar_Albumes_A_Seguir(String cliente_seleccionado){
      Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
       ArrayList<Album> a_cambiar_objeto = cliente_elegido.getAlbumesFavoritos();
       ArrayList<String> a_mostrar = new ArrayList<>();
       List<Album> albumes = cpu.Listar_Albumes();
       for(int i = 0; i < albumes.size();i++){
           String albumConId = albumes.get(i).getNombre() + " " + albumes.get(i).getId();
           a_mostrar.add(albumConId);
          //System.out.println(a_mostrar.get(i).substring(0,a_mostrar.get(i).length() - 1));
       }


       for (Album favorito : a_cambiar_objeto) {
        String albumConId = favorito.getNombre() + " " + favorito.getId();

        // Si el álbum está en a_mostrar, eliminarlo
        if (a_mostrar.contains(albumConId)) {
            a_mostrar.remove(albumConId);
        } else {
            System.out.println("No borra: " + albumConId);
        }
    }
       return a_mostrar;
    }
    
    public void Seguir_ALBUM(String cliente_seleccionado,String album_seleccionado){
       Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
        List<Album> albumes = cpu.Listar_Albumes();
        List<Album> a_seguir = new ArrayList<>();

        for(int i = 0 ; i < albumes.size(); i++){
            String albumConId = albumes.get(i).getNombre() + " " + albumes.get(i).getId();
            System.out.println("Comparando con: " + albumConId); // Debug
            if (albumConId.equals(album_seleccionado)) {
                a_seguir.add(albumes.get(i));
            }
        }

        for(int i = 0; i < a_seguir.size(); i++){
        cliente_elegido.getAlbumesFavoritos().add(a_seguir.get(i));

        }
        cpu.actualizarUsuario(cliente_elegido);
    }
    
    public ArrayList<String> Listar_Listas_A_Seguir(String cliente_seleccionado){
         Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
         ArrayList<ListaReproduccion> a_cambiar_objeto = cliente_elegido.getListasFavoritas();
       ArrayList<String> a_mostrar = new ArrayList<>();
       List<ListaReproduccion> listas_particulares = cpu.listaListas();
       for(int i = 0; i < listas_particulares.size();i++){
           if(listas_particulares.get(i) instanceof ListaParticular){
               ListaParticular lista = (ListaParticular) listas_particulares.get(i);
             if (lista.esPublica()) {
                  String albumConId = listas_particulares.get(i).getNombre() + " " + listas_particulares.get(i).getId();
                   a_mostrar.add(albumConId);
             }
           }else{
            String albumConId = listas_particulares.get(i).getNombre() + " " + listas_particulares.get(i).getId();
            a_mostrar.add(albumConId);
              
           }
           }
       
         for (ListaReproduccion favorito : a_cambiar_objeto) {
        String albumConId = favorito.getNombre() + " " + favorito.getId();

        // Si el álbum está en a_mostrar, eliminarlo
        if (a_mostrar.contains(albumConId)) {
            a_mostrar.remove(albumConId);
        } else {
            System.out.println("No borra: " + albumConId);
        }
    }
       return a_mostrar;
    }
    public void Seguir_LISTA(String cliente_seleccionado, String lista_seleccionada){
        Cliente cliente_elegido = cpu.obtenerCliente(cliente_seleccionado);
        List<ListaReproduccion> listas = cpu.listaListas();
        List<ListaReproduccion> a_seguir = new ArrayList<>();

        for(int i = 0 ; i < listas.size(); i++){
            String albumConId = listas.get(i).getNombre() + " " + listas.get(i).getId();
            System.out.println("Comparando con: " + albumConId); // Debug
            if (albumConId.equals(lista_seleccionada)) {
                a_seguir.add(listas.get(i));
            }
        }
        for(int i = 0; i < a_seguir.size(); i++){
        cliente_elegido.getListasFavoritas().add(a_seguir.get(i));

        }
        cpu.actualizarUsuario(cliente_elegido);
    }
    
    public DataLista traerListaDefGeneroNombre(String genero, String nombre){
        List<ListaPorDefecto> dtl = cpu.traerListasPorDefectoPorGenero(genero);
        DataLista dl = null; 
        for(ListaPorDefecto ld:dtl){
            if(ld.getNombre().equals(nombre)){
                dl = new DataLista(ld);
            }
        }
        return dl;
    }
    
    public DataLista traerListaPartClienteNombre(String cliente, String nombre){
        List<ListaParticular> dtl = cpu.traerListasParticularesPorCliente(cliente);
        DataLista dl = new DataLista();
        for(ListaParticular lp:dtl){
            if(lp.getNombre().equals(nombre)){
                dl = new DataLista(lp);
            }
        }
        return dl;
    }
    
     //probando 
   public List<String> traerGeneros(){
       List<Genero> generos = cpu.traerTodosGeneros();
       List<String> generos_string = new ArrayList<>();
       for(int i = 0; i < generos.size();i++){
           generos_string.add(generos.get(i).getNombre());
           System.out.println(generos.get(i).getNombre());
       }
       return generos_string;
   }
   public List<String> traerArtistas(){
       List<Artista> artistas = cpu.obtenerTodosLosArtistas();
       List<String> generos_string = new ArrayList<>();
       for(int i = 0; i < artistas.size();i++){
           generos_string.add(artistas.get(i).getNombre());
           System.out.println(artistas.get(i).getNombre());
       }
       return generos_string;
   } 
   public List<String> traerAlbumesGenero(String genero){
    
        List<Album> albumes = (List<Album>) cpu.obtenerGenero(genero).getAlbumes();
        List<String> albumes_string = new ArrayList<>();
        for(int i = 0; i < albumes.size();i++){
            albumes_string.add(albumes.get(i).getNombre());
        }
        return albumes_string;
   }
   
   public List<String> traerAlbumesArtista(String artista){
       Artista art = (Artista) cpu.obtenerUsuario(artista);
       List<Album> albumes = (List<Album>) cpu.getAlbumesArt(art);
        List<String> albumes_string = new ArrayList<>();
        for(int i = 0; i < albumes.size();i++){
            albumes_string.add(albumes.get(i).getNombre());
        }
        return albumes_string;
   }
   
   public Vector<DataAlbum> traerAlbumes(){
        Vector<String> listaAlbumes;
        List<Album> traerAlbumes= cpu.Listar_Albumes();
        Vector<DataAlbum> dtAlbumes= new Vector<>();
        
        for(Album album : traerAlbumes){
            dtAlbumes.add(album.getDataAlbum());
            // public DataAlbum(String nombre, Integer fechaCreacion, String imagen, Collection<DataTema> temas, Collection<String> generos)
        }
        return dtAlbumes;
    }
   
    public List<DataTema> traerTemasPorIdAlbum(Long id){
        List<Tema> listaTemasAlbum= cpu.traerTemasPorIdAlbum(id);
        List<DataTema> DTListaAlbum = new ArrayList();

        for(Tema t1: listaTemasAlbum){
            DTListaAlbum.add(t1.getDataTemaI());
        }

        return DTListaAlbum;
    }
    
    public void agregarTemaLista(int idLista, Long idTema){ // para modificar
          
          ListaReproduccion listaR = new ListaReproduccion();
          listaR=cpu.traerListaParticularPorId(idLista);
          if(listaR==null){
              listaR=cpu.traerListaPorDefectoPorId(idLista);
          }
          
          Tema nuevoTema = new Tema();
          
          nuevoTema=cpu.traerTemaPorId((Long)idTema);
          
          listaR.agregarTema(nuevoTema);
          
        try {
            cpu.editarListaReproduccion(listaR);
        } catch (Exception ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
           
      }
    
    public Vector<DataLista> traerListasParticularesPublicas(){
          List<ListaParticular> listasP = cpu.traerListasParticularesPublicas();
          Vector<DataLista> DTListas = new Vector<>();
          
          for(ListaParticular lp : listasP){
              DataLista dtLista = new DataLista(lp);
              DTListas.add(dtLista);
          }
          
          return DTListas;
      }
      
      public List<DataTema> traerTemasPorIdListaReproduccion(int id){
          Set<Tema> listaTemas = cpu.traerTemasPorIdListaReproduccion(id);
          List<DataTema> DTTemas = new ArrayList();
          
          for(Tema t1:listaTemas){
              DTTemas.add(t1.getDataTemaI());
          }
          
          return DTTemas;
      }
      
      public List<DataLista> traerListasPorDefecto(){
          List<ListaPorDefecto> listasPorDefecto= cpu.Listar_Listas_Por_Defecto();
          List<DataLista> DTListaDefecto = new ArrayList();
          
          for(ListaPorDefecto l1: listasPorDefecto){
              DataLista dtListaDef = new DataLista(l1);
              
              DTListaDefecto.add(dtListaDef);
          }
         
          return DTListaDefecto;
      }
      
      public ListaPorDefecto traerListaPorDefecto(String nombre){
          List<ListaPorDefecto> listasPorDefecto = cpu.Listar_Listas_Por_Defecto();
          ListaPorDefecto l = null;
          
          for(ListaPorDefecto li: listasPorDefecto){
              if (li.getNombre().equals(nombre)){
                  l = li;
              }
          }
         
          return l;
      }
      
      public List<DataTema> traerTemasPorListasPorDefectoId(int listaid){
          
          ListaPorDefecto listaDefecto = cpu.traerListaPorDefectoPorId(listaid);
          
          List<DataTema> DTListasTemas= new ArrayList();
          
          Set <Tema>listaTemas= listaDefecto.getListaTemas();
          
          for(Tema t: listaTemas){
              DTListasTemas.add(t.getDataTemaI());
          }
          
          return DTListasTemas;
          
      }
      
      public List<DataLista> traerListasFavoritasPorIdCliente(String nickname){
          Cliente cliente = cpu.obtenerCliente(nickname);
          List<DataLista> DTListasFav = new ArrayList();
          List<ListaReproduccion> listasFavoritas = cliente.getListasFavoritas();
          
          
          for(ListaReproduccion list:listasFavoritas){
             DataLista dtL = new DataLista();
             dtL.setId(list.getId());
             dtL.setNombre(list.getNombre());
             DTListasFav.add(dtL);
          }
          
          return DTListasFav;
      }
      
      public void eliminarListaFavoritaCliente(String nickname, int idLista){
          
          Cliente cliente=cpu.obtenerCliente(nickname);
          
          
          cliente.eliminarListaFavorita(idLista);
          
          cpu.editarCliente(cliente);
          
          
      }
      
      public List<DataTema> traerTemasFavoritosPorIdCliente(String nickname){
          Cliente cliente = cpu.obtenerCliente(nickname);
          List<DataTema> DTTemas = new ArrayList();
          List<Tema> listaTemas = cliente.getTemasFavoritos();

          for(Tema t: listaTemas){
             
             DTTemas.add(t.getDataTemaI());
          }
          
          return DTTemas;
      }
      
      
      public void eliminarTemasFavoritosCliente(String nickname, Long idTema){
          
          Cliente cliente=cpu.obtenerCliente(nickname);
          
          
          cliente.eliminarTemaFavorito(idTema);
          
          cpu.editarCliente(cliente);
          
          
      }
      
       public List<DataAlbum> traerAlbumesFavoritosPorIdCliente(String nickname){
          Cliente cliente = cpu.obtenerCliente(nickname);
          List<DataAlbum> DTAlbum = new ArrayList();
          List<Album> listaAlbumes = cliente.getAlbumesFavoritos();
          
          for(Album a: listaAlbumes){
             
             DTAlbum.add(a.getDataAlbum());
          }
          return DTAlbum;
      }
       
       public void eliminarAlbumesFavoritosCliente(String nickname, Long idAlbum){
          
          Cliente cliente=cpu.obtenerCliente(nickname);
          
          
          cliente.eliminarAlbumesFavoritos(idAlbum);
          
          cpu.editarCliente(cliente);
          
      }
       
       public List<Tema> getTemas(Album album){
           return cpu.traerTemasPorIdAlbum(album.getId());
       }
       
       public DataGenero[] getGeneros2() throws GeneroNoExisteException {
        List<Genero> generos = cpu.obtenerTodosLosGeneros();
        if (!generos.isEmpty()) {
            DataGenero[] dataGen = new DataGenero[generos.size()];
            for (int i = 0; i < generos.size(); i++) {
                dataGen[i] = generos.get(i).devolverData();
            }
            return dataGen;
        }else{
            throw new GeneroNoExisteException("No existen generos registrados");
        } 
    }

    public DataAlbum[] getAlbumsByGenero(String genero) throws AlbumNoExisteException {
        List<Album> albums = cpu.obtenerAlbumPorGenero(genero);
        if (!albums.isEmpty()) {
            DataAlbum[] dataAlbums = new DataAlbum[albums.size()];
            for (int i = 0; i < albums.size(); i++) {
                dataAlbums[i] = albums.get(i).devolverData();
                System.out.println(dataAlbums[i]);
            }

            return dataAlbums;
        } else {
            throw new AlbumNoExisteException("No existen álbumes para el género seleccionado.");
        }
    }
       
       public List<DataLista> getListasParticulares(String nickname){
            List<ListaParticular> dtListaPart = cpu.traerListasParticularesPorCliente(nickname);
            List<DataLista> DTListas = new ArrayList<>();;
            for(ListaParticular lp : dtListaPart){
              DataLista dtLista = new DataLista(lp);
              DTListas.add(dtLista);
          }

            return DTListas;
       }

       public DataLista getListaDefecto(String nombre){
           ListaPorDefecto lista = traerListaPorDefecto(nombre);
           DataLista dtLista = new DataLista(lista);
           return dtLista;
       }
   
       public void eliminarTemasListaPart(String nombre, String nombreL, Long idTema){

        DataLista dtL = traerListaPartClienteNombre(nombre, nombreL);
        ListaParticular listaP = (ListaParticular)cpu.traerListaReproduccionPorId(dtL.getId());
        listaP.eliminarTema(idTema);
        cpu.editarListaReproduccion(listaP);
        dtL.eliminarTema(idTema);
 
      }
       
       public void eliminarTemaListaDef(String nombre, Long idTema){
           DataLista dtL = getListaDefecto(nombre);
           ListaPorDefecto listaD = cpu.traerListaPorDefectoPorId(dtL.getId());
           listaD.eliminarTema(idTema);
           cpu.editarListaReproduccion(listaD);
           dtL.eliminarTema(idTema);
       }
       
       public boolean existeTemaLista(Long idTema, int idLista) {

        ListaReproduccion lista = cpu.traerListaReproduccionPorId(idLista);

        Set<Tema> listaTemas = lista.getListaTemas();

        for(Tema t: listaTemas){
            if(t.getId()==idTema){
                return true;
            }
        }
        return false;

      }
       
   public void cargarDatos() throws GeneroRepetidoException, UsuarioRepetidoException{
       
    // Artistas
    altaPerfil("vpeople", "Village", "People", "vpeople123", "vpeople@tuta.io", LocalDate.parse("1977-01-01"), "vpeople_artista.jpg", 
        "Village People es una innovadora formación musical de estilo disco de finales de los años 70. Fue famosa tanto por sus peculiares disfraces, como por sus canciones pegadizas, con letras sugerentes y llenas de dobles sentidos.", 
        "www.officialvillagepeople.com", ARTISTA);

    altaPerfil("dmode", "Depeche", "Mode", "dmode123", "dmode@tuta.io", LocalDate.parse("1980-06-14"), "dmode_artista.jpg", 
        null, "www.depechemode.com", ARTISTA);

    altaPerfil("clauper", "Cyndi", "Lauper", "clauper123", "clauper@hotmail.com", LocalDate.parse("1953-06-22"), "clauper_artista.jpg", 
        "Cynthia Ann Stephanie Lauper, conocida como Cyndi Lauper, es una cantautora, actriz y empresaria estadounidense. Después de participar en el grupo musical, Blue Angel, en 1983 firmó con Portrait Records y lanzó su álbum debut She's So Unusual a finales de ese mismo año. Continuó lanzando álbumes exitosos a lo largo de su carrera.", 
        "cyndilauper.com", ARTISTA);

    altaPerfil("bruceTheBoss", "Bruce", "Springsteen", "bruce123", "bruceTheBoss@gmail.com", LocalDate.parse("1949-09-23"), "brucetheboss_artista.jpg", 
        null, "brucespringsteen.net", ARTISTA);

    altaPerfil("tigerOfWales", "Tom", "Jones", "tiger123","tigerOfWales@tuta.io", LocalDate.parse("1940-06-07"), null, 
        "Sir Thomas John, conocido como Tom Jones, es un cantante británico con más de 100 millones de discos vendidos en todo el mundo.", 
        "www.tomjones.com", ARTISTA);

    altaPerfil("tripleNelson", "La Triple", "Nelson", "triple123", "tripleNelson@tuta.io", LocalDate.parse("1998-01-01"), "triplenelson_artista.jpg", 
        "La Triple Nelson es un grupo de rock uruguayo formado en 1998 e integrado inicialmente por Christian Cary, Fernando Pintos y Rubén Otonello.", 
        null, ARTISTA);

    altaPerfil("la_ley", "La", "Ley", "laley123", "la_ley@tuta.io", LocalDate.parse("1987-02-14"), null, null, null, ARTISTA);

    altaPerfil("chaiko", "Piotr", "Tchaikovsky", "chaiko123","chaiko@tuta.io", LocalDate.parse("1840-04-25"), null, 
        "Piotr Ilich Chaikovski fue un compositor ruso del período del Romanticismo.", 
        null, ARTISTA);

    altaPerfil("nicoleneu", "Nicole", "Neumann", "nicole123", "nicoleneu@hotmail.com", LocalDate.parse("1980-10-31"), "nicoleneuman_artista.jpg", null, null, ARTISTA);

    altaPerfil("lospimpi", "Pimpinela", null, "lospimpi123", "lospimpi@gmail.com", LocalDate.parse("1981-08-13"), "pimpinela_artista.jpg", null, "www.pimpinela.net", ARTISTA);

    altaPerfil("dyangounchained", "Dyango", null, "dyango123", "dyangounchained@gmail.com", LocalDate.parse("1940-03-05"), null, 
        "José Gómez Romero, conocido como Dyango, es un cantante español de música romántica.", 
        null, ARTISTA);

    altaPerfil("alcides", "Alcides", null, "alcides123", "alcides@tuta.io", LocalDate.parse("1952-07-17"), null, 
        "Su carrera comienza en 1976 cuando forma la banda Los Playeros junto a su hermano Víctor. A nivel nacional, Alcides gana popularidad en los años 1990 con el éxito 'Violeta'.", 
        null, ARTISTA);

    // Clientes
    altaPerfil("el_padrino", "Vito", "Corleone", "elpadrino123", "el_padrino@tuta.io", LocalDate.parse("1972-03-08"), "vitoCorleone_cliente.JPG", null, null, CLIENTE);

    altaPerfil("scarlettO", "Scarlett", "O'Hara", "scarlett0123", "scarlettO@tuta.io", LocalDate.parse("1984-11-27"), "scarletto_cliente.jpg", null, null, CLIENTE);

    altaPerfil("ppArgento", "Pepe", "Argento", "ppargento123", "ppArgento@hotmail.com", LocalDate.parse("1955-02-14"), "pepeargento_cliente.png", null, null, CLIENTE);

    altaPerfil("Heisenberg", "Walter", "White", "heisenberg123", "Heisenberg@tuta.io", LocalDate.parse("1956-03-07"), "heisenbergBD_cliente.png", null, null, CLIENTE);

    altaPerfil("benKenobi", "Obi-Wan", "Kenobi", "ben123", "benKenobi@gmail.com", LocalDate.parse("1914-04-02"), "Ben_Kenobi.png", null, null, CLIENTE);

    altaPerfil("lachiqui", "Mirtha", "Legrand", "lachiqui123", "lachiqui@hotmail.com.ar", LocalDate.parse("1927-02-23"), "Mirtha_Legrand_cliente.jpg", null, null, CLIENTE);

    altaPerfil("cbochinche", "Cacho", "Bochinche", "cbochinche123", "cbochinche@vera.com.uy", LocalDate.parse("1937-05-08"), "cachobochinche_cliente.jpg", null, null, CLIENTE);

    altaPerfil("Eleven11", "Eleven", null, "eleven123", "Eleven11@gmail.com", LocalDate.parse("1971-12-31"), "eleven_cliente.png", null, null, CLIENTE);
    
    //GENEROS
    
    altaGenero("Rock", "Genero");
    altaGenero("Rock Clásico", "Rock");
    altaGenero("Rock Latino", "Rock");
    altaGenero("Rock & Roll", "Rock");
    altaGenero("Clásica", "Genero");
    altaGenero("Disco", "Genero");
    altaGenero("Pop", "Genero");
    altaGenero("Electropop", "Pop");
    altaGenero("Dance-pop", "Pop");
    altaGenero("Pop Clásico", "Pop");
    altaGenero("Balada", "Genero");
    altaGenero("Cumbia", "Genero");
    
       //ALBUM
    cargarDatosAlbum("Village People Live and Sleazy", "vpeople", 1980, null);
    addGeneroAlbum("Disco");
    addGeneroAlbum("Dance-pop");
    addGeneroAlbum("Pop Clásico");
    altaTema("YMCA", "4:28", 1, "-", "picosong.com/download/zf8T");
    altaTema("Macho Man", "3:28", 2, "-", "-");
    altaTema("In the Navy", "3:13", 3, "-", "bit.ly/SCvpinthenavy");
    altaAlbum();

    cargarDatosAlbum("Violator", "dmode", 1990, null);
    addGeneroAlbum("Electropop");
    altaTema("Personal Jesus", "4:56", 1, "picosong.com/download/zfQ3", "-");
    altaTema("Enjoy The Silence", "4:21", 2, "picosong.com/download/zfQX", "-");
    altaAlbum();

    cargarDatosAlbum("She’s So Unusual", "clauper", 1983, "ShesSoUnusual.png");
    addGeneroAlbum("Pop Clásico");
    addGeneroAlbum("Dance-pop");
    altaTema("Girls Just Want To Have Fun", "3:15", 1, "-", "picosong.com/download/zfER");
    altaTema("Time After Time", "5:12", 2, "-", "bit.ly/SCclgirlsjustwant");
    altaAlbum();

    cargarDatosAlbum("Born In The U.S.A.", "bruceTheBoss", 1984, null);
    addGeneroAlbum("Rock Clásico");
    addGeneroAlbum("Rock & Roll");
    addGeneroAlbum("Pop Clásico");
    altaTema("Born In The U.S.A.", "4:58", 1, "-", "bit.ly/SCbsborninusa");
    altaTema("Glory Days", "5:23", 2, "-", "bit.ly/SCbsglorydays");
    altaTema("Dancing In The Park", "3:58", 3, "-", "-");
    altaAlbum();

    cargarDatosAlbum("It’s Not Unusual", "tigerOfWales", 1965, "ItsNotUnusual.jpeg");
    addGeneroAlbum("Rock Clásico");
    addGeneroAlbum("Pop Clásico");
    altaTema("It’s Not Unusual", "2:00", 1, "-", "-");
    altaAlbum();

    cargarDatosAlbum("Agua Y Sal", "tripleNelson", 2012, null);
    addGeneroAlbum("Rock Latino");
    altaTema("Adagio De Mi País", "4:50", 1, "-", "-");
    altaAlbum();

    cargarDatosAlbum("MTV Unplugged", "la_ley", 2001, "mtvunpluged.jpg");
    addGeneroAlbum("Rock Latino");
    addGeneroAlbum("Pop Clásico");
    altaTema("El Duelo", "5:23", 1, "-", "-");
    altaTema("Mentira", "4:48", 2, "-", "-");
    altaAlbum();

    cargarDatosAlbum("El Lago De Los Cisnes", "chaiko", 1875, null);
    addGeneroAlbum("Clásica");
    altaTema("Acto 2, Número 10, Escena (Moderato)", "2:40", 1, "-", "bit.ly/SCptswanlake");
    altaAlbum();

    cargarDatosAlbum("Concierto Para Piano No. 1 En Si Menor, Opus 23", "chaiko", 1875, null);
    addGeneroAlbum("Clásica");
    altaTema("Primer Movimiento (Allegro non troppo e molto maestoso – Allegro con spirito)", "21:58", 1, "-", "bit.ly/SCptpiano");
    altaAlbum();

    cargarDatosAlbum("Primer Amor", "nicoleneu", 1994, null);
    addGeneroAlbum("Electropop");
    altaTema("No Quiero Estudiar", "2:12", 1, "picosong.com/download/zfZN", "-");
    altaAlbum();

    cargarDatosAlbum("Hay Amores Que Matan", "lospimpi", 1993, null);
    addGeneroAlbum("Pop Clásico");
    addGeneroAlbum("Balada");
    altaTema("Por Ese Hombre", "4:45", 1, "picosong.com/download/zfa4", "-");
    altaAlbum();

    cargarDatosAlbum("Un Loco Como Yo", "dyangounchained", 1993, "unlococomoyo.jpg");
    addGeneroAlbum("Pop Clásico");
    addGeneroAlbum("Balada");
    altaTema("Por Ese Hombre", "5:13", 1, "-", "bit.ly/SCdyporesehombre");
    altaAlbum();

    cargarDatosAlbum("20 Grandes Éxitos", "alcides", 1989, "20grandesexitos.jpg");
    addGeneroAlbum("Cumbia");
    altaTema("Violeta", "1:56", 1, "-", "bit.ly/SCvioleta");
    altaAlbum();
    //LISTAS DE REPRODUCCION
    List<Tema> listaTemas;

    Album VPL = getAlbumEspecificoArt("Village People Live and Sleazy", "vpeople");
    listaTemas = getTemas(VPL);
    VPL.setTemas(listaTemas);

    Album DMV = getAlbumEspecificoArt("Violator", "dmode");
    listaTemas = getTemas(DMV);
    DMV.setTemas(listaTemas);

    Album CLU = getAlbumEspecificoArt("She’s So Unusual", "clauper");
    listaTemas = getTemas(CLU);
    CLU.setTemas(listaTemas);

    Album USA = getAlbumEspecificoArt("Born In The U.S.A.", "bruceTheBoss");
    listaTemas = getTemas(USA);
    USA.setTemas(listaTemas);

    Album INU = getAlbumEspecificoArt("It’s Not Unusual", "tigerOfWales");
    listaTemas = getTemas(INU);
    INU.setTemas(listaTemas);

    Album AYS = getAlbumEspecificoArt("Agua Y Sal", "tripleNelson");
    listaTemas = getTemas(AYS);
    AYS.setTemas(listaTemas);

    Album LLU = getAlbumEspecificoArt("MTV Unplugged", "la_ley");
    listaTemas = getTemas(LLU);
    LLU.setTemas(listaTemas);

    Album LDC = getAlbumEspecificoArt("El Lago De Los Cisnes", "chaiko");
    listaTemas = getTemas(LDC);
    LDC.setTemas(listaTemas);

    Album CPP = getAlbumEspecificoArt("Concierto Para Piano No. 1 En Si Menor, Opus 23", "chaiko");
    listaTemas = getTemas(CPP);
    CPP.setTemas(listaTemas);

    Album PAM = getAlbumEspecificoArt("Primer Amor", "nicoleneu");
    listaTemas = getTemas(PAM);
    PAM.setTemas(listaTemas);

    Album AMA = getAlbumEspecificoArt("Hay Amores Que Matan", "lospimpi");
    listaTemas = getTemas(AMA);
    AMA.setTemas(listaTemas);

    Album LOC = getAlbumEspecificoArt("Un Loco Como Yo", "dyangounchained");
    listaTemas = getTemas(LOC);
    LOC.setTemas(listaTemas);

    Album VIO = getAlbumEspecificoArt("20 Grandes Éxitos", "alcides");
    listaTemas = getTemas(VIO);
    VIO.setTemas(listaTemas);
    
    
    ListaPorDefecto ld;
    DataLista LD1 = new DataLista("Noche De La Nostalgia", "nochedelanostalgia.jpg", "Pop Clásico");
        try {
            altaListaDef(LD1);
            ld = traerListaPorDefecto("Noche De La Nostalgia");
            agregarTemaLista(ld.getId(), VPL.getTema("YMCA").getId());
            agregarTemaLista(ld.getId(), VPL.getTema("Macho Man").getId());
            agregarTemaLista(ld.getId(), VPL.getTema("In the Navy").getId());
            agregarTemaLista(ld.getId(), CLU.getTema("Girls Just Want To Have Fun").getId());
            agregarTemaLista(ld.getId(), CLU.getTema("Time After Time").getId());
            agregarTemaLista(ld.getId(), USA.getTema("Born In The U.S.A.").getId());
            agregarTemaLista(ld.getId(), USA.getTema("Glory Days").getId());
            agregarTemaLista(ld.getId(), USA.getTema("Dancing In The Park").getId());
            agregarTemaLista(ld.getId(), INU.getTema("It’s Not Unusual").getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LD2 = new DataLista("Rock En Español", "-", "Rock Latino ");
        try {
            altaListaDef(LD2);
            ld = traerListaPorDefecto("Rock En Español");
            agregarTemaLista(ld.getId(), AYS.getTema("Adagio De Mi País").getId());
            agregarTemaLista(ld.getId(), LLU.getTema("El Duelo").getId());
            agregarTemaLista(ld.getId(), LLU.getTema("Mentira").getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LD3 = new DataLista("Música Clásica", "musicaclasica.jpg", "Clásica");
        try {
            altaListaDef(LD3);
            ld = traerListaPorDefecto("Música Clásica");
            agregarTemaLista(ld.getId(), LDC.getTema("Acto 2, Número 10, Escena (Moderato)").getId());
            agregarTemaLista(ld.getId(), CPP.getTema("Primer Movimiento (Allegro non troppo e molto maestoso – Allegro con spirito)").getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    ListaParticular lp;

    DataLista LP1 = new DataLista("Música Inspiradora", "musicainspiradora.jpg", "el_padrino");
        try {
            altaListaPart(LP1);
            lp = traerListaParticularPorCliente("Música Inspiradora", "el_padrino");
            agregarTemaLista(lp.getId(), LDC.getTema("Acto 2, Número 10, Escena (Moderato)").getId());
            agregarTemaLista(lp.getId(), CPP.getTema("Primer Movimiento (Allegro non troppo e molto maestoso – Allegro con spirito)").getId());
            agregarTemaLista(lp.getId(), DMV.getTema("Personal Jesus").getId());
            modificarListaParticularPorId(lp.getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LP2 = new DataLista("De Todo Un Poco", "-", "scarlettO");
        try {
            altaListaPart(LP2);
            lp = traerListaParticularPorCliente("De Todo Un Poco", "scarlettO");
            agregarTemaLista(lp.getId(), CLU.getTema("Girls Just Want To Have Fun").getId());
            agregarTemaLista(lp.getId(), CLU.getTema("Time After Time").getId());
            agregarTemaLista(lp.getId(), INU.getTema("It’s Not Unusual").getId());
            agregarTemaLista(lp.getId(), LDC.getTema("Acto 2, Número 10, Escena (Moderato)").getId());
            modificarListaParticularPorId(lp.getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LP3 = new DataLista("Para Cocinar", "ParaCocinar.png", "Heisenberg");
        try {
            altaListaPart(LP3);
            lp = traerListaParticularPorCliente("Para Cocinar", "Heisenberg");
            agregarTemaLista(lp.getId(), DMV.getTema("Personal Jesus").getId());
            agregarTemaLista(lp.getId(), DMV.getTema("Enjoy The Silence").getId());
            agregarTemaLista(lp.getId(), USA.getTema("Born In The U.S.A.").getId());
            agregarTemaLista(lp.getId(), USA.getTema("Glory Days").getId());
            //modificarListaParticularPorId(lp.getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LP4 = new DataLista("Para Las Chicas", "-", "lachiqui");
        try {
            altaListaPart(LP4);
            lp = traerListaParticularPorCliente("Para Las Chicas", "lachiqui");
            agregarTemaLista(lp.getId(), CLU.getTema("Girls Just Want To Have Fun").getId());
            agregarTemaLista(lp.getId(), INU.getTema("It’s Not Unusual").getId());
            agregarTemaLista(lp.getId(), CPP.getTema("Primer Movimiento (Allegro non troppo e molto maestoso – Allegro con spirito)").getId());
            agregarTemaLista(lp.getId(), PAM.getTema("No Quiero Estudiar").getId());
            agregarTemaLista(lp.getId(), LOC.getTema("Por Ese Hombre").getId());
            modificarListaParticularPorId(lp.getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LP5 = new DataLista("Fiesteras", "fiesteras.jpg", "cbochinche");
        try {
            altaListaPart(LP5);
            lp = traerListaParticularPorCliente("Fiesteras", "cbochinche");
            agregarTemaLista(lp.getId(), VPL.getTema("YMCA").getId());
            agregarTemaLista(lp.getId(), VPL.getTema("Macho Man").getId());
            agregarTemaLista(lp.getId(), VPL.getTema("In the Navy").getId());
            agregarTemaLista(lp.getId(), USA.getTema("Glory Days").getId());
            agregarTemaLista(lp.getId(), VIO.getTema("Violeta").getId());
            modificarListaParticularPorId(lp.getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    DataLista LP6 = new DataLista("Mis Favoritas", "-", "cbochinche");
        try {
            altaListaPart(LP6);
            lp = traerListaParticularPorCliente("Mis Favoritas", "cbochinche");
            agregarTemaLista(lp.getId(), AYS.getTema("Adagio De Mi País").getId());
            agregarTemaLista(lp.getId(), CPP.getTema("Primer Movimiento (Allegro non troppo e molto maestoso – Allegro con spirito)").getId());
            agregarTemaLista(lp.getId(), AMA.getTema("Por Ese Hombre").getId());
            //modificarListaParticularPorId(lp.getId());
        } catch (ListaRepetidaException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
         //Seguir Usuario
    Seguir_Usuario("el_padrino","vpeople");
    Seguir_Usuario("el_padrino","dmode");
    Seguir_Usuario("el_padrino","clauper");
    Seguir_Usuario("el_padrino","benKenobi");
    Seguir_Usuario("el_padrino","lachiqui");
    Seguir_Usuario("el_padrino","cbochinche");
    Seguir_Usuario("el_padrino","Eleven11");
    Seguir_Usuario("scarlettO","dmode");
    Seguir_Usuario("scarlettO","bruceTheBoss");
    Seguir_Usuario("scarlettO","tigerOfWales");
    Seguir_Usuario("scarlettO","tripleNelson");
    Seguir_Usuario("scarlettO","Heisenberg");
    Seguir_Usuario("scarlettO","benKenobi");
    Seguir_Usuario("scarlettO","lachiqui");
    Seguir_Usuario("ppArgento","dmode");
    Seguir_Usuario("ppArgento","bruceTheBoss");
    Seguir_Usuario("ppArgento","tripleNelson");
    Seguir_Usuario("ppArgento","benKenobi");
    Seguir_Usuario("ppArgento","lachiqui");
    Seguir_Usuario("ppArgento","cbochinche");
    Seguir_Usuario("ppArgento","Eleven11");
    Seguir_Usuario("Heisenberg","dmode");
    Seguir_Usuario("Heisenberg","bruceTheBoss");
    Seguir_Usuario("Heisenberg","tigerOfWales");
    Seguir_Usuario("Heisenberg","tripleNelson");
    Seguir_Usuario("Heisenberg","lospimpi");
    Seguir_Usuario("Heisenberg","dyangounchained");
    Seguir_Usuario("Heisenberg","alcides");
    Seguir_Usuario("Heisenberg","el_padrino");
    Seguir_Usuario("Heisenberg","scarlettO");
    Seguir_Usuario("Heisenberg","ppArgento");
    Seguir_Usuario("Heisenberg","benKenobi");
    Seguir_Usuario("Heisenberg","lachiqui");
    Seguir_Usuario("Heisenberg","Eleven11");
    Seguir_Usuario("benKenobi","dmode");
    Seguir_Usuario("benKenobi","bruceTheBoss");
    Seguir_Usuario("benKenobi","la_ley");
    Seguir_Usuario("benKenobi","chaiko");
    Seguir_Usuario("benKenobi","nicoleneu");
    Seguir_Usuario("benKenobi","lospimpi");
    Seguir_Usuario("benKenobi","alcides");
    Seguir_Usuario("benKenobi","el_padrino");
    Seguir_Usuario("benKenobi","ppArgento");
    Seguir_Usuario("benKenobi","lachiqui");
    Seguir_Usuario("benKenobi","cbochinche");
    Seguir_Usuario("benKenobi","Eleven11");
    Seguir_Usuario("lachiqui","bruceTheBoss");
    Seguir_Usuario("lachiqui","la_ley");
    Seguir_Usuario("lachiqui","lospimpi");
    Seguir_Usuario("lachiqui","alcides");
    Seguir_Usuario("lachiqui","el_padrino");
    Seguir_Usuario("lachiqui","scarlettO");
    Seguir_Usuario("lachiqui","ppArgento");
    Seguir_Usuario("cbochinche","la_ley");
    Seguir_Usuario("cbochinche","lospimpi");
    Seguir_Usuario("cbochinche","dyangounchained");
    Seguir_Usuario("cbochinche","alcides");
    Seguir_Usuario("cbochinche","ppArgento");
    Seguir_Usuario("Eleven11","la_ley");
    Seguir_Usuario("Eleven11","el_padrino");
    Seguir_Usuario("Eleven11","scarlettO");
    Seguir_Usuario("Eleven11","ppArgento");
    
    
    //Preferencias (favoritos)
    Album album;
    Tema tema;
    ListaReproduccion lista;
    Cliente cliente;
    
    //VC-> el_padrino 
    //album
    album  = getAlbum("Violator");
    AgregarAlbumFavoritoCliente("el_padrino",album);
    //cliente = ObtenerCliente("el_padrino");
    album  = getAlbum("El Lago De Los Cisnes");
    AgregarAlbumFavoritoCliente("el_padrino",album);
    album  = getAlbum("Concierto Para Piano No. 1 En Si Menor, Opus 23");
    AgregarAlbumFavoritoCliente("el_padrino",album);

    tema = getTema("El Duelo");
    AgregarTemaFavoritoCliente("el_padrino",tema);

    lista = getLista("Noche De La Nostalgia");
    AgregarListaFavoritaCliente("el_padrino", lista);
    lista = getLista("Rock En Español");
    AgregarListaFavoritaCliente("el_padrino", lista);
    
    
    //scarlettO
    lista = getLista("Música Clásica");
    AgregarListaFavoritaCliente("scarlettO", lista);
    
    //ppArgento
    tema = getTema("Adagio De Mi País");
    AgregarTemaFavoritoCliente("ppArgento",tema);
    
    lista = getLista("Noche De La Nostalgia");
    AgregarListaFavoritaCliente("ppArgento", lista);
    lista = getLista("Rock En Español");
    AgregarListaFavoritaCliente("ppArgento", lista);
    
    //Heisenberg
    lista = getLista("Música Inspiradora");
    AgregarListaFavoritaCliente("Heisenberg", lista);
    
    //benKenobi
    album  = getAlbum("El Lago De Los Cisnes");
    AgregarAlbumFavoritoCliente("benKenobi",album);
    album  = getAlbum("Concierto Para Piano No. 1 En Si Menor, Opus 23");
    AgregarAlbumFavoritoCliente("benKenobi",album);
    
    //lachiqui
    
    //cbochinche
    tema = getTema("Primer Movimiento (Allegro non troppo e molto maestoso – Allegro con spirito)");
    AgregarTemaFavoritoCliente("cbochinche",tema);
    
    lista = getLista("Noche De La Nostalgia");
    AgregarListaFavoritaCliente("cbochinche", lista);
    lista = getLista("Rock En Español");
    AgregarListaFavoritaCliente("cbochinche", lista);
    
    album  = getAlbum("Hay Amores Que Matan");
    AgregarAlbumFavoritoCliente("cbochinche",album);
    
    //Eleven11
    tema = getTema("No Quiero Estudiar");
    AgregarTemaFavoritoCliente("Eleven11",tema);
    
    
    }
   
    public Album getAlbum(String nombreA){
        Album album = cpu.obtenerAlbumPorNombre(nombreA);
        return album;
    }
    
    public Tema getTema(String nombreT){
        Tema tema = cpu.obtenerTemaPorNombre(nombreT);
        return tema;
    }
    
    public ListaReproduccion getLista(String nombreL){
        ListaReproduccion lista = cpu.obtenerListaPorNombre(nombreL);
        return lista;
    }
   
    public boolean Encontrar_Lista_Por_Defecto(String nombre){
           List<ListaPorDefecto> listas = cpu.Listar_Listas_Por_Defecto();
           for(int i = 0; i < listas.size();i++){
               if(listas.get(i).getNombre().equals(nombre)){
                   return true;
               }
           }
           return false;
    }
   public boolean Encontrar_Lista_Particular(String nombre_cliente,String nombre_lista){
           List<ListaParticular> listas = cpu.Listar_Listas_Particulares();
           for(int i = 0; i < listas.size();i++){
               if(listas.get(i).getNombre().equals(nombre_lista)){
                   if(listas.get(i).getCliente().equals(nombre_cliente)){
                       return true;
                   }
               }
           }
           return false;
    }
   
   //consultaalbum
   
   public DataAlbum[] getAlbumsByArtista(String artista) throws AlbumNoExisteException {
        List<Album> albums = cpu.obtenerAlbumPorArtista(artista);
        if (!albums.isEmpty()) {
            DataAlbum[] dataAlbums = new DataAlbum[albums.size()];
            for (int i = 0; i < albums.size(); i++) {
                dataAlbums[i] = albums.get(i).devolverData();
                System.out.println(dataAlbums[i]);
            }
            return dataAlbums;
        } else {
            throw new AlbumNoExisteException("No existen álbumes para el artista seleccionado.");
        }
    }
   
    //
    public void AgregarListaFavoritaCliente(String nickname, ListaReproduccion lista){
        Cliente cliente = cpu.obtenerCliente(nickname);
        cliente.agregarListaFavorita(lista);
        cpu.actualizarUsuario(cliente);  
    }
    
    public void AgregarAlbumFavoritoCliente(String nickname, Album album){
        Cliente cliente = cpu.obtenerCliente(nickname);
        cliente.agregarAlbumFavorito(album);
        cpu.actualizarUsuario(cliente);   
    }
    
    public void AgregarTemaFavoritoCliente(String nickname, Tema tema){
        Cliente cliente = cpu.obtenerCliente(nickname);
        cliente.agregarTemaFavorito(tema);
        cpu.actualizarUsuario(cliente);
    }
    
    // Favoritos / Preferencias / 

}



