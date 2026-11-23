package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.model.Categoria;
import br.com.senai.clothes4happiness.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarAtivas(){
        return categoriaRepository.findByAtivoTrueOrderByNomeAsc();
    }

    public Categoria buscarPorId(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException("Categoria não encontrada com o ID: "+id));
    }

    public Categoria salvar(Categoria categoria){
        validarNome(categoria);

        if(categoriaRepository.existsByNomeIgnoreCase(categoria.getNome())){
            throw new RegraNegocioException("Já existe uma categoria com esse nome.");
        }

        categoria.setAtivo(true);

        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria dadosAtualizados){
        Categoria existente = buscarPorId(id);

        validarNome(dadosAtualizados);

        if(categoriaRepository.existsByNomeIgnoreCaseAndIdNot(dadosAtualizados.getNome(), id)){
            throw new RegraNegocioException("Já existe outra categoria com esse nome.");
        }

        existente.setNome(dadosAtualizados.getNome());
        existente.setDescricao(dadosAtualizados.getDescricao());

        return categoriaRepository.save(existente);
    }

    public void deletar(Long id){
        Categoria deletada = buscarPorId(id);
        categoriaRepository.delete(deletada);
    }

    public Categoria inativar(Long id){
        Categoria categoria = buscarPorId(id);
        categoria.setAtivo(false);
        return categoriaRepository.save(categoria);
    }

    private void validarNome(Categoria categoria){
        if(categoria.getNome() == null || categoria.getNome().isBlank()){
            throw new RegraNegocioException("Categoria precisa de um nome.");
        }
    }
}
