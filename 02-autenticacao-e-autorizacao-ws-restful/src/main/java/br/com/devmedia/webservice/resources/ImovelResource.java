package br.com.devmedia.webservice.resources;

import br.com.devmedia.webservice.domain.Imovel;
import br.com.devmedia.webservice.domain.Tipo;
import br.com.devmedia.webservice.resources.filter.AcessoRestrito;
import br.com.devmedia.webservice.service.ImovelService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@AcessoRestrito
@Path("/imoveis")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ImovelResource {

    private final ImovelService imovelService = new ImovelService();

    @POST
    public Response cadastrarImovel(@PathParam("usuarioId") long donoImovelId, Imovel imovel) {
        imovelService.cadastrarImovel(imovel, donoImovelId);
        return Response.status(Status.CREATED)
                .entity(imovel)
                .build();
    }

    @GET
    public List<Imovel> recuperarImoveis() {
        return imovelService.listarImoveis();
    }

    @GET
    @Path("{imovelId}")
    public Imovel recuperarImovel(@PathParam("imovelId") long id) {
        return imovelService.obterImovel(id);
    }

    @PUT
    @Path("{imovelId}")
    public void atualizarImovel(@PathParam("imovelId") long imovelId, Imovel imovel) {
        imovelService.atualizarImovel(imovelId, imovel);
    }

    @DELETE
    @Path("{imovelId}")
    @AcessoRestrito({Tipo.ADMINISTRADOR})
    public void excluirImovel(@PathParam("imovelId") long id) {
        imovelService.descadastrarImovel(id);
    }

}
