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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ChamadoResponse> cadastrarChamado(@Valid @RequestBody ChamadoRequest dto) {
        try {
            ChamadoResponse response = chamadoService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar chamado", e);
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ChamadoResponse> atualizarChamado(@PathVariable Long id, @Valid @RequestBody ChamadoRequest dto) {
        try {
            ChamadoResponse response = chamadoService.atualizar(id, dto);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar chamado", e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarChamado(@PathVariable Long id) {
        try {
            chamadoService.deletarChamado(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar chamado", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponse> buscarChamado(@PathVariable Long id) {
        try {
            ChamadoResponse response = chamadoService.buscarChamado(id);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar chamado por ID", e);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ChamadoResponse>> listarTodosChamados() {
        try {
            List<ChamadoResponse> response = chamadoService.listarTodos();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar todos os chamados", e);
        }
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<ChamadoResponse>> listarChamadosPendentes() {
        try {
            List<ChamadoResponse> response = chamadoService.listarPendente();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar chamados pendentes", e);
        }
    }

    @GetMapping("/arquivados")
    public ResponseEntity<List<ChamadoResponse>> listarChamadosArquivados() {
        try {
            List<ChamadoResponse> response = chamadoService.listarArquivado();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar chamados arquivados", e);
        }
    }

    @PatchMapping("/alterar/status/{id}")
    public ResponseEntity<ChamadoResponse> alterarStatusChamado(
            @PathVariable Long id,
            @RequestBody AlterarStatusRequest request) {
        try {
            ChamadoResponse response = chamadoService.atualizarStatus(
                    id,
                    request.getNovoStatus(),
                    request.getAcao(),
                    request.getMensagem()
            );
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao alterar status do chamado", e);
        }
    }

    @GetMapping("/historico/{chamadoId}")
    public ResponseEntity<List<ChamadoHistoricoResponse>> buscarHistoricoDoChamado(@PathVariable Long chamadoId) {
        try {
            List<ChamadoHistoricoResponse> historico = chamadoService.buscarHistoricoDoChamado(chamadoId);
            return ResponseEntity.ok(historico);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar histórico do chamado", e);
        }
    }
}