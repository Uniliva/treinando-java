package br.com.devmedia.wsjwt.webservice.jwt;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class JwtSecurityContext implements SecurityContext {
    private UserDetalhes userDetalhes;
    private boolean secure;

    public JwtSecurityContext(UserDetalhes userDetalhes, boolean secure) {
        this.secure = secure;
        this.userDetalhes = userDetalhes;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.userDetalhes;
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.userDetalhes.getRoles().contains(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}
