package br.com.senai.clothes4happiness.repository;

import br.com.senai.clothes4happiness.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository
        extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByProdutoId(Long produtoId);

    boolean existsByProdutoId(Long produtoId);
}
