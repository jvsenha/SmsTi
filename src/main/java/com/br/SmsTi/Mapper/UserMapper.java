package com.br.SmsTi.Mapper;

import com.br.SmsTi.DTO.UserDTO;
import com.br.SmsTi.Entity.UserEntity;
import com.br.SmsTi.Enum.Status; // Importe o Enum Status
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // Torna esta classe um componente Spring para injeção
public class UserMapper {

    // Converte UserEntity para UserDTO
    public UserDTO toUserDTO(UserEntity user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        // A senha não deve ser exposta no DTO de resposta
        dto.setTelefone(user.getTelefone());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());
        return dto;
    }

    // Converte UserDTO para UserEntity (usado para criar um novo usuário)
    public UserEntity toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        // ID não é setado aqui, pois é gerado pelo banco de dados para novos usuários
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        // A senha será codificada e setada no serviço, não diretamente do DTO
        entity.setTelefone(dto.getTelefone());
        // O status e a role podem vir do DTO, com um padrão se não forem fornecidos
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.STATUS_ATIVO);
        entity.setRole(dto.getRole()); // Assumindo que a role vem no DTO de cadastro
        return entity;
    }

    // Atualiza uma UserEntity existente com dados de um UserDTO (usado para atualização)
    public void updateEntityFromDto(UserDTO dto, UserEntity entity) {
        if (dto == null || entity == null) {
            return;
        }
        // ID e Senha geralmente não são atualizados diretamente assim
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        // Status e Role só devem ser atualizados se o DTO fornecer um valor
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
        // A senha é tratada separadamente no serviço
    }

    // Converte uma lista de UserEntity para uma lista de UserDTO
    public List<UserDTO> toUserDTOList(List<UserEntity> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }
}