package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.enums.StatusPedido;
import br.com.senai.clothes4happiness.model.Cliente;
import br.com.senai.clothes4happiness.model.Estoque;
import br.com.senai.clothes4happiness.model.ItemPedido;
import br.com.senai.clothes4happiness.model.Pedido;
import br.com.senai.clothes4happiness.repository.ClienteRepository;
import br.com.senai.clothes4happiness.repository.EstoqueRepository;
import br.com.senai.clothes4happiness.repository.ItemPedidoRepository;
import br.com.senai.clothes4happiness.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final EstoqueRepository estoqueRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         ItemPedidoRepository itemPedidoRepository, EstoqueRepository estoqueRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<Pedido> listarTodos(){
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPorCliente(Long clienteId){
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> listarPorStatus(StatusPedido status){
        return pedidoRepository.findByStatus(status);
    }

    public Pedido buscarPorId(Long id){
        return pedidoRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Pedido não encontrado com o ID: "+id));
    }

    public Pedido salvar(Pedido pedido){
        if(pedido.getCliente() == null || pedido.getCliente().getId() == null){
            throw new RegraNegocioException("Informe o cliente do pedido.");
        }

        Long clienteId = pedido.getCliente().getId();

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Cliente não encontrado com o ID: "+clienteId));

        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setValorTotal(BigDecimal.ZERO);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizarStatus(Long id, StatusPedido novoStatus){
        Pedido pedido = this.buscarPorId(id);

        if(novoStatus == null){
            throw new RegraNegocioException("Informe o status: PENDENTE, CONFIRMADO, CANCELADO ou CONCLUIDO.");
        }
        if(pedido.getStatus() == StatusPedido.CANCELADO || pedido.getStatus() == StatusPedido.CONCLUIDO){
            throw new RegraNegocioException("Pedido " + pedido.getStatus() + " não pode mudar de status.");
        }

        if(novoStatus == StatusPedido.CANCELADO){
            List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(id);

            for(ItemPedido item : itens){
                Long produtoId = item.getProduto().getId();

                Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                        .orElseThrow(()-> new RecursoNaoEncontradoException("Estoque não encontrado para o produto com ID: "+produtoId));

                estoque.setQuantidade(estoque.getQuantidade() + item.getQuantidade());
                estoqueRepository.save(estoque);
            }
        }

        pedido.setStatus(novoStatus);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deletar(Long id){
        Pedido pedido = this.buscarPorId(id);

        if(pedido.getStatus() != StatusPedido.CANCELADO){
            throw new RegraNegocioException("Só é possível deletar pedidos CANCELADOS. Cancele o pedido antes.");
        }

        List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(id);
        itemPedidoRepository.deleteAll(itens);

        pedidoRepository.delete(pedido);
    }
}
