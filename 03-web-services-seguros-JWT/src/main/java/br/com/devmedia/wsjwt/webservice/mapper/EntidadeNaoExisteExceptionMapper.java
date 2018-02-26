package br.com.devmedia.wsjwt.webservice.mapper;

import br.com.devmedia.wsjwt.domain.ErrorMessage;
import br.com.devmedia.wsjwt.exception.EntidadeNaoExisteException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntidadeNaoExisteExceptionMapper implements ExceptionMapper<EntidadeNaoExisteException> {

    @Override
    public Response toResponse(EntidadeNaoExisteException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorMessage.builder()
                        .addErro(exception.getMessage())
                        .addStatusCode(Response.Status.NOT_FOUND.getStatusCode())
                        .addStatusMessage(Response.Status.NOT_FOUND.toString())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
