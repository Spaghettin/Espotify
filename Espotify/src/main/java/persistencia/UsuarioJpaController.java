/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import logica.Usuario;

/**
 *
 * @author dokgo
 */
public class UsuarioJpaController implements Serializable {
    
    public UsuarioJpaController(){
        emf = Persistence.createEntityManagerFactory("ServidorPU");
    }
    
    private EntityManagerFactory emf = null;

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String email) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, email);
                usuario.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new Exception("El usuario con email " + email + " no existe.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Usuario findUsuario(String email) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, email);
        } finally {
            em.close();
        }
    }
    
    public List<Usuario> findAllUsers() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Usuario c", Usuario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Usuario findUsuarioByNickname(String nickname) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :nickname");
        query.setParameter("nickname", nickname);
        return (Usuario) query.getSingleResult();
    } catch (NoResultException e) {
        return null;
    } finally {
        em.close();
    }
    }
    
    public Usuario findUsuarioByEmail(String email) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
        query.setParameter("email", email);
        return (Usuario) query.getSingleResult();
    } catch (NoResultException e) {
        return null;
    } finally {
        em.close();
        }
    }
    
    public boolean existeEmail(String email) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email");
        query.setParameter("email", email);
        Long count = (Long) query.getSingleResult();
        return count > 0; 
    } catch (NoResultException e) {
        return false;
    } finally {
        em.close();
    }
}

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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
    
    public List<Usuario> findAllUsuariosAjax(String s) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname LIKE :nickname", Usuario.class);
            query.setParameter("nickname", s + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Usuario> findUsuarioByTipoCliente() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> clientes= new ArrayList();

        try {
            em.getTransaction().begin();
            
            // Consulta para obtener solo los usuarios de tipo "cliente"
            clientes = em.createQuery("SELECT u FROM Usuario u WHERE u.tipo = 'cliente'", Usuario.class)
                         .getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return clientes;
    }
}