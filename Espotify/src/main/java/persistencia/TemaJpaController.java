package persistencia;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import logica.Album;
import logica.Tema;


public class TemaJpaController implements Serializable {

    public TemaJpaController(){
        emf = Persistence.createEntityManagerFactory("ServidorPU");
    }
    
    private EntityManagerFactory emf = null;

    public TemaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear un nuevo tema
    public void create(Tema tema) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Editar un tema existente
    public void edit(Tema tema) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tema = em.merge(tema);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new Exception("Error al editar el tema: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Eliminar un tema por su ID
    public void destroy(String nombre) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tema tema;
            try {
                tema = em.getReference(Tema.class, nombre);
            } catch (EntityNotFoundException enfe) {
                throw new Exception("El tema con el nombre: " + nombre + " no existe.", enfe);
            }
            em.remove(tema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Encontrar un tema por su ID
    public Tema findTema(String nombreT) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tema.class, nombreT);
        } finally {
            em.close();
        }
    }
    
    public Tema findTema(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tema.class, id);
        } finally {
            em.close();
        }
    }

    // Listar todos los temas
    public List<Tema> findTemaEntities() {
        return findTemaEntities(true, -1, -1);
    }

    public List<Tema> findTemaEntities(int maxResults, int firstResult) {
        return findTemaEntities(false, maxResults, firstResult);
    }

    private List<Tema> findTemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tema.class));
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
    
    public List<Tema> findAllTemasAjax(String s) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT t FROM Tema t WHERE t.nombre LIKE :nombre", Tema.class);
            query.setParameter("nombre", s + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Contar el número de temas
    public int getTemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tema> rt = cq.from(Tema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Tema> findTemasByAlbumId(Long albumId) {
        EntityManager em = getEntityManager();
        try {
            // Consulta JPQL para buscar todos los temas relacionados con un álbum específico
            return em.createQuery("SELECT t FROM Tema t WHERE t.album.id = :albumId", Tema.class)
                     .setParameter("albumId", albumId)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    //preferencias
    public Tema findTemaNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            // Utilizamos una consulta JPQL para buscar el álbum por nombre
            TypedQuery<Tema> query = em.createQuery("SELECT a FROM Tema a WHERE a.nombre = :nombre", Tema.class);
            query.setParameter("nombre", nombre);
            return query.getSingleResult();  // Retorna el álbum encontrado
        } catch (NoResultException e) {
            return null; // En caso de no encontrar el álbum, retornamos null
        } finally {
            em.close();
        }
    }
}