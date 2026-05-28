package com.toomate.backend.controller;

import com.toomate.backend.dto.Kpi;
import com.toomate.backend.dto.estoque_grupo.EstoqueGrupo;
import com.toomate.backend.dto.estoque_grupo.EstoqueMapper;
import com.toomate.backend.dto.estoque_grupo.EstoqueVencimento;
import com.toomate.backend.dto.estoque_grupo.VencimentoView;
import com.toomate.backend.dto.page.PageResponseDto;
import com.toomate.backend.mapper.lote.LoteMapper;
import com.toomate.backend.dto.lote.LotePatchDto;
import com.toomate.backend.dto.lote.LoteRequestDto;
import com.toomate.backend.dto.lote.LoteResponseDto;
import com.toomate.backend.dto.lote.ResumoLotesPeriodoDto;
import com.toomate.backend.model.*;
import com.toomate.backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lotes")
@SecurityRequirement(name = "Bearer")
public class LoteController {
    private final LoteService loteService;
    private final UsuarioService usuarioService;
    private final MarcaService marcaService;

    public LoteController(LoteService loteService, UsuarioService usuarioService, MarcaService marcaService) {
        this.loteService = loteService;
        this.usuarioService = usuarioService;
        this.marcaService = marcaService;
    }


    @Operation(summary = "Listar lote",
            description = "Retorna uma lista de lotes(codigo 200) ou codigo 204 se não houver lotes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de lotes",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<LoteResponseDto>> listar() {
        List<LoteResponseDto> lote = LoteMapper.toDto(loteService.listar());

        if (lote.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(lote);
    }

    @Operation(summary = "Listar lotes paginado",
            description = "Retorna uma página de lotes para uso em tabelas de listagem. Aceita filtro de período opcional (dataInicial e dataFinal).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Página de lotes",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/paginado")
    public ResponseEntity<Page<LoteResponseDto>> listarPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal
    ) {
        Page<Lote> resultado = loteService.listarPaginadoPorPeriodo(
                dataInicial, dataFinal, PageRequest.of(pagina, tamanho));

        if (resultado.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(resultado.map(LoteMapper::toDto));
    }

    @Operation(summary = "Resumo de lotes por período",
            description = "Retorna o total de valor e quantidade de registros de lotes no período. Útil para acompanhar o total real (não da página).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resumo do período",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/resumo-periodo")
    public ResponseEntity<ResumoLotesPeriodoDto> resumoPorPeriodo(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal
    ) {
        return ResponseEntity.ok(loteService.resumoPorPeriodo(dataInicial, dataFinal));
    }

    @Operation(summary = "Busca um lote pelo id",
            description = "Retorna um lote(codigo 200) ou lança um exceção com código 404 se não encontrar o lote",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lote",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDto> pegarPorId(@PathVariable Integer id) {
        Lote lote = loteService.listarPorId(id);

        LoteResponseDto loteDto = LoteMapper.toDto(lote);
        return ResponseEntity.status(200).body(loteDto);
    }

    @GetMapping("/estoque")
    public ResponseEntity<PageResponseDto<EstoqueGrupo>> buscarEstoque(@RequestParam(defaultValue = "0") Integer pagina, @RequestParam(defaultValue = "10") Integer tamanho) {
        PageResponseDto<EstoqueGrupo> estoque = loteService.buscarEstoque(pagina, tamanho);

        if (estoque.getConteudo().isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estoque);
    }

    @GetMapping("/estoque/{categoria}")
    public ResponseEntity<List<EstoqueGrupo>> buscarEstoquePorCategoria(@PathVariable String categoria) {
        List<EstoqueGrupo> estoque = loteService.buscarEstoquePorCategoria(categoria);

        if (estoque.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estoque);
    }

    @GetMapping("/estoque/search")
    public ResponseEntity<List<EstoqueGrupo>> pesquisarEstoquePorInsumo(@RequestParam String insumo) {
        List<EstoqueGrupo> estoque = loteService.pesquisarEstoquePorInsumo(insumo);

        if (estoque.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estoque);
    }

    @GetMapping("/estoque/vencimentos")
    public ResponseEntity<List<VencimentoView>> buscarEstoqueVencimento(){
        List<EstoqueVencimento> vencimentos = loteService.buscarEstoqueVencimento();

        if (vencimentos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<VencimentoView> view = EstoqueMapper.toView(vencimentos);

        return ResponseEntity.status(200).body(view);
    }

    @GetMapping("/estoque/kpis")
    public ResponseEntity<List<Kpi>> buscarKpis(){
        List<Kpi> kpis = loteService.buscarKpisVencimentos();

        if(kpis.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(kpis);
    }

    @PatchMapping
    public ResponseEntity<Void> atualizarEstoque(@RequestBody List<LotePatchDto> requests){
        loteService.atualizarQuantidades(requests);

        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Cadastrar lote",
            description = "Retorna o lote cadastrado(codigo 201) ou lança uma exceção de codigo 409 se o lote já estiver cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lote cadastrado",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping
    public ResponseEntity<LoteResponseDto> cadastrar(@RequestBody LoteRequestDto lote) {
        if (lote == null) {
            return null;
        }
        Usuario usuario = usuarioService.usuarioPorId(lote.getFkUsuario());
        Marca marca = marcaService.marcaPorId(lote.getFkMarca());
        Lote corpo = loteService.cadastrar(LoteMapper.toEntity(lote, usuario, marca));

        LoteResponseDto loteResponse = LoteMapper.toDto(corpo);
        return ResponseEntity.status(201).body(loteResponse);
    }

    @Operation(summary = "Deletar lote por id",
            description = "Retorna o codigo 204 ou lança uma exceção de codigo 404 se o lote não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        loteService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar lote",
            description = "Retorna o código 204 ou lança uma exceção de codigo 404 se o lote não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Lote> atualizar(@RequestBody LoteRequestDto lote, @PathVariable Integer id) {

        if (loteService.existePorId(id)) {
            Usuario usuario = usuarioService.usuarioPorId(lote.getFkUsuario());
            Marca marca = marcaService.marcaPorId(lote.getFkMarca());
            Lote loteAtualizado = LoteMapper.toEntity(lote, usuario, marca);
            loteService.atualizar(id, loteAtualizado);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/adicionarEstoque/{id}")
    public ResponseEntity<Lote> adicionar(@RequestBody Integer quantidadeMedida, @PathVariable Integer id) {
        if (loteService.existePorId(id)) {
            loteService.adicionarQuantidade(id, quantidadeMedida);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/removerEstoque/{id}")
    public ResponseEntity<Lote> remover(@RequestBody Integer quantidadeMedida, @PathVariable Integer id) {
        if (loteService.existePorId(id)) {
            loteService.removerQuantidade(id, quantidadeMedida);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

}
