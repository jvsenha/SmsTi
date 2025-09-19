package com.br.SmsTi.Mapper;

import com.br.SmsTi.DTO.UnidadeResponse;
import com.br.SmsTi.Entity.UnidadeEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UnidadeMapper {


    public UnidadeResponse toResponse(UnidadeEntity entity) {
        if (entity == null) {
            return null;
        }
        UnidadeResponse response = new UnidadeResponse();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setEndereco(entity.getEndereco());
        response.setTelefone(entity.getTelefone());
        response.setNomeResponsavel(entity.getNomeResponsavel());
        response.setTelefoneResponsavel(entity.getTelefoneResponsavel());
        response.setStatus(entity.getStatus());

        return response;
    }


    public List<UnidadeResponse> toResponseList(List<UnidadeEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
