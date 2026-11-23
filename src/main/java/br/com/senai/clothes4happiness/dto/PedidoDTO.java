package br.com.senai.clothes4happiness.dto;

import br.com.senai.clothes4happiness.enums.StatusPedido;
import br.com.senai.clothes4happiness.model.Cliente;
import br.com.senai.clothes4happiness.model.Pedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private Long clienteId;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, LocalDateTime dataPedido, StatusPedido status,
                     BigDecimal valorTotal, Long clienteId) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.valorTotal = valorTotal;
        this.clienteId = clienteId;
    }

    public static PedidoDTO fromEntity(Pedido pedido) {
        return new PedidoDTO(pedido.getId(), pedido.getDataPedido(), pedido.getStatus(),
                pedido.getValorTotal(), pedido.getCliente().getId());
    }

    public Pedido toEntity() {
        Pedido pedido = new Pedido();

        if(this.clienteId != null){
            Cliente cliente = new Cliente();
            cliente.setId(this.clienteId);
            pedido.setCliente(cliente);
        }

        return pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
