package br.com.senai.clothes4happiness.repository;

import br.com.senai.clothes4happiness.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository
        extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findByPedidoId(Long pedidoId);
}
