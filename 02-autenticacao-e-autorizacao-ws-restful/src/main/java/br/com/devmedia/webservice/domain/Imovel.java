package br.com.devmedia.webservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String endereco;

    @Enumerated(EnumType.STRING)
    private Direcionamento direcionamento;

    private Double valor;

    @ManyToOne
    @JsonIgnore
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Direcionamento getDirecionamento() {
        return direcionamento;
    }

    public void setDirecionamento(Direcionamento direcionamento) {
        this.direcionamento = direcionamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Imovel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", direcionamento=" + direcionamento +
                ", valor=" + valor +
                ", usuario=" + usuario +
                '}';
    }
}
