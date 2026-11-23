package br.com.senai.clothes4happiness.dto;

import br.com.senai.clothes4happiness.model.Categoria;
import br.com.senai.clothes4happiness.model.Produtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Long id;
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    private String descricao;
    @NotBlank(message = "A marca é obrigatória.")
    private String marca;
    private String modelo;
    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser maior que zero.")
    private BigDecimal preco;
    private Boolean ativo;
    @NotNull(message = "O categoriaId é obrigatório.")
    private Long categoriaId;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, String descricao, String marca,
                      String modelo, BigDecimal preco, Boolean ativo, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.ativo = ativo;
        this.categoriaId = categoriaId;
    }

    public static ProdutoDTO fromEntity(Produtos produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getDescricao(),
                produto.getMarca(), produto.getModelo(), produto.getPreco(),
                produto.getAtivo(), produto.getCategoria().getId());
    }

    public Produtos toEntity() {
        Produtos produto = new Produtos();
        produto.setNome(this.nome);
        produto.setDescricao(this.descricao);
        produto.setMarca(this.marca);
        produto.setModelo(this.modelo);
        produto.setPreco(this.preco);

        Categoria categoria = new Categoria();
        categoria.setId(this.categoriaId);
        produto.setCategoria(categoria);

        return produto;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
