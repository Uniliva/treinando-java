package br.com.devmedia.webservice.resources.filter;

import br.com.devmedia.webservice.domain.Tipo;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface AcessoRestrito {
    Tipo[] value() default {Tipo.CLIENTE, Tipo.FUNCIONARIO,  Tipo.ADMINISTRADOR};
}
