package br.com.devmedia.wsjwt;

import br.com.devmedia.wsjwt.dao.JPAUtil;
import br.com.devmedia.wsjwt.dao.UsuarioDAO;
import br.com.devmedia.wsjwt.domain.Usuario;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String args[]) {
        UsuarioDAO dao = new UsuarioDAO();

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        dao.salvarUsuario(new Usuario("Eduardo", "eduardo", "123456"));
        em.getTransaction().commit();
        em.close();
    }

}
