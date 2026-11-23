package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.model.Administrador;
import br.com.senai.clothes4happiness.service.AdministradorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> listarTodos(){
        return ResponseEntity.ok(administradorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(administradorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Administrador> salvar(@RequestBody Administrador administrador){
        return ResponseEntity.status(HttpStatus.CREATED).body(administradorService.salvar(administrador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizar(
            @PathVariable Long id,
            @RequestBody Administrador administrador
    ){
        return ResponseEntity.ok(administradorService.atualizar(id, administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        administradorService.deletar(id);
        return ResponseEntity.ok("Administrador deletado com sucesso.");
    }
}
