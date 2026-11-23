package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.dto.LoginRequestDTO;
import br.com.senai.clothes4happiness.dto.TokenResponseDTO;
import br.com.senai.clothes4happiness.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto){
        return ResponseEntity.ok(usuarioService.login(dto));
    }
}
