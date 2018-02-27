package br.com.devmedia.wsjwt.webservice.mapper;

import br.com.devmedia.wsjwt.domain.ErrorMessage;
import br.com.devmedia.wsjwt.webservice.exception.UnauthenticatedException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class UnauthenticatedExceptionMapper implements
        ExceptionMapper<UnauthenticatedException> {

    @Override
    public Response toResponse(UnauthenticatedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessage.builder()
                        .addErro(exception.getMessage())
                        .addStatusCode(Response.Status.UNAUTHORIZED.getStatusCode())
                        .addStatusMessage(Response.Status.UNAUTHORIZED.toString())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }


}