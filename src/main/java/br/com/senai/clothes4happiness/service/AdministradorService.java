package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.model.Administrador;
import br.com.senai.clothes4happiness.repository.AdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    public List<Administrador> listarTodos(){
        return administradorRepository.findAll();
    }

    public Administrador buscarPorId(Long id){
        return administradorRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Administrador não encontrado com o ID: "+id));
    }

    public Administrador salvar(Administrador administrador){
        validar(administrador);

        if(administradorRepository.existsByEmailIgnoreCase(administrador.getEmail())){
            throw new RegraNegocioException("Já existe administrador cadastrado com esse e-mail.");
        }

        return administradorRepository.save(administrador);
    }

    public Administrador atualizar(Long id, Administrador administradorAtualizado){
        Administrador administradorExistente = this.buscarPorId(id);

        validar(administradorAtualizado);

        if(administradorRepository.existsByEmailIgnoreCaseAndIdNot(administradorAtualizado.getEmail(), id)){
            throw new RegraNegocioException("Já existe outro administrador com esse e-mail.");
        }

        administradorExistente.setNome(administradorAtualizado.getNome());
        administradorExistente.setEmail(administradorAtualizado.getEmail());
        administradorExistente.setSenha(administradorAtualizado.getSenha());

        return administradorRepository.save(administradorExistente);
    }

    public void deletar(Long id){
        Administrador administrador = this.buscarPorId(id);

        administradorRepository.delete(administrador);
    }

    private void validar(Administrador administrador){
        if(administrador.getNome() == null || administrador.getNome().isBlank()){
            throw new RegraNegocioException("O nome do administrador é obrigatório.");
        }
        if(administrador.getEmail() == null || administrador.getEmail().isBlank()){
            throw new RegraNegocioException("O e-mail do administrador é obrigatório.");
        }
        if(administrador.getSenha() == null || administrador.getSenha().isBlank()){
            throw new RegraNegocioException("A senha do administrador é obrigatória.");
        }
    }
}
