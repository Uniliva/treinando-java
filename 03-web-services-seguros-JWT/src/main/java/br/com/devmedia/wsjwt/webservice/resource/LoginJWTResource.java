package br.com.devmedia.wsjwt.webservice.resource;

import br.com.devmedia.wsjwt.domain.Usuario;
import br.com.devmedia.wsjwt.service.UsuarioService;
import br.com.devmedia.wsjwt.webservice.exception.UnauthenticatedException;
import br.com.devmedia.wsjwt.webservice.jwt.JwtSecurityContext;
import br.com.devmedia.wsjwt.webservice.jwt.TokenJWTUtil;
import br.com.devmedia.wsjwt.webservice.jwt.UserDetalhes;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginJWTResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response autenticarUsuario(@FormParam("login") String login, @FormParam("password") String password) {
        Usuario usuario = ValidarCredenciais(login, password);
        String token = TokenJWTUtil.gerarToken(usuario.getNome(), usuario.recuperarRoles());

        return Response.ok().header("Authorization", "Bearer" + token).build();
    }

    //serve para revalidar o token, atraves de uma solicitação do cliente
    @POST
    @Path("/refresh")
    public Response atualizaToken(@Context ContainerRequestContext requestContext) {
        JwtSecurityContext securityContext = (JwtSecurityContext) requestContext.getSecurityContext();
        UserDetalhes userDetalhes = (UserDetalhes) securityContext.getUserPrincipal();
        String token = TokenJWTUtil.gerarToken(userDetalhes.getName(), userDetalhes.getRoles());
        return Response.ok().header("Authorization", "Bearer" + token).build();
    }

    private Usuario ValidarCredenciais(String login, String password) {
        UsuarioService usuarioService = new UsuarioService();

        Usuario usuario = usuarioService.recuperarUsuarioComLoginESenha(login, password);

        if (usuario == null)
            throw new UnauthenticatedException("Usuario não autenticado: nome do usuario ou senha invalidos!");

        return usuario;
    }

}
