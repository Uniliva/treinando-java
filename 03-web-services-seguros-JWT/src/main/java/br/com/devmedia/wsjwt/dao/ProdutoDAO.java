package br.com.devmedia.wsjwt.dao;

import br.com.devmedia.wsjwt.domain.Marca;
import br.com.devmedia.wsjwt.domain.Produto;
import br.com.devmedia.wsjwt.exception.EntidadeNaoExisteException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

public class ProdutoDAO {

    public void salvarProduto(Produto produto, long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Marca marca = em.find(Marca.class, marcaId);
            produto.setMarca(marca);
            marca.getProdutos().add(produto);
            em.persist(produto);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Produto> recuperarProdutos(long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("select new Produto(p.id, p.nome, p.descricao) from Produto p where p.marca.id = :marcaId", Produto.class)
                    .setParameter("marcaId", marcaId)
                    .getResultList();
    }

    public Produto recuperarProdutoPorId(long marcaId, long produtoId, EntityManager em) {
        Produto produto;

        try {
             produto = em.createQuery("select p from Produto p where p.id = :produtoId and p.marca.id = :marcaId", Produto.class)
                    .setParameter("produtoId", produtoId)
                    .setParameter("marcaId", marcaId)
                    .getSingleResult();

        } catch (NoResultException ex) {
            throw new EntidadeNaoExisteException("Produto de id "+ produtoId +
                    " e marca id " + marcaId + " não existe.");
        }

        return produto;
    }

    public List<Produto> recuperarProdutosPorNome(long marcaId, String nome) {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("select p from Produto p where p.marca.id = :marcaId and p.nome like :nome", Produto.class)
                .setParameter("marcaId", marcaId)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public void atualizarProduto(Produto produto, EntityManager em) {
        em.merge(produto);
    }

    public void excluirProduto(long produtoId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Produto.class, produtoId));
            em.getTransaction().commit();
        } catch (EntityNotFoundException ex) {
            em.getTransaction().rollback();
            throw new EntidadeNaoExisteException("Produto de id " + produtoId + " não existe no banco de dados");
        } finally {
            em.close();
        }
    }
}
