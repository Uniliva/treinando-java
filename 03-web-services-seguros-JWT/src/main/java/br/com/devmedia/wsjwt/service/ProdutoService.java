package br.com.devmedia.wsjwt.service;

import br.com.devmedia.wsjwt.dao.JPAUtil;
import br.com.devmedia.wsjwt.dao.ProdutoDAO;
import br.com.devmedia.wsjwt.domain.Produto;
import br.com.devmedia.wsjwt.utils.IdUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void salvarProduto(Produto produto, long marcaId) {
        produtoDAO.salvarProduto(produto, IdUtils.idValido(marcaId));
    }

    public List<Produto> recuperarProdutos(long marcaId) {
        return produtoDAO.recuperarProdutos(IdUtils.idValido(marcaId));
    }

    public Produto recuperarProdutoPorId(long marcaId, long produtoId) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        Produto produto = produtoDAO.recuperarProdutoPorId(IdUtils.idValido(marcaId), IdUtils.idValido(produtoId), em);
        em.getTransaction().commit();

        return produto;
    }

    public List<Produto> recuperarProdutosPorNome(long marcaId, String nome) {
        return produtoDAO.recuperarProdutosPorNome(IdUtils.idValido(marcaId), nome);
    }

    public void atualizarProduto(long marcaId, long produtoId, Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        produto.setId(produtoId);
        produtoDAO.recuperarProdutoPorId(IdUtils.idValido(marcaId), IdUtils.idValido(produtoId), em);
        produtoDAO.atualizarProduto(produto, em);
        em.getTransaction().commit();
    }

    public void excluirProduto(long produtoId) {
        produtoDAO.excluirProduto(IdUtils.idValido(produtoId));
    }

}
