package com.br.SmsTi.Service;

import com.br.SmsTi.DTO.UnidadeRequest;
import com.br.SmsTi.DTO.UnidadeResponse;
import com.br.SmsTi.Entity.UnidadeEntity;
import com.br.SmsTi.Enum.Status;
import com.br.SmsTi.Mapper.UnidadeMapper;
import com.br.SmsTi.Repository.UnidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private UnidadeMapper unidadeMapper;

    @Transactional
    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncAdm(authentication)")
    public UnidadeResponse cadastrar(UnidadeRequest dto) {
        UnidadeEntity unidade = new UnidadeEntity();
        unidade.setNome(dto.getNome());
        unidade.setTelefone(dto.getTelefone());
        unidade.setStatus(Status.STATUS_ATIVO);
        unidade.setNomeResponsavel(dto.getNomeResponsavel());
        unidade.setTelefoneResponsavel(dto.getTelefoneResponsavel());
        unidade.setEndereco(dto.getEndereco());

        unidadeRepository.save(unidade);

        return unidadeMapper.toResponse(unidade);
    }
    @Transactional
    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncAdm(authentication)")
    public UnidadeResponse atualizar(Long id, UnidadeRequest dto) {
        UnidadeEntity unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrado"));

        unidade.setNome(dto.getNome());
        unidade.setTelefone(dto.getTelefone());
        unidade.setStatus(Status.STATUS_ATIVO);
        unidade.setNomeResponsavel(dto.getNomeResponsavel());
        unidade.setTelefoneResponsavel(dto.getTelefoneResponsavel());
        unidade.setEndereco(dto.getEndereco());


        unidadeRepository.save(unidade);

        return unidadeMapper.toResponse(unidade);
    }


    @Transactional
    @PreAuthorize("@securityService.isAdmin(authentication)")
    public void deletarUnidade(Long id) {
        unidadeRepository.deleteById(id);
    }

    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncionario(authentication)")
    public UnidadeEntity buscarUnidade(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrado"));
    }

    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncionario(authentication)")
    public List<UnidadeEntity> listarTodos() {
        return unidadeRepository.findAll();
    }

    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncionario(authentication)")
    public List<UnidadeEntity> listarAtivos() {
        return unidadeRepository.findByStatus(Status.STATUS_ATIVO);
    }

    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncionario(authentication)")
    public List<UnidadeEntity> listarInativos() {
        return unidadeRepository.findByStatus(Status.STATUS_INATIVO);
    }

    @Transactional
    @PreAuthorize("@securityService.isAdmin(authentication) or @securityService.isFuncionario(authentication)")
    public Status alternarStatus(Long id) {
        UnidadeEntity unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrado"));

        if (unidade.getStatus() == Status.STATUS_ATIVO) {
            unidade.setStatus(Status.STATUS_INATIVO);
        } else {
            unidade.setStatus(Status.STATUS_ATIVO);
        }

        unidadeRepository.save(unidade);
        return unidade.getStatus();
    }




}
