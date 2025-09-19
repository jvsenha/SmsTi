package com.br.SmsTi.Entity;

import com.br.SmsTi.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unidade")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeEntity {

    @Id
    public Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Embedded
    private EnderecoEmbeddable endereco;

    @Column(name ="telefone", nullable = false)
    private String telefone;

    @Column(name = "nomeResponsavel", nullable = false)
    private String nomeResponsavel;

    @Column(name ="telefoneResponsavel", nullable = false)
    private String telefoneResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.STATUS_ATIVO;
}
