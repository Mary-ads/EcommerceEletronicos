package br.com.senai.clothes4happiness.dto;

import br.com.senai.clothes4happiness.model.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    private Long id;
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;
    private String telefone;
    private String endereco;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String email, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public static ClienteDTO fromEntity(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(),
                cliente.getTelefone(), cliente.getEndereco());
    }

    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setEmail(this.email);
        cliente.setSenha(this.senha);
        cliente.setTelefone(this.telefone);
        cliente.setEndereco(this.endereco);
        return cliente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
