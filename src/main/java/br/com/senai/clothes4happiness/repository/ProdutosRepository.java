package br.com.senai.clothes4happiness.repository;

import br.com.senai.clothes4happiness.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosRepository
        extends JpaRepository<Produtos, Long> {

    List<Produtos> findByAtivoTrueOrderByNomeAsc();

    List<Produtos> findByCategoriaId(Long categoriaId);
}
