package br.com.devmedia.webservice.service;

import br.com.devmedia.webservice.dao.ImovelDAO;
import br.com.devmedia.webservice.domain.Direcionamento;
import br.com.devmedia.webservice.domain.Imovel;

import java.util.List;

public class ImovelService {

    private final ImovelDAO imovelDAO = new ImovelDAO();

    public void cadastrarImovel(Imovel imovel, long donoImovelId) {
        imovelDAO.salvarImovel(imovel, donoImovelId);
    }

    public void descadastrarImovel(long id) {
        imovelDAO.excluirImovel(id);
    }

    public void atualizarImovel(long imovelId, Imovel imovel) {
        imovel.setId(imovelId);
        imovelDAO.atualizarImovel(imovel);
    }

    public List<Imovel> listarImoveis() {
        return imovelDAO.selecionarImoveis();
    }

    public Imovel obterImovel(long id) {
        return imovelDAO.recuperarImovelPorId(id);
    }

    public List<Imovel> obterImoveisPorPaginacao(int offset, int limit) {
        return imovelDAO.listarImoveisPorPaginacao(offset, limit);
    }

    public List<Imovel> obterImoveisPorNome(String name) {
        return imovelDAO.listarImoveisPorNome(name);
    }

    public List<Imovel> obterImoveisPorEndereco(String endereco) {
        return imovelDAO.listarImoveisPorEndereco(endereco);
    }

    public List<Imovel> obterImoveisPorDirecionamento(String direcionamento) {
        return imovelDAO.listarImoveisPorDirecionamento(direcionamentoConverter(direcionamento));
    }

    private Direcionamento direcionamentoConverter(String direcionamento) {
        if (direcionamento.equalsIgnoreCase("venda")) {
            return Direcionamento.VENDA;
        } else {
            return Direcionamento.ALUGUEL;
        }
    }

}
