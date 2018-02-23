package br.com.devmedia.webservice.dao;

import br.com.devmedia.webservice.domain.Usuario;

import javax.persistence.EntityManager;

public class UsuarioDao {
    public Usuario obterUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
       return  em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password", Usuario.class)
                .setParameter("username", usuario.getUsername())
                .setParameter("password", usuario.getPassword())
                .getSingleResult();
    }


}
