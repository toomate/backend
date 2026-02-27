package com.toomate.backend.config;

import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class Inicializar {

    @Bean
    public CommandLineRunner migrarSenhasLegadas(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            List<Usuario> usuarios = usuarioRepository.findAll();

            for (Usuario usuario : usuarios) {
                String senhaAtual = usuario.getSenha();
                if (senhaAtual == null || senhaAtual.isBlank()) {
                    continue;
                }

                if (!isBcryptHash(senhaAtual)) {
                    usuario.setSenha(passwordEncoder.encode(senhaAtual));
                    usuarioRepository.save(usuario);
                }
            }
        };
    }

    private boolean isBcryptHash(String senha) {
        return senha != null && senha.matches("^\\$2[aby]?\\$\\d\\d\\$[./A-Za-z0-9]{53}$");
    }
}
