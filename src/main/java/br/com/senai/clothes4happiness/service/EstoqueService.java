package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.EstoqueInsuficienteException;
import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.model.Estoque;
import br.com.senai.clothes4happiness.model.Produtos;
import br.com.senai.clothes4happiness.repository.EstoqueRepository;
import br.com.senai.clothes4happiness.repository.ProdutosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutosRepository produtosRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutosRepository produtosRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtosRepository = produtosRepository;
    }

    public List<Estoque> listarTodos(){
        return estoqueRepository.findAll();
    }

    public Estoque buscarPorId(Long id){
        return estoqueRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Estoque não encontrado com o ID: "+id));
    }

    public Estoque buscarPorProduto(Long produtoId){
        return estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Nenhum estoque cadastrado para o produto com ID: "+produtoId));
    }

    public Estoque salvar(Estoque estoque){
        validarQuantidade(estoque.getQuantidade());

        if(estoque.getProduto() == null || estoque.getProduto().getId() == null){
            throw new RegraNegocioException("Informe o produto do estoque.");
        }

        Long produtoId = estoque.getProduto().getId();

        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Produto não encontrado com o ID: "+produtoId));

        if(estoqueRepository.existsByProdutoId(produtoId)){
            throw new RegraNegocioException("Este produto já possui estoque cadastrado.");
        }

        estoque.setProduto(produto);

        return estoqueRepository.save(estoque);
    }

    public Estoque atualizar(Long id, Estoque estoqueAtualizado){
        Estoque estoqueExistente = this.buscarPorId(id);

        validarQuantidade(estoqueAtualizado.getQuantidade());

        estoqueExistente.setQuantidade(estoqueAtualizado.getQuantidade());

        return estoqueRepository.save(estoqueExistente);
    }

    public void deletar(Long id){
        Estoque estoque = this.buscarPorId(id);

        estoqueRepository.delete(estoque);
    }

    public void verificarEstoque(Long produtoId, Integer quantidadePedida) {
        if (quantidadePedida == null || quantidadePedida <= 0) {
            throw new RegraNegocioException("A quantidade pedida deve ser maior que zero.");
        }
        Estoque estoque = this.buscarPorProduto(produtoId);

        if (estoque.getQuantidade() < quantidadePedida) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para o produto " + estoque.getProduto().getNome()
                            + ". Disponível: " + estoque.getQuantidade()
                            + ", solicitado: " + quantidadePedida);
        }
    }

    public void baixarEstoque(Long produtoId, Integer quantidade) {
        verificarEstoque(produtoId, quantidade);

        Estoque estoque = this.buscarPorProduto(produtoId);
        estoque.setQuantidade(estoque.getQuantidade() - quantidade);

        estoqueRepository.save(estoque);
    }

    public void devolverEstoque(Long produtoId, Integer quantidade) {
        Estoque estoque = this.buscarPorProduto(produtoId);
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);

        estoqueRepository.save(estoque);
    }

    private void validarQuantidade(Integer quantidade){
        if(quantidade == null || quantidade < 0){
            throw new RegraNegocioException("A quantidade em estoque não pode ser negativa.");
        }
    }
}
