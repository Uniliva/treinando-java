package br.com.devmedia.webservice.resources.filtro;

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
@AcessoRestiro
public class AutenticationFilter implements ContainerRequestFilter {
    private final String BASIC_AUTHORIZATION_PREFIX = "Basic ";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private UsuarioService service = new UsuarioService();

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {


        List<String> headersAutorization = containerRequestContext.getHeaders().get(AUTHORIZATION_HEADER);
        if (headersAutorization != null && headersAutorization.size() > 0) {
            //pegando apenas string codigicada
            String dadosAutorizacao = headersAutorization.get(0);
            dadosAutorizacao = dadosAutorizacao.replaceFirst(BASIC_AUTHORIZATION_PREFIX, "");
            Usuario usuario = getUsuario(headersAutorization, dadosAutorizacao);


            if (service.validarUsuario(usuario)) {
                return;
            }


        }

        Response naoAutalizado = Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage("Usuario não autorizado!",
                        Response.Status.UNAUTHORIZED.getStatusCode()))
                .build();

        containerRequestContext.abortWith(naoAutalizado);

    }

    private Usuario getUsuario(List<String> headersAutorization, String dadosAutorizacao) {
        //decodificando string
        String dadosdecodificados = Base64.decodeAsString(dadosAutorizacao);
        //pegando o usuario e a senha, dando um  split
        System.out.println(headersAutorization);
        StringTokenizer tokenizer = new StringTokenizer(dadosdecodificados, ":");
        Usuario usuario = new Usuario();
        usuario.setUsername(tokenizer.nextToken());
        usuario.setPassword(tokenizer.nextToken());
        return usuario;
    }
}
