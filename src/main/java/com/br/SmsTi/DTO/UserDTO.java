package com.br.SmsTi.DTO;

import com.br.SmsTi.Enum.Role;
import com.br.SmsTi.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Status status;
    private Role role;


}
