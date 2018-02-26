package br.com.devmedia.wsjwt.webservice.mapper;

import br.com.devmedia.wsjwt.domain.ErrorMessage;
import br.com.devmedia.wsjwt.exception.IdInvalidoException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IdInvalidoExceptionMapper implements ExceptionMapper<IdInvalidoException> {

    @Override
    public Response toResponse(IdInvalidoException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorMessage.builder()
                        .addErro(exception.getMessage())
                        .addStatusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .addStatusMessage(Response.Status.BAD_REQUEST.toString())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
