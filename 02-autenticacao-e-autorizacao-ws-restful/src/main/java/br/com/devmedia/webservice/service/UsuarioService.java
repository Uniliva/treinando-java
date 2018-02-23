package br.com.devmedia.webservice.service;

import br.com.devmedia.webservice.dao.UsuarioDAO;
import br.com.devmedia.webservice.domain.Tipo;
import br.com.devmedia.webservice.domain.Usuario;

import javax.persistence.NoResultException;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticarUsuario(Usuario usuario) {
        try {
            usuario = usuarioDAO.recuperarUsuarioPorUsernameEPassword(usuario.getUsername(), usuario.getPassword());
        } catch (NoResultException ex) {
            return null;
        }
        return usuario;
    }

    public Usuario saveUsuario(Usuario usuario) {
        if (Tipo.CLIENTE.equals(usuario.getTipo())) {
            return usuarioDAO.salvarUsuario(usuario);
        }
        return null;
    }

    public Usuario getUsuario(long id) {
        return usuarioDAO.recuperarUsuarioPorId(id);
    }

    public List<Usuario> getUsuarios() {
        return usuarioDAO.selecionarUsuarios();
    }

    public void updateUsuario(Usuario usuario, long id) {
        usuario.setId(id);
        usuarioDAO.atualizarUsuario(usuario);
    }

    public void deleteUsuario(long id) {
        usuarioDAO.excluirUsuario(id);
    }
}
