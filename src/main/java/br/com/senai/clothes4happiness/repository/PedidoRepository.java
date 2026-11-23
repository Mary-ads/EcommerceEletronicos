package br.com.senai.clothes4happiness.repository;

import br.com.senai.clothes4happiness.enums.StatusPedido;
import br.com.senai.clothes4happiness.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository
        extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(StatusPedido status);
}
