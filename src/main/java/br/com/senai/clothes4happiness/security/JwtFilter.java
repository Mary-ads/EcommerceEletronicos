package br.com.senai.clothes4happiness.security;

import br.com.senai.clothes4happiness.model.Usuario;
import br.com.senai.clothes4happiness.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public JwtFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String cabecalho = request.getHeader("Authorization");

        if(cabecalho != null && cabecalho.startsWith("Bearer ")){
            String token = cabecalho.substring(7);
            String email = tokenService.validarToken(token);

            if(email != null){
                Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElse(null);

                if(usuario != null){
                    List<SimpleGrantedAuthority> permissoes =
                            List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name()));

                    UsernamePasswordAuthenticationToken autenticacao =
                            new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, permissoes);

                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
