package br.com.devmedia.webservice.resources.beans;

import javax.ws.rs.QueryParam;

public class ConfiguracaoConsultaBean {

    private @QueryParam("offset") int offset;
    private @QueryParam("limit") int limit;
    private @QueryParam("nome") String nome;
    private @QueryParam("endereco") String endereco;
    private @QueryParam("direcionamento") String direcionamento;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDirecionamento() {
        return direcionamento;
    }

    public void setDirecionamento(String direcionamento) {
        this.direcionamento = direcionamento;
    }
}
