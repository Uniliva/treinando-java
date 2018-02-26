package br.com.devmedia.webservice.resources;

import br.com.devmedia.webservice.domain.ErrorMessage;
import br.com.devmedia.webservice.domain.Tipo;
import br.com.devmedia.webservice.domain.Usuario;
import br.com.devmedia.webservice.resources.filter.AcessoRestrito;
import br.com.devmedia.webservice.service.UsuarioService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UsuarioResource {

    private final UsuarioService usuarioService = new UsuarioService();

    @POST
    public Response cadastrarUsuario(Usuario usuario) {
        usuario = usuarioService.saveUsuario(usuario);
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage("Apenas usuários do tipo Cliente podem ser criados.",
                            Response.Status.BAD_REQUEST.getStatusCode()))
                    .build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(usuario)
                .build();
    }

    @GET
    @AcessoRestrito({Tipo.FUNCIONARIO, Tipo.ADMINISTRADOR})
    public List<Usuario> recuperarUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GET
    @Path("{usuarioId}")
    @AcessoRestrito
    public Usuario recuperarUsuario(@PathParam("usuarioId") long id) {
        return usuarioService.getUsuario(id);
    }

    @PUT
    @Path("{usuarioId}")
    @AcessoRestrito({Tipo.FUNCIONARIO, Tipo.ADMINISTRADOR})
    public Response atualizarUsuario(@PathParam("usuarioId") long id, Usuario usuario) {
        usuarioService.updateUsuario(usuario, id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{usuarioId}")
    @AcessoRestrito({Tipo.ADMINISTRADOR})
    public Response removerUsuario(@PathParam("usuarioId") long id) {
        usuarioService.deleteUsuario(id);
        return Response.noContent().build();
    }

    @Path("{usuarioId}/imoveis")
    public ImovelResource obterImovelResource() {
        return new ImovelResource();
    }

}
