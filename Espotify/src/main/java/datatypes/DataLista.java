/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datatypes;

import java.util.HashSet;
import java.util.Set;
import logica.ListaParticular;
import logica.ListaPorDefecto;
import logica.Tema;

/**
 *
 * @author frank
 */
public class DataLista {
    private int id;
    private String nombre,imagen,extra;
    private Set<DataTema> temas;
    private Set<String> temass;
    private String cliente;
    private boolean publica;

    public DataLista(ListaPorDefecto ld) {
        this.id=ld.getId();
        this.nombre = ld.getNombre();
        this.imagen = ld.getImagen();
        this.extra = ld.getGenero();
        this.temas = new HashSet<>();
        for(Tema t: ld.getListaTemas()){
            temas.add(new DataTema(t));
        }
    }
    
    public DataLista(ListaParticular ld){
        this.id=ld.getId();
        this.nombre = ld.getNombre();
        this.imagen = ld.getImagen();
        this.extra = ld.getCliente();
        this.temas = new HashSet<>();
        for(Tema t: ld.getListaTemas()){
            temas.add(new DataTema(t));
        }
        this.cliente = ld.getCliente();
        this.publica = ld.esPublica();
    }
    
    public DataLista(String nom, String ima, String extra){
        this.nombre = nom;
        this.imagen = ima;
        this.extra = extra;
        this.temas = new HashSet<>();
    }
    
    public DataLista(){
        
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Set<DataTema> getTemas() {
        return temas;
    }
    
     public String toString(){
        return nombre;
}
    
    public void eliminarTema(Long idTema) {
    temas.removeIf(temas -> temas.getId() == idTema);
    }
}