package br.com.devmedia.webservice.resources;

import br.com.devmedia.webservice.domain.Imovel;
import br.com.devmedia.webservice.resources.filtro.AcessoRestiro;
import br.com.devmedia.webservice.service.ImovelService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("imoveis")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ImovelResource {

    private final ImovelService imovelService = new ImovelService();

    @POST
    @AcessoRestiro
    public Response cadastrarImovel(Imovel imovel) {
        imovelService.cadastrarImovel(imovel);
        return Response.status(Status.CREATED)
                .entity(imovel)
                .build();
    }

    @GET
    public List<Imovel> getImoveis() {
        return imovelService.listarImoveis();
    }

    @GET
    @Path("{imovelId}")
    public Imovel getImovel(@PathParam("imovelId") long id) {
        return imovelService.obterImovel(id);
    }

    @PUT
    @Path("{imovelId}")
    @AcessoRestiro
    public void update(@PathParam("imovelId") int id, Imovel imovel) {
        imovelService.atualizarImovel(id, imovel);
    }

    @DELETE
    @Path("{imovelId}")
    @AcessoRestiro
    public void delete(@PathParam("imovelId") int id) {
        imovelService.descadastrarImovel(id);
    }

}
