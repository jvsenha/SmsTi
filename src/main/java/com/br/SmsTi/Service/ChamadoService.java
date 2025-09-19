package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.ChamadoRequest;
import com.br.SmsTi.DTO.ChamadoResponse;
import com.br.SmsTi.Entity.ChamadoEntity;
import com.br.SmsTi.Entity.ChamadoHistoricoEntity;
import com.br.SmsTi.Entity.UnidadeEntity;
import com.br.SmsTi.Enum.StatusChamado;
import com.br.SmsTi.Mapper.ChamadoMapper;
import com.br.SmsTi.Repository.ChamadoHistoricoRepository;
import com.br.SmsTi.Repository.ChamadoRepository;
import com.br.SmsTi.Repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors; // Importar Collectors

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoHistoricoRepository chamadoHistoricoRepository;

    @Autowired
    private ChamadoMapper chamadoMapper;

    @Autowired
    private UnidadeRepository unidadeRepository;


    private void registrarHistorico(ChamadoEntity chamado, String tipoAcao, String detalhes) {
        String usuarioAcao = SecurityContextHolder.getContext().getAuthentication().getName();
        ChamadoHistoricoEntity historico = new ChamadoHistoricoEntity();
        historico.setChamado(chamado);
        historico.setTipoAcao(tipoAcao);
        historico.setUsuarioAcao(usuarioAcao);
        historico.setDataAcao(LocalDateTime.now());
        historico.setDetalhes(detalhes);
        chamadoHistoricoRepository.save(historico);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or@securityService.isNivel2(authentication)")
    public ChamadoResponse cadastrar(ChamadoRequest dto) {
        UnidadeEntity unidade = unidadeRepository.findById(dto.getUnidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada!"));

        ChamadoEntity chamado = chamadoMapper.toEntity(dto); // Usa o mapper
        chamado.setUnidade(unidade);
        chamado.setStatusChamado(StatusChamado.PENDENTE);

        ChamadoEntity salvoChamado = chamadoRepository.save(chamado);

        registrarHistorico(salvoChamado, "Cadastro", "Chamado cadastrado."); // Registra o cadastro

        return chamadoMapper.toResponse(salvoChamado);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or@securityService.isNivel2(authentication)")
    public ChamadoResponse atualizar(Long id, ChamadoRequest dto) {
        ChamadoEntity chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado!"));

        UnidadeEntity unidade = unidadeRepository.findById(dto.getUnidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada!"));

        // Guarda o estado anterior para detalhes do histórico, se necessário
        String statusAnterior = chamado.getStatusChamado().name();

        chamadoMapper.updateEntityFromRequest(dto, chamado); // Usa o mapper para atualizar
        chamado.setUnidade(unidade); // Atualiza a unidade, se o ID for diferente
        // Não setar comentarios para null aqui

        ChamadoEntity atualizadoChamado = chamadoRepository.save(chamado);

        registrarHistorico(atualizadoChamado, "Atualização", "Chamado atualizado. Status anterior: " + statusAnterior);

        return chamadoMapper.toResponse(atualizadoChamado);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication)")
    public void deletarChamado(Long id) {
        ChamadoEntity chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado para deleção!"));

        chamadoRepository.delete(chamado);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public ChamadoResponse buscarChamado(Long id) {
        ChamadoEntity chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado!"));
        return chamadoMapper.toResponse(chamado);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public List<ChamadoResponse> listarTodos() {
        return chamadoRepository.findAll().stream()
                .map(chamadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public List<ChamadoResponse> listarPendente() {
        return chamadoRepository.findByStatusChamado(StatusChamado.PENDENTE).stream()
                .map(chamadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public List<ChamadoResponse> listarArquivado() {
        return chamadoRepository.findByStatusChamado(StatusChamado.ARQUIVADO).stream()
                .map(chamadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public ChamadoResponse arquivarChamado(Long id) { // Removido usuarioEmail do parâmetro
        ChamadoEntity chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado para arquivamento!"));

        if (chamado.getStatusChamado().equals(StatusChamado.ARQUIVADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Chamado já está arquivado!");
        }

        String statusAnterior = chamado.getStatusChamado().name();
        chamado.setStatusChamado(StatusChamado.ARQUIVADO);
        ChamadoEntity chamadoArquivado = chamadoRepository.save(chamado);

        registrarHistorico(chamadoArquivado, "Arquivamento", "Chamado arquivado. Status anterior: " + statusAnterior);

        return chamadoMapper.toResponse(chamadoArquivado);
    }

    // Novo método para alterar qualquer status
    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or@securityService.isNivel2(authentication)")
    public ChamadoResponse alterarStatus(Long id, StatusChamado novoStatus) {
        ChamadoEntity chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado para alteração de status!"));

        String statusAnterior = chamado.getStatusChamado().name();
        if (chamado.getStatusChamado().equals(novoStatus)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O chamado já possui este status!");
        }

        chamado.setStatusChamado(novoStatus);
        ChamadoEntity atualizadoChamado = chamadoRepository.save(chamado);

        registrarHistorico(atualizadoChamado, "Alteração de Status", "Status alterado de " + statusAnterior + " para " + novoStatus.name());

        return chamadoMapper.toResponse(atualizadoChamado);
    }

    // Método para buscar histórico de um chamado
    public List<ChamadoHistoricoEntity> buscarHistoricoDoChamado(Long chamadoId) {
        ChamadoEntity chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado!"));
        return chamadoHistoricoRepository.findByChamadoOrderByDataAcaoAsc(chamado);
    }
}