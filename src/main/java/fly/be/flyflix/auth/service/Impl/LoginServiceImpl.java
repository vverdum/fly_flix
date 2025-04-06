package fly.be.flyflix.auth.service.Impl;

import fly.be.flyflix.auth.controller.dto.LoginRequest;
import fly.be.flyflix.auth.controller.dto.LoginResponse;
import fly.be.flyflix.auth.repository.UsuarioRepository;
import fly.be.flyflix.auth.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        System.out.println("Login Request: ");

        System.out.println("Login Request: " + loginRequest.login());

        var usuario = usuarioRepository.findByLogin(loginRequest.login());

        //Validar se login esta correto se o usuario existir e se a senha estiver correta
        if (usuario.isEmpty() || !usuario.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }

        //Gerar e retornar token jwt com as permissoes
        var now = Instant.now();
        var expiresIn = 300L; // expira em 5 minutos
        var scopes = usuario.get().getPerfiles()
                .stream()
                .map(role -> role.getName().toUpperCase())
                .collect(Collectors.joining(" "));


        // dados do token jwt com as permissoes do usuario logado
        var claims = JwtClaimsSet.builder()
                .issuer("myBackend")
                .subject(usuario.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes) // permissoes do token jwt
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
