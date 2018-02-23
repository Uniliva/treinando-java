package br.com.devmedia.webservice.dao;

import br.com.devmedia.webservice.domain.Direcionamento;
import br.com.devmedia.webservice.domain.Imovel;
import br.com.devmedia.webservice.domain.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class ImovelDAO {

    public Imovel salvarImovel(Imovel imovel, long donoImovelId) {
        EntityManager em = JPAUtil.getEntityManager();
        Usuario usuario;

        try {
            em.getTransaction().begin();
            usuario = em.find(Usuario.class, donoImovelId);
            imovel.setUsuario(usuario);
            usuario.getImoveis().add(imovel);
            em.persist(imovel);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return imovel;
    }

    public List<Imovel> selecionarImoveis() {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("select i from Imovel i", Imovel.class).getResultList();
    }

    public Imovel recuperarImovelPorId(long id) {
        EntityManager em = JPAUtil.getEntityManager();

        return em.createQuery("select i from Imovel i where i.id = :imovelId", Imovel.class)
                .setParameter("imovelId", id)
                .getSingleResult();
    }

    public void atualizarImovel(Imovel imovel) {
        EntityManager em = JPAUtil.getEntityManager();
        Imovel imovelManaged;

        try {
            em.getTransaction().begin();
            imovelManaged = em.find(Imovel.class, imovel.getId());
            imovelManaged.setNome(imovel.getNome());
            imovelManaged.setDirecionamento(imovel.getDirecionamento());
            imovelManaged.setEndereco(imovel.getEndereco());
            imovelManaged.setValor(imovel.getValor());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void excluirImovel(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Imovel imovel;

        try {
            em.getTransaction().begin();
            imovel = em.find(Imovel.class, id);
            em.remove(imovel);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Imovel> listarImoveisPorPaginacao(int offset, int limit) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Imovel i", Imovel.class)
                    .setFirstResult(offset - 1)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Imovel> listarImoveisPorNome(String name) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Imovel i where i.nome like :nome", Imovel.class)
                    .setParameter("nome", "%" + name + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Imovel> listarImoveisPorEndereco(String endereco) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Imovel i where i.endereco like :endereco", Imovel.class)
                    .setParameter("endereco",  "%" + endereco + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Imovel> listarImoveisPorDirecionamento(Direcionamento direcionamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery("select i from Imovel i where i.direcionamento = :direcionamento", Imovel.class)
                    .setParameter("direcionamento",  direcionamento)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
