package com.toomate.backend.config;

import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Configuration
public class Inicializar {

    @Bean
    public CommandLineRunner migrarDadosLegadosUsuario(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            List<Usuario> usuarios = usuarioRepository.findAll();
            Set<String> apelidosEmUso = new HashSet<>();

            for (Usuario usuario : usuarios) {
                String apelido = usuario.getApelido();
                if (apelido != null && !apelido.isBlank()) {
                    apelidosEmUso.add(apelido.toLowerCase(Locale.ROOT));
                }
            }

            for (Usuario usuario : usuarios) {
                boolean alterado = false;

                String senhaAtual = usuario.getSenha();
                if (senhaAtual != null && !senhaAtual.isBlank() && !isBcryptHash(senhaAtual)) {
                    usuario.setSenha(passwordEncoder.encode(senhaAtual));
                    alterado = true;
                }

                if (usuario.getApelido() == null || usuario.getApelido().isBlank()) {
                    String base = slugApelido(usuario.getNome());
                    if (base.isBlank()) {
                        base = "usuario";
                    }

                    String candidato = base;
                    int sufixo = 1;
                    while (apelidosEmUso.contains(candidato.toLowerCase(Locale.ROOT))) {
                        candidato = base + sufixo;
                        sufixo++;
                    }

                    usuario.setApelido(candidato);
                    apelidosEmUso.add(candidato.toLowerCase(Locale.ROOT));
                    alterado = true;
                }

                if (alterado) {
                    usuarioRepository.save(usuario);
                }
            }
        };
    }

    private boolean isBcryptHash(String senha) {
        return senha != null && senha.matches("^\\$2[aby]?\\$\\d\\d\\$[./A-Za-z0-9]{53}$");
    }

    private String slugApelido(String valor) {
        if (valor == null || valor.isBlank()) {
            return "";
        }

        String semAcento = Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return semAcento
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9._-]", "")
                .trim();
    }
}
