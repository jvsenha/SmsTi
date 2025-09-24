package com.br.SmsTi.Controller;

import com.br.SmsTi.DTO.AlterarStatusRequest;
import com.br.SmsTi.DTO.ChamadoHistoricoResponse;
import com.br.SmsTi.DTO.ChamadoRequest;
import com.br.SmsTi.DTO.ChamadoResponse;
import com.br.SmsTi.Service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ChamadoResponse> cadastrarChamado(@Valid @RequestBody ChamadoRequest dto) {
        // Sem try-catch! Se o service lançar a exceção, o handler captura.
        ChamadoResponse response = chamadoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ChamadoResponse> atualizarChamado(@PathVariable Long id, @Valid @RequestBody ChamadoRequest dto) {
        ChamadoResponse response = chamadoService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarChamado(@PathVariable Long id) {
        chamadoService.deletarChamado(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponse> buscarChamado(@PathVariable Long id) {
        ChamadoResponse response = chamadoService.buscarChamado(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ChamadoResponse>> listarTodosChamados() {
        List<ChamadoResponse> response = chamadoService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<ChamadoResponse>> listarChamadosPendentes() {
        List<ChamadoResponse> response = chamadoService.listarPendente();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/arquivados")
    public ResponseEntity<List<ChamadoResponse>> listarChamadosArquivados() {
        List<ChamadoResponse> response = chamadoService.listarArquivado();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/alterar/status/{id}")
    public ResponseEntity<ChamadoResponse> alterarStatusChamado(
            @PathVariable Long id,
            @RequestBody AlterarStatusRequest request) {
        ChamadoResponse response = chamadoService.atualizarStatus(
                id,
                request.getNovoStatus(),
                request.getAcao(),
                request.getMensagem()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/historico/{chamadoId}")
    public ResponseEntity<List<ChamadoHistoricoResponse>> buscarHistoricoDoChamado(@PathVariable Long chamadoId) {
        List<ChamadoHistoricoResponse> historico = chamadoService.buscarHistoricoDoChamado(chamadoId);
        return ResponseEntity.ok(historico);
    }
}