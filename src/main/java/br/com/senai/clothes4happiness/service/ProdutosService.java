package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.model.Categoria;
import br.com.senai.clothes4happiness.model.Produtos;
import br.com.senai.clothes4happiness.repository.CategoriaRepository;
import br.com.senai.clothes4happiness.repository.ProdutosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutosService {

    private final ProdutosRepository produtosRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutosService(ProdutosRepository produtosRepository, CategoriaRepository categoriaRepository) {
        this.produtosRepository = produtosRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Produtos> listarTodos(){
        return produtosRepository.findAll();
    }

    public List<Produtos> listarAtivos(){
        return produtosRepository.findByAtivoTrueOrderByNomeAsc();
    }

    public List<Produtos> listarPorCategoria(Long categoriaId){
        return produtosRepository.findByCategoriaId(categoriaId);
    }

    public Produtos buscarPorId(Long id){
        return produtosRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Produto não encontrado com o ID: "+id));
    }

    public Produtos salvar(Produtos produto){
        validar(produto);

        Categoria categoria = buscarCategoria(produto);

        if(!categoria.getAtivo()){
            throw new RegraNegocioException("Categoria inativa não pode receber produtos.");
        }

        produto.setCategoria(categoria);
        produto.setAtivo(true);

        return produtosRepository.save(produto);
    }

    public Produtos atualizar(Long id, Produtos dadosAtualizados){
        Produtos existente = this.buscarPorId(id);

        validar(dadosAtualizados);

        Categoria categoria = buscarCategoria(dadosAtualizados);

        existente.setNome(dadosAtualizados.getNome());
        existente.setDescricao(dadosAtualizados.getDescricao());
        existente.setMarca(dadosAtualizados.getMarca());
        existente.setModelo(dadosAtualizados.getModelo());
        existente.setPreco(dadosAtualizados.getPreco());
        existente.setCategoria(categoria);

        return produtosRepository.save(existente);
    }

    public void deletar(Long id){
        Produtos produto = this.buscarPorId(id);

        produtosRepository.delete(produto);
    }

    public Produtos inativar(Long id){
        Produtos produto = this.buscarPorId(id);

        produto.setAtivo(false);

        return produtosRepository.save(produto);
    }

    private Categoria buscarCategoria(Produtos produto){
        Long categoriaId = produto.getCategoria().getId();

        return categoriaRepository.findById(categoriaId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Categoria não encontrada com o ID: "+categoriaId));
    }

    private void validar(Produtos produto){
        if(produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new RegraNegocioException("O preço do produto deve ser maior que zero.");
        }
        if(produto.getCategoria() == null || produto.getCategoria().getId() == null){
            throw new RegraNegocioException("Informe a categoria do produto.");
        }
    }
}
