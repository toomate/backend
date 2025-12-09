package com.toomate.backend.config;

import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.dto.lote.LoteRequestDto;
import com.toomate.backend.dto.lote.LoteResponseDto;
import com.toomate.backend.dto.marca.MarcaRequestDto;
import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.UsuarioRepository;
import com.toomate.backend.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;


