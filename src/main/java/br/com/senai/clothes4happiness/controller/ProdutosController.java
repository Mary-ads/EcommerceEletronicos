package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.dto.ProdutoDTO;
import br.com.senai.clothes4happiness.model.Produtos;
import br.com.senai.clothes4happiness.service.ProdutosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutosService produtosService;

    public ProdutosController(ProdutosService produtosService) {
        this.produtosService = produtosService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos(){
        List<ProdutoDTO> produtos = produtosService.listarTodos().stream().map(ProdutoDTO::fromEntity).toList();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoDTO>> listarAtivos(){
        List<ProdutoDTO> produtos = produtosService.listarAtivos().stream().map(ProdutoDTO::fromEntity).toList();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDTO>> listarPorCategoria(@PathVariable Long categoriaId){
        List<ProdutoDTO> produtos = produtosService.listarPorCategoria(categoriaId).stream().map(ProdutoDTO::fromEntity).toList();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(ProdutoDTO.fromEntity(produtosService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@Valid @RequestBody ProdutoDTO dto){
        Produtos salvo = produtosService.salvar(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoDTO.fromEntity(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDTO dto
    ){
        Produtos atualizado = produtosService.atualizar(id, dto.toEntity());
        return ResponseEntity.ok(ProdutoDTO.fromEntity(atualizado));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<ProdutoDTO> inativar(@PathVariable Long id){
        return ResponseEntity.ok(ProdutoDTO.fromEntity(produtosService.inativar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        produtosService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
