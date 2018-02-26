package br.com.devmedia.wsjwt.utils;

import br.com.devmedia.wsjwt.exception.IdInvalidoException;

public class IdUtils {

    public static Long idValido(Long id) {
        if (id <= 0) {
            throw new IdInvalidoException("Id inválido. Deve ser número inteiro maior que zero.");
        }
        return id;
    }

}
