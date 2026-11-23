package br.com.senai.clothes4happiness.exception;

public class CredenciaisInvalidasException extends RuntimeException {

    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
