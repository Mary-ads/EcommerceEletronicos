package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.dto.CategoriaDTO;
import br.com.senai.clothes4happiness.model.Categoria;
import br.com.senai.clothes4happiness.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodos(){
        List<CategoriaDTO> categorias = service.listarTodos().stream().map(CategoriaDTO::fromEntity).toList();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<CategoriaDTO>> listarAtivas(){
        List<CategoriaDTO> categorias = service.listarAtivas().stream().map(CategoriaDTO::fromEntity).toList();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(CategoriaDTO.fromEntity(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> salvar(@Valid @RequestBody CategoriaDTO dto){
        Categoria salva = service.salvar(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaDTO.fromEntity(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO dto
    ){
        Categoria atualizada = service.atualizar(id, dto.toEntity());
        return ResponseEntity.ok(CategoriaDTO.fromEntity(atualizada));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<CategoriaDTO> inativar(@PathVariable Long id){
        return ResponseEntity.ok(CategoriaDTO.fromEntity(service.inativar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
