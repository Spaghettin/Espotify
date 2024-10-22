package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import logica.Album;
import logica.Artista;
import logica.Genero;

public class AlbumJpaController implements Serializable {

    public AlbumJpaController(){
        emf = Persistence.createEntityManagerFactory("ServidorPU");
    }
    
    private EntityManagerFactory emf = null;

    public AlbumJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para crear un nuevo álbum
    public void create(Album album) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para editar un álbum existente
    public void edit(Album album) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            album = em.merge(album);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new Exception("Error al editar el álbum: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para eliminar un álbum por su ID
    public void destroy(String nombre) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album album;
            try {
                album = em.getReference(Album.class, nombre);
            } catch (EntityNotFoundException enfe) {
                throw new Exception("El álbum con el nombre: " + nombre + " no existe.", enfe);
            }
            em.remove(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para encontrar un álbum por su nombre
    public Album findAlbum(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Album.class, nombre);
        } finally {
            em.close();
        }
    }
    
   public void detach(Album album) {
        EntityManager em = getEntityManager();
        try {
            em.detach(album); 
        } finally {
            em.close(); 
        }
    }
    

    // Método para listar todos los álbumes
    public List<Album> findAlbumEntities(Artista usr) {
        return findAlbumEntities(usr, true, -1, -1);
    }

    public List<Album> findAlbumEntities(Artista usr, int maxResults, int firstResult) {
        return findAlbumEntities(usr, false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(Artista usuario, boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
        // Crear el CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);
        
        // Root de Artista
        Root<Artista> artistaRoot = cq.from(Artista.class);
        
        // Realizar un join con la lista de álbumes
        Join<Artista, Album> albumesJoin = artistaRoot.join("Albumes");
        
        // Comparar una propiedad específica del artista, como nickname o id
        cq.select(albumesJoin).where(cb.equal(artistaRoot.get("nickname"), usuario.getNickname()));
        
        // Crear la consulta
        Query q = em.createQuery(cq);
        
        // Establecer límites si no queremos traer todos los resultados
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        
        // Obtener la lista de álbumes
        return q.getResultList();
    } finally {
        em.close();
    }
}
    public List<Album> obtenerNombresAlbumesPorGenero(String nombreGenero) {
        EntityManager em = getEntityManager();
        String jpql = "SELECT a FROM Album a JOIN a.Generos g WHERE g.nombre = :nombreGenero";
        return em.createQuery(jpql, Album.class)
                            .setParameter("nombreGenero", nombreGenero)
                            .getResultList();
    }
    
    public List<Album> findAlbumEntities(Genero gen) {
        return findAlbumEntities(gen, true, -1, -1);
    }

    public List<Album> findAlbumEntities(Genero gen, int maxResults, int firstResult) {
        return findAlbumEntities(gen, false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(Genero gen, boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);
        
        Root<Genero> genRoot = cq.from(Genero.class);
        
        Join<Genero, Album> albumesJoin = genRoot.join("albumes");
        
        cq.select(albumesJoin).where(cb.equal(genRoot, gen));
        
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    } finally {
        em.close();
    }
}
    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Album.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    

    // Método para contar la cantidad de álbumes
    public int getAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Album> rt = cq.from(Album.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Album> findAllAlbumes() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Album c", Album.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Album> findAllAlbumesAjax(String s) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Album c WHERE c.nombre LIKE :nombre", Album.class);
            query.setParameter("nombre", s + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    //Consulta Album
    public List<Album> obtenerNombresAlbumesPorArtista(String artistaNickname) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM Album a WHERE a.artista.nickname = :nickname", Album.class)
                    .setParameter("nickname", artistaNickname)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
    //preferencias
    public Album findAlbumNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            // Utilizamos una consulta JPQL para buscar el álbum por nombre
            TypedQuery<Album> query = em.createQuery("SELECT a FROM Album a WHERE a.nombre = :nombre", Album.class);
            query.setParameter("nombre", nombre);
            return query.getSingleResult();  // Retorna el álbum encontrado
        } catch (NoResultException e) {
            return null; // En caso de no encontrar el álbum, retornamos null
        } finally {
            em.close();
        }
    }
    
}