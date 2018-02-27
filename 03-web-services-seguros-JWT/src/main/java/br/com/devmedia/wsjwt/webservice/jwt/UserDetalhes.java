package br.com.devmedia.wsjwt.webservice.jwt;

import java.security.Principal;
import java.util.List;

public class UserDetalhes implements Principal {
    private final  String username;
    private  final  List<String> roles;

    public UserDetalhes(String nome, List<String> roles){
        this.roles= roles;
        this.username= nome;

    }

    @Override
    public String getName() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
