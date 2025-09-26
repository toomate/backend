package com.toomate.backend.mapper.usuario;

import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.model.Usuario;

import java.util.List;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioRequestDto usuarioRequestDto){
        if (usuarioRequestDto == null){
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioRequestDto.getNome());
        usuario.setSenha(usuarioRequestDto.getSenha());
        usuario.setAdministrador(usuarioRequestDto.getAdministrador());

        return usuario;
    }

    public static UsuarioResponseDto toResponse(Usuario usuario){
        if (usuario == null){
            return null;
        }

        UsuarioResponseDto response = new UsuarioResponseDto();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setAdministrador(usuario.getAdministrador());

        return response;
    }

    public static List<UsuarioResponseDto> toResponse(List<Usuario> usuarios){
        return usuarios.stream().map(usuario -> UsuarioMapper.toResponse(usuario)).toList();
    }
}
