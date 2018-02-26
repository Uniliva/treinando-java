package br.com.devmedia.wsjwt.webservice.resource;

import br.com.devmedia.wsjwt.domain.Marca;
import br.com.devmedia.wsjwt.service.MarcaService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/marcas")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MarcaResource {

    private final MarcaService marcaService = new MarcaService();

    @POST
    public Response salvarMarca(Marca marca, @Context UriInfo uriInfo) {
        marcaService.salvarMarca(marca);

        return Response
                .created(uriInfo.getAbsolutePathBuilder().path(Long.toString(marca.getId())).build())
                .entity(marca)
                .build();
    }

    @GET
    public List<Marca> recuperarMarcas(@QueryParam("nome") @DefaultValue("") String nome) {
        return (nome.isEmpty()) ? marcaService.recuperarMarcas() : marcaService.recuperarMarcasPorNome(nome);
    }

    @GET
    @Path("{marcaId}")
    public Marca recuperarMarcaPorId(@PathParam("marcaId") long marcaId) {
        return marcaService.recuperarMarcaPorId(marcaId);
    }

    @PUT
    @Path("{marcaId}")
    public Marca atualizarMarca(@PathParam("marcaId") long marcaId, Marca marca) {
        marcaService.atualizarMarca(marca, marcaId);
        return marca;
    }

    @DELETE
    @Path("{marcaId}")
    public void excluirMarca(@PathParam("marcaId") long marcaId) {
        marcaService.excluirMarca(marcaId);
    }

    @Path("{marcaId}/produtos")
    public ProdutoResource obterProdutoResource() {
        return new ProdutoResource();
    }

}
