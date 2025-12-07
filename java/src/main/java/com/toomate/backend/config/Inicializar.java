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

@Configuration
public class Inicializar implements CommandLineRunner  {

    final UsuarioService usuarioService;
    final CategoriaService categoriaService;
    final FornecedorService fornecedorService;
    final InsumoService insumoService;
    final MarcaService marcaService;
    final LoteService loteService;
    final UsuarioRepository usuarioRepository;

    public Inicializar(UsuarioService usuarioService, CategoriaService categoriaService, FornecedorService fornecedorService, InsumoService insumoService, MarcaService marcaService, LoteService loteService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
        this.insumoService = insumoService;
        this.marcaService = marcaService;
        this.loteService = loteService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        UsuarioRequestDto usuario = new UsuarioRequestDto("admin","123",true);
        UsuarioResponseDto usuarioCadastrado = usuarioService.cadastrar(usuario);
        Usuario usuarioComId = usuarioRepository.findById(usuarioCadastrado.getId()).get();


        CategoriaRequestDto categoriaRequestDto = new CategoriaRequestDto("Graos");
        Categoria categoria = categoriaService.cadastrar(categoriaRequestDto);

        FornecedorRequestDto fornecedorRequestDto = new FornecedorRequestDto("fornecedorGraos.com.br", "Fornecedor de Graos", "11 123456789");
        Fornecedor fornecedor = fornecedorService.cadastrar(fornecedorRequestDto);

        InsumoRequestDto arrozRequestDto = new InsumoRequestDto("Arroz", 20, "kilos", categoria.getIdCategoria());
        Insumo insumoArrozCadastrar = InsumoMapperDto.toEntity(arrozRequestDto, categoria);
        Insumo insumoArroz = insumoService.cadastrar(insumoArrozCadastrar);

        InsumoRequestDto feijaoRequestDto = new InsumoRequestDto("Feijao", 15, "kilos", categoria.getIdCategoria());
        Insumo insumoFeijaoCadastrar = InsumoMapperDto.toEntity(feijaoRequestDto, categoria);
        Insumo insumoFeijao = insumoService.cadastrar(insumoFeijaoCadastrar);

        MarcaRequestDto marcaARequestDto = new MarcaRequestDto("Camil", insumoArroz.getIdInsumo(), fornecedor.getId());
        Marca marcaArroz = marcaService.cadastrar(marcaARequestDto);

        MarcaRequestDto marcaFRequestDto = new MarcaRequestDto("Feijao Joao", insumoFeijao.getIdInsumo(), fornecedor.getId());
        Marca marcaFeijao = marcaService.cadastrar(marcaFRequestDto);

        for (int i = 1; i <= 3; i++){
            LocalDate dataCompra = LocalDate.of(2025, i * 3,i * 7);
            LocalDate dataValidade = LocalDate.of(2025, i * 3,i * 7 + 9);
            LoteRequestDto loteArroz = new LoteRequestDto(
                    dataValidade, 19.9, 3.0 * i, dataCompra, marcaArroz.getIdMarca(),1
            );

            LoteRequestDto loteFeijao = new LoteRequestDto(
                    dataValidade, 14.9, 2.5 * i, dataCompra, marcaFeijao.getIdMarca(),1
            );

            loteService.cadastrar(loteArroz, usuarioComId, marcaArroz);
            loteService.cadastrar(loteFeijao, usuarioComId, marcaFeijao);

        }

    }
}
