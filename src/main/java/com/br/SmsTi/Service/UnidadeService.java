package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.UnidadeRequest;
import com.br.SmsTi.DTO.UnidadeResponse;
import com.br.SmsTi.Entity.UnidadeEntity;
import com.br.SmsTi.Enum.Status;
import com.br.SmsTi.Exception.ResourceNotFoundException; // Importe a exceção customizada
import com.br.SmsTi.Mapper.UnidadeMapper;
import com.br.SmsTi.Repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private UnidadeMapper unidadeMapper;

    // Método privado para evitar repetição de código
    private void updateUnidadeFromRequest(UnidadeEntity unidade, UnidadeRequest dto) {
        unidade.setNome(dto.getNome());
        unidade.setTelefone(dto.getTelefone());
        unidade.setNomeResponsavel(dto.getNomeResponsavel());
        unidade.setTelefoneResponsavel(dto.getTelefoneResponsavel());
        unidade.setEndereco(dto.getEndereco());
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public UnidadeResponse cadastrar(UnidadeRequest dto) {
        UnidadeEntity unidade = new UnidadeEntity();
        updateUnidadeFromRequest(unidade, dto); // Usa o método privado
        unidade.setStatus(Status.STATUS_ATIVO);

        UnidadeEntity savedUnidade = unidadeRepository.save(unidade);
        return unidadeMapper.toResponse(savedUnidade);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel2(authentication)")
    public UnidadeResponse atualizar(Long id, UnidadeRequest dto) {
        UnidadeEntity unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade com ID " + id + " não encontrada."));

        updateUnidadeFromRequest(unidade, dto); // Usa o método privado

        UnidadeEntity updatedUnidade = unidadeRepository.save(unidade);
        return unidadeMapper.toResponse(updatedUnidade);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication)")
    public void deletarUnidade(Long id) {
        if (!unidadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Unidade com ID " + id + " não encontrada para deleção.");
        }
        unidadeRepository.deleteById(id);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public UnidadeResponse buscarUnidade(Long id) {
        UnidadeEntity unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade com ID " + id + " não encontrada."));
        return unidadeMapper.toResponse(unidade);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public List<UnidadeResponse> listarTodos() {
        List<UnidadeEntity> unidades = unidadeRepository.findAll();
        return unidadeMapper.toResponseList(unidades);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public List<UnidadeResponse> listarAtivos() { // Corrigido para retornar DTO
        List<UnidadeEntity> unidades = unidadeRepository.findByStatus(Status.STATUS_ATIVO);
        return unidadeMapper.toResponseList(unidades);
    }

    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public List<UnidadeResponse> listarInativos() { // Corrigido para retornar DTO
        List<UnidadeEntity> unidades = unidadeRepository.findByStatus(Status.STATUS_INATIVO);
        return unidadeMapper.toResponseList(unidades);
    }

    @Transactional
    @PreAuthorize("@securityService.isNivel3(authentication) or @securityService.isNivel1(authentication)")
    public UnidadeResponse alternarStatus(Long id) {
        UnidadeEntity unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade com ID " + id + " não encontrada."));

        unidade.setStatus(
                unidade.getStatus() == Status.STATUS_ATIVO ? Status.STATUS_INATIVO : Status.STATUS_ATIVO
        );

        unidadeRepository.save(unidade);
        return unidadeMapper.toResponse(unidade);
    }
}