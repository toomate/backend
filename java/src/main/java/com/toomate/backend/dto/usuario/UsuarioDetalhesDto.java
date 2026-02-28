package com.toomate.backend.dto.usuario;

import com.toomate.backend.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetalhesDto implements UserDetails {
    @Schema(description = "apelido do usuario", example = "lucas.dev")
    private final String apelido;

    @Schema(description = "senha do usuario", example = "lucas123")
    private final String senha;

    @Schema(description = "usuario administrador", example = "true")
    private final Boolean administrador;

    public UsuarioDetalhesDto(Usuario usuario) {
        this.apelido = usuario.getApelido();
        this.senha = usuario.getSenha();
        this.administrador = usuario.getAdministrador();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return apelido;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
