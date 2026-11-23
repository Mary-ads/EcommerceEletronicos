package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.model.Estoque;
import br.com.senai.clothes4happiness.service.EstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos(){
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<Estoque> buscarPorProduto(@PathVariable Long produtoId){
        return ResponseEntity.ok(estoqueService.buscarPorProduto(produtoId));
    }

    @PostMapping
    public ResponseEntity<Estoque> salvar(@RequestBody Estoque estoque){
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.salvar(estoque));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> atualizar(
            @PathVariable Long id,
            @RequestBody Estoque estoque
    ){
        return ResponseEntity.ok(estoqueService.atualizar(id, estoque));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        estoqueService.deletar(id);
        return ResponseEntity.ok("Estoque deletado com sucesso.");
    }
}
