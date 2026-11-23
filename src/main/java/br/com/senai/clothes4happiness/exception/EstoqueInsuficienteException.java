package br.com.senai.clothes4happiness.exception;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
