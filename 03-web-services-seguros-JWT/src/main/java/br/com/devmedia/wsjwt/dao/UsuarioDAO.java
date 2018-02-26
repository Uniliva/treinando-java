package br.com.devmedia.wsjwt.dao;

import br.com.devmedia.wsjwt.domain.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDAO {

    public Usuario recuperarUsuarioPorUsernameEPassword(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password", Usuario.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
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

}
