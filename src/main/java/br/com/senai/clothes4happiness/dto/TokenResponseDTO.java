package br.com.senai.clothes4happiness.dto;

public class TokenResponseDTO {

    private String token;
    private String tipo = "Bearer";
    private String perfil;

    public TokenResponseDTO(String token, String perfil) {
        this.token = token;
        this.perfil = perfil;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPerfil() {
        return perfil;
    }
}
