package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.dto.PedidoDTO;
import br.com.senai.clothes4happiness.enums.StatusPedido;
import br.com.senai.clothes4happiness.model.Pedido;
import br.com.senai.clothes4happiness.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos(){
        return ResponseEntity.ok(pedidoService.listarTodos().stream().map(PedidoDTO::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(PedidoDTO.fromEntity(pedidoService.buscarPorId(id)));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> listarPorCliente(@PathVariable Long clienteId){
        return ResponseEntity.ok(pedidoService.listarPorCliente(clienteId).stream().map(PedidoDTO::fromEntity).toList());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidoDTO>> listarPorStatus(@PathVariable StatusPedido status){
        return ResponseEntity.ok(pedidoService.listarPorStatus(status).stream().map(PedidoDTO::fromEntity).toList());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> salvar(@RequestBody PedidoDTO dto){
        Pedido salvo = pedidoService.salvar(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoDTO.fromEntity(salvo));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody PedidoDTO dto
    ){
        Pedido atualizado = pedidoService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.ok(PedidoDTO.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
