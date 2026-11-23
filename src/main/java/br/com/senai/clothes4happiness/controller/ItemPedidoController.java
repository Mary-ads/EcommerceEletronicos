package br.com.senai.clothes4happiness.controller;

import br.com.senai.clothes4happiness.model.ItemPedido;
import br.com.senai.clothes4happiness.service.ItemPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<ItemPedido>> listarTodos(){
        return ResponseEntity.ok(itemPedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(itemPedidoService.buscarPorId(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedido>> listarPorPedido(@PathVariable Long pedidoId){
        return ResponseEntity.ok(itemPedidoService.listarPorPedido(pedidoId));
    }

    @PostMapping
    public ResponseEntity<ItemPedido> adicionar(@RequestBody ItemPedido item){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoService.adicionar(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable Long id){
        itemPedidoService.remover(id);
        return ResponseEntity.ok("Item removido do pedido com sucesso.");
    }
}
