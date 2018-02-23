package br.com.devmedia.webservice.dao;

import br.com.devmedia.webservice.domain.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class UsuarioDAO {

    public Usuario recuperarUsuarioPorUsernameEPassword(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password", Usuario.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return usuario;
    }

    public List<Usuario> selecionarUsuarios() {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    public Usuario recuperarUsuarioPorId(long id) {
        EntityManager em = JPAUtil.getEntityManager();

        return em.find(Usuario.class, id);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usuarioManaged;

        try {
            em.getTransaction().begin();
            usuarioManaged = em.find(Usuario.class, usuario.getId());
            usuarioManaged.setNome(usuario.getNome());
            usuarioManaged.setPassword(usuario.getPassword());
            usuarioManaged.setTipo(usuario.getTipo());
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return usuario;
    }

    public void excluirUsuario(long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Usuario.class, id));
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
