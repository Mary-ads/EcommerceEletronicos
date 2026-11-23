package br.com.senai.clothes4happiness.dto;

import br.com.senai.clothes4happiness.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {

    private Long id;
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;
    private String descricao;
    private Boolean ativo;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nome, String descricao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public static CategoriaDTO fromEntity(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(), categoria.getNome(),
                categoria.getDescricao(), categoria.getAtivo());
    }

    public Categoria toEntity() {
        Categoria categoria = new Categoria();
        categoria.setNome(this.nome);
        categoria.setDescricao(this.descricao);
        return categoria;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
