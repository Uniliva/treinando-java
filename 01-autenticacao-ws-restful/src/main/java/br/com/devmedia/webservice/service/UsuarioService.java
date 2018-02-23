package br.com.devmedia.webservice.service;



import br.com.devmedia.webservice.dao.UsuarioDao;
import br.com.devmedia.webservice.domain.Usuario;

import javax.persistence.NoResultException;

public class UsuarioService {

    private final UsuarioDao dao =  new UsuarioDao();

    public boolean validarUsuario(Usuario usuario) {
        System.out.println(usuario.getUsername()  +"  "+usuario.getPassword());
        try {
            Usuario x = dao.obterUsuario(usuario);
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

}
