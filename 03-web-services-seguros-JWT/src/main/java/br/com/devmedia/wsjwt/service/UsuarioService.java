package br.com.devmedia.wsjwt.service;

import br.com.devmedia.wsjwt.dao.UsuarioDAO;
import br.com.devmedia.wsjwt.domain.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario recuperarUsuarioComLoginESenha(String usuario, String password) {
        return usuarioDAO.recuperarUsuarioPorUsernameEPassword(usuario, password);
    }

}
