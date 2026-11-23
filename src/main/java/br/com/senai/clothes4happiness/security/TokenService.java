package br.com.senai.clothes4happiness.security;

import br.com.senai.clothes4happiness.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TokenService {

    private static final String EMISSOR = "clothes4happiness";

    private final Algorithm algoritmo;

    public TokenService(@Value("${JWT_SECRET:}") String segredo) {
        if(segredo == null || segredo.isBlank()){
            segredo = UUID.randomUUID().toString();
        }
        this.algoritmo = Algorithm.HMAC256(segredo);
    }

    public String gerarToken(Usuario usuario){
        return JWT.create()
                .withIssuer(EMISSOR)
                .withSubject(usuario.getEmail())
                .withClaim("perfil", usuario.getPerfil().name())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(algoritmo);
    }

    public String validarToken(String token){
        try {
            return JWT.require(algoritmo)
                    .withIssuer(EMISSOR)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
