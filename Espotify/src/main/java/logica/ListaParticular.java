
package logica;

import jakarta.persistence.Entity;

/**
 *
 * @author frank
 */
@Entity
public class ListaParticular extends ListaReproduccion {
    private String cliente;
    private boolean publica;

    public ListaParticular() {
    }
    
    
    public ListaParticular(String nombre, String imagen, String cli) {
        super(nombre, imagen);
        cliente=cli;
        publica=false;
    }
    
    //Getters y Setters

    public String getCliente() {
        return cliente;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public boolean esPublica() {
        return publica;
    }

    public void setPublica(boolean estado) {
        this.publica = estado;
    }
    
    
}
