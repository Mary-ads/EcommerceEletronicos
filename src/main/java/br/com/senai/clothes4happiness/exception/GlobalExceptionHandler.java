package br.com.senai.clothes4happiness.exception;

import br.com.senai.clothes4happiness.dto.ErroResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> naoEncontrado(RecursoNaoEncontradoException e){
        return montarErro(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponseDTO> regraNegocio(RegraNegocioException e){
        return montarErro(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErroResponseDTO> estoqueInsuficiente(EstoqueInsuficienteException e){
        return montarErro(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroResponseDTO> credenciaisInvalidas(CredenciaisInvalidasException e){
        return montarErro(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> dadosInvalidos(MethodArgumentNotValidException e){
        String mensagem = e.getBindingResult().getFieldErrors().get(0).getField()
                + ": " + e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return montarErro(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> jsonInvalido(HttpMessageNotReadableException e){
        return montarErro(HttpStatus.BAD_REQUEST, "JSON inválido. Confira os campos e os valores enviados.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponseDTO> integridade(DataIntegrityViolationException e){
        return montarErro(HttpStatus.CONFLICT, "Operação não permitida: o registro está vinculado a outros dados.");
    }

    private ResponseEntity<ErroResponseDTO> montarErro(HttpStatus status, String mensagem){
        ErroResponseDTO erro = new ErroResponseDTO(status.value(), mensagem, LocalDateTime.now());
        return ResponseEntity.status(status).body(erro);
    }
}
