package com.br.SmsTi.Mapper;

import com.br.SmsTi.DTO.UserDTO;
import com.br.SmsTi.Entity.UserEntity;

public class UserMapper {
    public static UserDTO toUserDTO(UserEntity user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setTelefone(user.getTelefone());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());
        return dto;
    }


}
