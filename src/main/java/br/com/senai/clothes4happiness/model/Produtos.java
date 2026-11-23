package br.com.senai.clothes4happiness.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private String marca;

    private String modelo;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Boolean ativo=true;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Produtos() {
    }

    public Produtos(Long id, String nome, String descricao, String marca, String modelo, BigDecimal preco, Boolean ativo, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.ativo = ativo;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
