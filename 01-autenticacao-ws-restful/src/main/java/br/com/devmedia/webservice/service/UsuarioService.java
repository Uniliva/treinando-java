package br.com.devmedia.webservice.service;

import br.com.devmedia.webservice.dao.UsuarioDao;
import br.com.devmedia.webservice.domain.Usuario;

import javax.persistence.NoResultException;

public class UsuarioService {
    private final UsuarioDao dao= new UsuarioDao();

    public boolean validarUsuario(Usuario usuario){
        try{
            dao.obterUsuario(usuario);
        }catch (NoResultException e){
            return false;
        }
        return true;
    }

}
