package com.br.SmsTi.Entity;

import com.br.SmsTi.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "unidade")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da unidade é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Embedded
    private EnderecoEmbeddable endereco;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(name ="telefone", nullable = false)
    private String telefone;

    @NotBlank(message = "O nome do responsável é obrigatório")
    @Column(name = "nomeResponsavel", nullable = false)
    private String nomeResponsavel;

    @NotBlank(message = "O telefone do responsável é obrigatório")
    @Column(name = "telefoneResponsavel", nullable = false)
    private String telefoneResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.STATUS_ATIVO;

    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChamadoEntity> chamados;
}