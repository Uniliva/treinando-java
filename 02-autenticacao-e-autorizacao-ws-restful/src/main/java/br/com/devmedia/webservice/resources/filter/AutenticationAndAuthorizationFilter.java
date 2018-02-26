package br.com.devmedia.webservice.resources.filter;

import br.com.devmedia.webservice.domain.ErrorMessage;
import br.com.devmedia.webservice.domain.Tipo;
import br.com.devmedia.webservice.domain.Usuario;
import br.com.devmedia.webservice.service.UsuarioService;
import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

@Provider
@AcessoRestrito
public class AutenticationAndAuthorizationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_AUTHORIZATION_PREFIX = "Basic ";

    private UsuarioService usuarioService = new UsuarioService();

    // é uma interface da API JAX-RS que nos permite recuperar a classe e o método do recurso solicitado na requisição. Logo vamos utilizá-lo
    @Context
    private ResourceInfo resourceInfo;
    /*@Context, estamos informando ao Jersey que ele deve injetar a instância de ResourceInfo nesse atributo. Em outras palavras, com esse código
    já temos como saber a classe do recurso e o método que deve ser executado para atender à requisição que chegou ao servidor.*/

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> headersAutorizacao = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
        if ((headersAutorizacao != null) && (headersAutorizacao.size() > 0)) {
            String dadosAutorizacao = headersAutorizacao.get(0);
            Usuario usuarioDoHeader = obterUsuarioDoHeader(dadosAutorizacao);

            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuarioDoHeader);
            if (usuarioAutenticado != null) {
                autorizarUsuario(requestContext, usuarioAutenticado);
                return;
            }
        }

        Response naoAutorizado = Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage("Usuário não autenticado. Verifique os dados de login e senha.",
                        Response.Status.UNAUTHORIZED.getStatusCode()))
                .build();

        requestContext.abortWith(naoAutorizado);

    }

    private void autorizarUsuario(ContainerRequestContext requestContext, Usuario usuarioAutenticado) {
        //recuperar a classe do recurso que esta sendo acesado
        Class<?> classeDoRecurso = resourceInfo.getResourceClass();
        //pegas os tipos de permissoes que a classe pode ter
        List<Tipo> permissoesDoRecurso = recuperaPermissoes(classeDoRecurso);

        //recupera o metodo que esta sendo utilizado
        Method metodoRecurso = resourceInfo.getResourceMethod();
        //lista os tipo de permissoes que o methodo tem
        List<Tipo> permissoesDoMetodo = recuperaPermissoes(metodoRecurso);

        try {
            if (permissoesDoRecurso.isEmpty()) {
                verificaPermissoes(permissoesDoRecurso, requestContext, usuarioAutenticado);
            } else {
                verificaPermissoes(permissoesDoMetodo, requestContext, usuarioAutenticado);
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage("Usuário não tem permissão para executar essa função.",
                            Response.Status.FORBIDDEN.getStatusCode())).build());

        }
    }

    private void verificaPermissoes(List<Tipo> permissoes, ContainerRequestContext requestContext, Usuario usuario) throws Exception {
        if (permissoes.contains(usuario.getTipo())) {

            long idUsuarioAcesado = recuperaIDdaUrl(requestContext);

            if (Tipo.CLIENTE.equals(usuario.getTipo()) && (usuario.getId() == idUsuarioAcesado)) {
                return;
            } else if (!Tipo.CLIENTE.equals(usuario)) {
                return;

            }

        }
        throw new Exception();
    }

    private long recuperaIDdaUrl(ContainerRequestContext requestContext) {
        String idObtidoDaUrl = requestContext.getUriInfo().getPathParameters().getFirst("usuarioId");
        if(idObtidoDaUrl == null){
            return 0;
        }else{
            return Long.parseLong(idObtidoDaUrl);
        }

    }

    private List<Tipo> recuperaPermissoes(AnnotatedElement elementoAnotado) {
        //recupera a anotação
        AcessoRestrito acessoRestrito = elementoAnotado.getAnnotation(AcessoRestrito.class);
        if (acessoRestrito == null) {
            //retona um lista vazia
            return new ArrayList<Tipo>();
        } else {
            //recupera todos os valores da anotação
            Tipo[] permissoes = acessoRestrito.value();
            return Arrays.asList(permissoes);

        }
    }

    private Usuario obterUsuarioDoHeader(String dadosAutorizacao) {
        dadosAutorizacao = dadosAutorizacao.replaceFirst(BASIC_AUTHORIZATION_PREFIX, "");
        String dadosDecodificados = Base64.decodeAsString(dadosAutorizacao);
        StringTokenizer dadosTokenizer = new StringTokenizer(dadosDecodificados, ":");
        Usuario usuario = new Usuario();
        usuario.setUsername(dadosTokenizer.nextToken());
        usuario.setPassword(dadosTokenizer.nextToken());
        return usuario;
    }

}
