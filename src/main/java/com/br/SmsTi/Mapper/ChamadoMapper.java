package com.br.SmsTi.Mapper;

import com.br.SmsTi.DTO.ChamadoRequest;
import com.br.SmsTi.DTO.ChamadoResponse;
import com.br.SmsTi.Entity.ChamadoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChamadoMapper {

    public ChamadoEntity toEntity(ChamadoRequest request) {
        if (request == null) {
            return null;
        }
        ChamadoEntity entity = new ChamadoEntity();
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setMemorando(request.getMemorando());
        entity.setCategoria(request.getCategoria());
        entity.setPrioridade(request.getPrioridade());
        return entity;
    }

    public ChamadoResponse toResponse(ChamadoEntity entity) {
        if (entity == null) {
            return null;
        }
        ChamadoResponse response = new ChamadoResponse();
        response.setId(entity.getId());
        response.setTitulo(entity.getTitulo());
        response.setDescricao(entity.getDescricao());
        response.setMemorando(entity.getMemorando());
        response.setCategoria(entity.getCategoria());
        response.setPrioridade(entity.getPrioridade());
        response.setDataCriacao(entity.getDataCriacao()); // Novo campo
        response.setDataConclusao(entity.getDataConclusao()); // Novo campo

        if (entity.getUnidade() != null) {
            response.setUnidadeId(entity.getUnidade().getId());
            response.setUnidadeName(entity.getUnidade().getNome());
        }

        response.setStatusChamado(entity.getStatusChamado());

        return response;
    }

    public List<ChamadoResponse> toResponseList(List<ChamadoEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(ChamadoRequest request, ChamadoEntity entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setMemorando(request.getMemorando());
        entity.setCategoria(request.getCategoria());
        entity.setPrioridade(request.getPrioridade());
    }
}