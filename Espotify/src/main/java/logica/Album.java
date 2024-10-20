package logica;
import datatypes.DataAlbum;
import datatypes.DataTema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



@Entity
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Se usa un ID autogenerado
    private Long id;
    
    private String nombre ;
    @Basic
    private Integer fechaCreacion;
    private String ImagenAlbum;

    @ManyToOne
    @JoinColumn(name = "artista_nombre")
    private Artista artista;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tema> Temas = new ArrayList<>();

    @ManyToMany//(cascade = CascadeType.PERSIST)
    @JoinTable(
    name = "ALBUM_GENERO", // Nombre de la tabla intermedia
    joinColumns = @JoinColumn(name = "album_id"), // Columna que hace referencia a Album
    inverseJoinColumns = @JoinColumn(name = "genero_nombre")) // Columna que hace referencia a Genero
    private List<Genero> Generos = new ArrayList<>();

    public Album() {
        this.nombre = "nuevoAlbum";
    }

    public Album(String nombre, Integer fechaCreacion, String imagen) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.ImagenAlbum = imagen;
        
    }

    // Constructor por copia
    public Album(Album otroAlbum) {
        this.nombre = otroAlbum.nombre;
        this.fechaCreacion = otroAlbum.fechaCreacion;
        this.ImagenAlbum = otroAlbum.ImagenAlbum;
        this.artista = otroAlbum.artista;
       
        this.Temas = otroAlbum.Temas;

        for (Tema tema : Temas){
            tema.setAlbum(this);
        }
        this.Generos = otroAlbum.Generos;   
    }

    public void copia(Album otroAlbum) {
        this.nombre = otroAlbum.nombre;
        this.fechaCreacion = otroAlbum.fechaCreacion;
        this.ImagenAlbum = otroAlbum.ImagenAlbum;
        this.artista = otroAlbum.artista;
       
        this.Temas = otroAlbum.Temas;

        for (Tema tema : Temas){
            tema.setAlbum(this);
        }
        this.Generos = otroAlbum.Generos;   
    }
    
    /////
    public void limpiarAlbum() {
        this.id = null;
        this.nombre = null; 
        this.fechaCreacion = null; 
        this.ImagenAlbum = null; 
        this.artista = null;
        
        if (this.Temas != null) {
            Iterator<Tema> it = this.Temas.iterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
        
        if (this.Generos != null) {
            Iterator<Genero> it = this.Generos.iterator();
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaCreacion() {
        return fechaCreacion;
    }

    public Long getId(){
        return id;
    }
    
    public void setFechaCreacion(Integer fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getImagenAlbum() {
        return ImagenAlbum;
    }

    public void setImagenAlbum(String ImagenAlbum) {
        this.ImagenAlbum = ImagenAlbum;
    }
    
     public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
    
    public List<Tema> getTemas() {
        return this.Temas;
    }
    
    public void setTemas(List<Tema> temas){
        this.Temas = temas;
    }
    
    public List<Genero> getGeneros() {
        return this.Generos;
    }
    
   public void addTema(Tema tema){
        this.Temas.add(tema);
    }
    
   public void addGenero(Genero genero){
        this.Generos.add(genero);
    }
   
   public Tema altaTema(String nombre, String duracion, Integer pos, String direccionWeb, String archivo){
        Tema nuevoTema = new Tema(nombre, duracion, pos, direccionWeb, archivo);
        this.addTema(nuevoTema);
        return nuevoTema;
    }
   
   public Tema getTema(String nombreTema){
       Tema temae = null;
       for (Tema tema : Temas){
           if(tema.getNombre().equals(nombreTema)){
               temae = tema;
           }
       }
       return temae;
   }
    public DataAlbum getDataAlbum(){
        Collection<DataTema> temas =  new ArrayList<>();
        Collection<String> generos =  new ArrayList<>();
        DataTema dtTema = null;
        
        for (Tema entry : this.Temas){
            dtTema = entry.getDataTema();
            temas.add(dtTema);
        }
        
        for (Genero entry : this.Generos){
            generos.add(entry.getNombre());
        }
       
        DataAlbum dt_alb = new DataAlbum(getId(), getNombre(), getFechaCreacion(), getImagenAlbum(), temas, generos, artista.getNickname());
    return dt_alb;
    }
 
    public DataAlbum devolverData(){
        Collection<DataTema> dataTemas = new ArrayList<>();
    for (Tema tema : getTemas()) {
        dataTemas.add(tema.getDataTemaI()); 
    }

    Collection<String> generos = new ArrayList<>();
    for (Genero genero : getGeneros()) {
        generos.add(genero.getNombre());
    }

    return new DataAlbum(getNombre(), getFechaCreacion(), getImagenAlbum(), dataTemas, generos, artista.getNickname());
    }
    
}
