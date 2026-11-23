package br.com.senai.clothes4happiness.dto;

import br.com.senai.clothes4happiness.enums.Perfil;
import br.com.senai.clothes4happiness.model.Usuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Perfil perfil;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Perfil getPerfil() {
        return perfil;
    }
}
