package br.com.devmedia.webservice.resources.filter;

import br.com.devmedia.webservice.domain.ErrorMessage;
import br.com.devmedia.webservice.domain.Usuario;
import br.com.devmedia.webservice.service.UsuarioService;
import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

@Provider
@AcessoRestrito
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_AUTHORIZATION_PREFIX = "Basic ";

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> headersAutorizacao = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
        if((headersAutorizacao != null) && (headersAutorizacao.size() > 0)) {
            String dadosAutorizacao = headersAutorizacao.get(0);
            Usuario usuarioDoHeader = obterUsuarioDoHeader(dadosAutorizacao);

            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuarioDoHeader);
            if (usuarioAutenticado != null) {
                return;
            }
        }

        Response naoAutorizado = Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage("Usuário não autenticado. Verifique os dados de login e senha.",
                        Response.Status.UNAUTHORIZED.getStatusCode()))
                .build();

        requestContext.abortWith(naoAutorizado);
    }

    private Usuario obterUsuarioDoHeader(String dadosAutorizacao) {
        dadosAutorizacao = dadosAutorizacao.replaceFirst(BASIC_AUTHORIZATION_PREFIX, "");
        String dadosDecodificados = Base64.decodeAsString(dadosAutorizacao);
        StringTokenizer dadosTokenizer = new StringTokenizer(dadosDecodificados,":");
        Usuario usuario = new Usuario();
        usuario.setUsername(dadosTokenizer.nextToken());
        usuario.setPassword(dadosTokenizer.nextToken());
        return usuario;
    }

}
