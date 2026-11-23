package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.enums.StatusPedido;
import br.com.senai.clothes4happiness.model.ItemPedido;
import br.com.senai.clothes4happiness.model.Pedido;
import br.com.senai.clothes4happiness.model.Produtos;
import br.com.senai.clothes4happiness.repository.ItemPedidoRepository;
import br.com.senai.clothes4happiness.repository.PedidoRepository;
import br.com.senai.clothes4happiness.repository.ProdutosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutosRepository produtosRepository;
    private final EstoqueService estoqueService;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository,
                             ProdutosRepository produtosRepository, EstoqueService estoqueService) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtosRepository = produtosRepository;
        this.estoqueService = estoqueService;
    }

    public List<ItemPedido> listarTodos(){
        return itemPedidoRepository.findAll();
    }

    public List<ItemPedido> listarPorPedido(Long pedidoId){
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }

    public ItemPedido buscarPorId(Long id){
        return itemPedidoRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Item não encontrado com o ID: "+id));
    }

    @Transactional
    public ItemPedido adicionar(ItemPedido item){
        if(item.getQuantidade() == null || item.getQuantidade() <= 0){
            throw new RegraNegocioException("A quantidade deve ser maior que zero.");
        }
        if(item.getPedido() == null || item.getPedido().getId() == null){
            throw new RegraNegocioException("Informe o pedido.");
        }
        if(item.getProduto() == null || item.getProduto().getId() == null){
            throw new RegraNegocioException("Informe o produto.");
        }

        Long pedidoId = item.getPedido().getId();
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Pedido não encontrado com o ID: "+pedidoId));

        if(pedido.getStatus() != StatusPedido.PENDENTE){
            throw new RegraNegocioException("Só é possível adicionar itens a pedidos PENDENTES.");
        }

        Long produtoId = item.getProduto().getId();
        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Produto não encontrado com o ID: "+produtoId));

        if(!produto.getAtivo()){
            throw new RegraNegocioException("Produto inativo não pode ser vendido.");
        }

        estoqueService.baixarEstoque(produtoId, item.getQuantidade());

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setPrecoUnitario(produto.getPreco());

        BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
        pedido.setValorTotal(pedido.getValorTotal().add(subtotal));
        pedidoRepository.save(pedido);

        return itemPedidoRepository.save(item);
    }

    @Transactional
    public void remover(Long id){
        ItemPedido item = this.buscarPorId(id);
        Pedido pedido = item.getPedido();

        if(pedido.getStatus() != StatusPedido.PENDENTE){
            throw new RegraNegocioException("Só é possível remover itens de pedidos PENDENTES.");
        }

        Long produtoId = item.getProduto().getId();
        estoqueService.devolverEstoque(produtoId, item.getQuantidade());

        BigDecimal subtotal = item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
        pedido.setValorTotal(pedido.getValorTotal().subtract(subtotal));
        pedidoRepository.save(pedido);

        itemPedidoRepository.delete(item);
    }
}
