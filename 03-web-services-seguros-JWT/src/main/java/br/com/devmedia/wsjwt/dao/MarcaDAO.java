package br.com.devmedia.wsjwt.dao;

import br.com.devmedia.wsjwt.domain.Marca;
import br.com.devmedia.wsjwt.exception.EntidadeNaoExisteException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class MarcaDAO {

    public void salvarMarca(Marca marca, EntityManager em) {
        em.persist(marca);
    }

    public List<Marca> recuperarMarcas() {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("select m from Marca m", Marca.class).getResultList();
    }

    public Marca recuperarMarcaPorId(long marcaId, EntityManager em) {
        Marca marca = em.find(Marca.class, marcaId);
        if (marca == null) {
            throw new EntidadeNaoExisteException("Marca de id " + marcaId + " não existe no banco de dados");
        }
        return marca;
    }

    public List<Marca> recuperarMarcasPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("SELECT m FROM Marca m WHERE m.nome like :nome", Marca.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public void atualizarMarca(Marca marca, EntityManager em) {
        em.merge(marca);
    }

    public void excluirMarca(long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.getReference(Marca.class, marcaId));
            em.getTransaction().commit();
        } catch (EntityNotFoundException ex) {
            em.getTransaction().rollback();
            throw new EntidadeNaoExisteException("Marca de id " + marcaId + " não existe no banco de dados");
        } finally {
            em.close();
        }
    }

}
