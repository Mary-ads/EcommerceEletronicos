package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.exception.RecursoNaoEncontradoException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.model.Cliente;
import br.com.senai.clothes4happiness.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Cliente não encontrado com o ID: "+id));
    }

    public Cliente salvar(Cliente cliente){
        validar(cliente);

        if(clienteRepository.existsByEmailIgnoreCase(cliente.getEmail())){
            throw new RegraNegocioException("Já existe cliente cadastrado com esse e-mail.");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado){
        Cliente clienteExistente = this.buscarPorId(id);

        validar(clienteAtualizado);

        if(clienteRepository.existsByEmailIgnoreCaseAndIdNot(clienteAtualizado.getEmail(), id)){
            throw new RegraNegocioException("Já existe outro cliente com esse e-mail.");
        }

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setSenha(clienteAtualizado.getSenha());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(clienteExistente);
    }

    public void deletar(Long id){
        Cliente cliente = this.buscarPorId(id);

        clienteRepository.delete(cliente);
    }

    private void validar(Cliente cliente){
        if(cliente.getNome() == null || cliente.getNome().isBlank()){
            throw new RegraNegocioException("O nome do cliente é obrigatório.");
        }
        if(cliente.getEmail() == null || cliente.getEmail().isBlank()){
            throw new RegraNegocioException("O e-mail do cliente é obrigatório.");
        }
        if(cliente.getSenha() == null || cliente.getSenha().isBlank()){
            throw new RegraNegocioException("A senha do cliente é obrigatória.");
        }
    }
}
