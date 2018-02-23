package br.com.devmedia.webservice.resources;

import br.com.devmedia.webservice.domain.Imovel;
import br.com.devmedia.webservice.resources.beans.ConfiguracaoConsultaBean;
import br.com.devmedia.webservice.service.ImovelService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/buscas")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BuscaResource {

    private final ImovelService imovelService = new ImovelService();

    @GET
    public List<Imovel> getImoveis(@BeanParam ConfiguracaoConsultaBean configuracao) {
        if ((configuracao.getOffset() >= 0) && (configuracao.getLimit() > 0)) {
            return imovelService.obterImoveisPorPaginacao(configuracao.getOffset(), configuracao.getLimit());
        }
        if (configuracao.getNome() != null) {
            return imovelService.obterImoveisPorNome(configuracao.getNome());
        }
        if (configuracao.getEndereco() != null) {
            return imovelService.obterImoveisPorEndereco(configuracao.getEndereco());
        }
        if (configuracao.getDirecionamento() != null) {
            return imovelService.obterImoveisPorDirecionamento(configuracao.getDirecionamento());
        }

        return imovelService.listarImoveis();
    }

}
