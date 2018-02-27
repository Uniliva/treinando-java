package br.com.devmedia.wsjwt.webservice.mapper;

import br.com.devmedia.wsjwt.domain.ErrorMessage;
import br.com.devmedia.wsjwt.exception.UnauthorizedException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/// Este mapper server para dar uam resposta mais amigavel pelo jersey o que fazer quando receber uma excessao do tipo UnauthorizedException
@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(ErrorMessage.builder()
                        .addErro(e.getMessage())
                        .addStatusCode(Response.Status.FORBIDDEN.getStatusCode())
                        .addStatusMessage(Response.Status.FORBIDDEN.toString())
                        .build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
