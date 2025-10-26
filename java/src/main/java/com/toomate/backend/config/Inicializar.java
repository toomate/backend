package com.toomate.backend.config;

import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.dto.marca.MarcaRequestDto;
import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.model.*;
import com.toomate.backend.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Inicializar implements CommandLineRunner  {

    final UsuarioService usuarioService;
    final CategoriaService categoriaService;
    final FornecedorService fornecedorService;
    final InsumoService insumoService;
    final MarcaService marcaService;

    public Inicializar(UsuarioService usuarioService, CategoriaService categoriaService, FornecedorService fornecedorService, InsumoService insumoService, MarcaService marcaService) {
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
        this.insumoService = insumoService;
        this.marcaService = marcaService;
    }

    @Override
    public void run(String... args) throws Exception {
        UsuarioRequestDto usuario = new UsuarioRequestDto("admin","123",true);
        usuarioService.cadastrar(usuario);

        CategoriaRequestDto categoriaRequestDto = new CategoriaRequestDto("Carboidrato");
        Categoria categoria = categoriaService.cadastrar(categoriaRequestDto);

        FornecedorRequestDto fornecedorRequestDto = new FornecedorRequestDto("fornecedorCamil.com.br", "Fornecedor Camil", "11 123456789");
        Fornecedor fornecedor = fornecedorService.cadastrar(fornecedorRequestDto);

        InsumoRequestDto insumoRequestDto = new InsumoRequestDto("Arroz", 20, "kilos", categoria.getIdCategoria());
        Insumo insumo = insumoService.cadastrar(insumoRequestDto, categoria);

        MarcaRequestDto marcaRequestDto = new MarcaRequestDto("Camil", insumo.getIdInsumo(), fornecedor.getId());
        marcaService.cadastrar(marcaRequestDto, fornecedor, insumo);
    }
}
