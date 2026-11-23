package br.com.senai.clothes4happiness.service;

import br.com.senai.clothes4happiness.dto.LoginRequestDTO;
import br.com.senai.clothes4happiness.dto.TokenResponseDTO;
import br.com.senai.clothes4happiness.dto.UsuarioRequestDTO;
import br.com.senai.clothes4happiness.dto.UsuarioResponseDTO;
import br.com.senai.clothes4happiness.exception.CredenciaisInvalidasException;
import br.com.senai.clothes4happiness.exception.RegraNegocioException;
import br.com.senai.clothes4happiness.model.Usuario;
import br.com.senai.clothes4happiness.repository.UsuarioRepository;
import br.com.senai.clothes4happiness.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public List<UsuarioResponseDTO> listarTodos(){
        return usuarioRepository.findAll().stream().map(UsuarioResponseDTO::new).toList();
    }

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto){
        if(usuarioRepository.existsByEmailIgnoreCase(dto.getEmail())){
            throw new RegraNegocioException("Já existe usuário cadastrado com esse e-mail.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setPerfil(dto.getPerfil());

        return new UsuarioResponseDTO(usuarioRepository.save(usuario));
    }

    public TokenResponseDTO login(LoginRequestDTO dto){
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(()-> new CredenciaisInvalidasException("E-mail ou senha inválidos."));

        if(!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())){
            throw new CredenciaisInvalidasException("E-mail ou senha inválidos.");
        }

        String token = tokenService.gerarToken(usuario);

        return new TokenResponseDTO(token, usuario.getPerfil().name());
    }
}
