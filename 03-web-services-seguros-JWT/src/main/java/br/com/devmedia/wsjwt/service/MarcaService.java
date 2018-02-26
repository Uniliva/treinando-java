package br.com.devmedia.wsjwt.service;

import br.com.devmedia.wsjwt.dao.JPAUtil;
import br.com.devmedia.wsjwt.dao.MarcaDAO;
import br.com.devmedia.wsjwt.domain.Marca;
import br.com.devmedia.wsjwt.utils.IdUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class MarcaService {

    private final MarcaDAO marcaDAO = new MarcaDAO();

    public void salvarMarca(Marca marca) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            marcaDAO.salvarMarca(marca, em);
            if (marca.getProdutos() != null) {
                marca.getProdutos()
                        .parallelStream()
                        .forEach(marca::addProdutoToMarca);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Marca> recuperarMarcas() {
        return marcaDAO.recuperarMarcas();
    }

    public Marca recuperarMarcaPorId(long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();
        return marcaDAO.recuperarMarcaPorId(IdUtils.idValido(marcaId), em);
    }

    public List<Marca> recuperarMarcasPorNome(String nome) {
        return marcaDAO.recuperarMarcasPorNome(nome);
    }

    public void atualizarMarca(Marca marca, long marcaId) {
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        marca.setId(IdUtils.idValido(marcaId));
        marcaDAO.recuperarMarcaPorId(marcaId, em);
        marcaDAO.atualizarMarca(marca, em);
        em.getTransaction().commit();
    }

    public void excluirMarca(long marcaId) {
        marcaDAO.excluirMarca(IdUtils.idValido(marcaId));
    }

}
