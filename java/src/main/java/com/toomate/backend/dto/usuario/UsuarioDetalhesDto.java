package com.toomate.backend.dto.usuario;

import com.toomate.backend.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetalhesDto implements UserDetails {
    private final String nome;
    private final String senha;
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
