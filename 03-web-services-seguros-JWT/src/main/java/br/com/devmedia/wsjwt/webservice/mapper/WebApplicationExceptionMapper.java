package br.com.devmedia.wsjwt.webservice.mapper;

import br.com.devmedia.wsjwt.domain.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//este mapper trata das demais excessoes que podem cocorrer
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        return Response.status(e.getResponse().getStatus())
                .entity(ErrorMessage.builder()
                        .addErro(e.getMessage())
                        .addStatusCode(e.getResponse().getStatus())
                        .addStatusMessage(e.getResponse().getStatusInfo().toString())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

