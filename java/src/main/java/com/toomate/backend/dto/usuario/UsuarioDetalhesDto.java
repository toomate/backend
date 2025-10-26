package com.toomate.backend.dto.usuario;

import com.toomate.backend.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetalhesDto implements UserDetails {
    @Schema(description = "nome do usário", example = "lucas")
    private final String nome;
    @Schema(description = "senha do usuário", example = "lucas123")
    private final String senha;
    @Schema(description = "usuário administrador", example = "1")
    private final Boolean administrador;

    public UsuarioDetalhesDto(Usuario usuario){
        this.nome = usuario.getNome();
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
        return nome;
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
