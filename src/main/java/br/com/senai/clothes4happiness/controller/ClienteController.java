package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.dto.ClienteDTO;
import br.com.senai.clothes4happiness.model.Cliente;
import br.com.senai.clothes4happiness.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos(){
        List<ClienteDTO> clientes = clienteService.listarTodos().stream().map(ClienteDTO::fromEntity).toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(ClienteDTO.fromEntity(clienteService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO dto){
        Cliente salvo = clienteService.salvar(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteDTO.fromEntity(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto
    ){
        Cliente atualizado = clienteService.atualizar(id, dto.toEntity());
        return ResponseEntity.ok(ClienteDTO.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
