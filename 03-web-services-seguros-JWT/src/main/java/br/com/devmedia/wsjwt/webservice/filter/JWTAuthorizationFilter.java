package br.com.devmedia.wsjwt.webservice.filter;


import br.com.devmedia.wsjwt.exception.UnauthorizedException;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class JWTAuthorizationFilter implements ContainerRequestFilter {
    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            SecurityContext securityContext = requestContext.getSecurityContext();

            if (verificaPermissoesDoMetodo(securityContext)) {
                return;
            } else if (verificaPermissoesDaClasse(securityContext)) {
                return;
            }
        } catch (Exception e) {
            String usuario = requestContext.getSecurityContext().getUserPrincipal().getName();
            throw new UnauthorizedException("Usuario " + usuario + " não tem autorização para acessar essa funcionalidade");
        }

    }

    private boolean verificaPermissoesDaClasse(SecurityContext securityContext) throws Exception {
        Class classeRecurso = resourceInfo.getResourceClass();
        if (classeRecurso.isAnnotationPresent(PermitAll.class)) {
            return true;
        }
        List<String> permissaoClasse = recuperarPermissoes(classeRecurso);

        if (permissaoClasse != null) {
            verificaPermissoes(permissaoClasse, securityContext);
            return true;
        }
        return false;

    }

    private boolean verificaPermissoesDoMetodo(SecurityContext securityContext) throws Exception {
        Method metodoRecurso = resourceInfo.getResourceMethod();
        if (metodoRecurso.isAnnotationPresent(PermitAll.class)) {
            return true;
        }
        List<String> permissaoDoMetodo = recuperarPermissoes(metodoRecurso);

        if (permissaoDoMetodo != null) {
            verificaPermissoes(permissaoDoMetodo, securityContext);
            return true;
        }
        return false;

    }

    private void verificaPermissoes(List<String> permissoes, SecurityContext securityContext) throws Exception {

        for (final String role : permissoes) {
            if (securityContext.isUserInRole(role)) {
                return;
            }
        }
        throw  new Exception();
    }


    private List<String> recuperarPermissoes(AnnotatedElement elementoAnotado) {
        if (elementoAnotado.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed regrasPermitidas = elementoAnotado.getAnnotation(RolesAllowed.class);
            if (regrasPermitidas == null) {
                return new ArrayList<>();
            } else {
                String[] permissoes = regrasPermitidas.value();
                return Arrays.asList(permissoes);
            }
        }
        return null;
    }
}
