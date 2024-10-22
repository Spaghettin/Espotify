/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import logica.ListaParticular;
import logica.ListaPorDefecto;
import logica.ListaReproduccion;
import logica.Tema;

/**
 *
 * @author frank
 */
public class ListaReproduccionJpaController implements Serializable {
    
    public ListaReproduccionJpaController(){
        emf = Persistence.createEntityManagerFactory("ServidorPU");
        ldj = new ListaPorDefectoJpaController();
        lpj = new ListaParticularJpaController();
    }
    
    private EntityManagerFactory emf = null;
    private ListaPorDefectoJpaController ldj = null;
    private ListaParticularJpaController lpj = null;

    public ListaReproduccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createListaDef(ListaPorDefecto lr) {
        ldj.create(lr);
    }
    
    public void createListaPar(ListaParticular lr){
        lpj.create(lr);
    }

    public void edit(ListaReproduccion lr) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lr = em.merge(lr);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String nombre) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaReproduccion lr;
            try {
                lr = em.getReference(ListaReproduccion.class, nombre);
            } catch (EntityNotFoundException enfe) {
                throw new Exception("No existe una lista con ese nombre", enfe);
            }
            em.remove(lr);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public ListaReproduccion findLista(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaReproduccion.class, nombre);
        } finally {
            em.close();
        }
    }
    
    public ListaReproduccion findListaReproduccionByName(String nombre) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery("SELECT lr FROM ListaReproduccion lr WHERE lr.nombre = :nombre", ListaReproduccion.class)
                 .setParameter("nombre", nombre)
                 .getSingleResult();
    } catch (NoResultException e) {
        return null; // Retorna null si no se encuentra la lista
    } finally {
        em.close();
    }
}

    
    public ListaReproduccion findListaById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaReproduccion.class, id);
        } finally {
            em.close();
        }
    }


    public List<ListaReproduccion> findListaEntities() {
        return findListaEntities(true, -1, -1);
    }

    public List<ListaReproduccion> findListaEntities(int maxResults, int firstResult) {
        return findListaEntities(false, maxResults, firstResult);
    }

    private List<ListaReproduccion> findListaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaReproduccion.class));
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
    
        public void agregarTemaALista(int listaId, Tema tema) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            // Encuentra la lista de reproducción por su ID
            ListaReproduccion lista = em.find(ListaReproduccion.class, listaId);
            if (lista == null) {
                throw new Exception("Lista de reproducción no encontrada");
            }

            // Agrega el tema a la lista de reproducción
            lista.agregarTema(tema);

            // Persiste el tema (si es nuevo)
            if (tema.getId() == null) {
                em.persist(tema);
            } else {
                em.merge(tema);  // Si el tema ya existe
            }

            em.merge(lista);  // Actualiza la lista de reproducción
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
        
        public List<ListaReproduccion> findAllListasAjax(String s) {
        EntityManager em = getEntityManager();
            try {
                Query query = em.createQuery("SELECT lr FROM ListaReproduccion lr WHERE lr.nombre LIKE :nombre", ListaReproduccion.class);
                query.setParameter("nombre", s + "%");
                return query.getResultList();
            } finally {
                em.close();
            }
        }
    
public Set<Tema> findTemasByListaReproduccionId(int idLista) {
    EntityManager em = getEntityManager();
    try {
        // Obtener la lista de reproducción por su ID
        ListaReproduccion listaReproduccion = em.find(ListaReproduccion.class, idLista);
        
        // Retornar la lista de temas de la lista de reproducción
        return listaReproduccion != null ? listaReproduccion.getListaTemas() : Collections.emptySet();
    } finally {
        em.close();
    }
}
}
