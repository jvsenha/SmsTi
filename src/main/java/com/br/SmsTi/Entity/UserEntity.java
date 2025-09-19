package com.br.SmsTi.Entity;

import com.br.SmsTi.Enum.Role;
import com.br.SmsTi.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private Long id;

    @Column(name ="nome", nullable = false )
    private String nome;

    @Email
    @Column(name ="email", nullable = false, unique = true )
    private String email;

    @Column(name ="senha", nullable = false )
    private String senha;

    @Column(name ="telefone", nullable = false, unique = true )
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.STATUS_ATIVO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


}
