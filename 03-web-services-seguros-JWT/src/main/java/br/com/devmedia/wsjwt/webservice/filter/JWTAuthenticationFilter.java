package br.com.devmedia.wsjwt.webservice.filter;

import br.com.devmedia.wsjwt.webservice.exception.UnauthenticatedException;
import br.com.devmedia.wsjwt.webservice.jwt.JwtSecurityContext;
import br.com.devmedia.wsjwt.webservice.jwt.KeyGenerator;
import br.com.devmedia.wsjwt.webservice.jwt.TokenJWTUtil;
import br.com.devmedia.wsjwt.webservice.jwt.UserDetalhes;
import br.com.devmedia.wsjwt.webservice.resource.LoginJWTResource;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.List;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter{

    private KeyGenerator keyGenerator = new KeyGenerator();
    @Context
    private UriInfo uriInfo;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //recuperando o cabecario da requisição
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.contains("Bearer ")){
            String token = authorizationHeader.substring("Bearer".length()).trim();
            System.out.println(token);

            Key key = keyGenerator.generateKey();

            if(TokenJWTUtil.tokenValido(token,key)){
                String nome=TokenJWTUtil.recuperarNome(token,key);
                List<String> regras = TokenJWTUtil.recupearRoles(token,key);

                UserDetalhes userDetalhes = new UserDetalhes(nome, regras);
                //recupera a forma de segurança false - http  true--https
                boolean secure = requestContext.getSecurityContext().isSecure();
                requestContext.setSecurityContext(new JwtSecurityContext(userDetalhes, secure));
                return;
            }

        }else if(acessoParaLoginNaAPI(requestContext)){
            return;
        }else if(acessoParaMetodosDeConsulta(requestContext)){
            return;
        }
        throw  new UnauthenticatedException("Token invalido/expirado ou usuario não autenticado!");

    }
    private boolean acessoParaLoginNaAPI(ContainerRequestContext requestContext) {
        return requestContext.getUriInfo().getAbsolutePath().toString()
                .equals(uriInfo.getBaseUriBuilder().path
                        (LoginJWTResource.class).build().toString());
    }


    private boolean acessoParaMetodosDeConsulta(ContainerRequestContext requestContext) {
        return "GET".equalsIgnoreCase(requestContext.getMethod());
    }



}
