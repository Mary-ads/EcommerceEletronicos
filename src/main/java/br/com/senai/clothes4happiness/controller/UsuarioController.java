package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.dto.UsuarioRequestDTO;
import br.com.senai.clothes4happiness.dto.UsuarioResponseDTO;
import br.com.senai.clothes4happiness.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(dto));
    }
}
