package com.br.SmsTi.Entity;

import com.br.SmsTi.Enum.Categorias;
import com.br.SmsTi.Enum.Prioridade;
import com.br.SmsTi.Enum.StatusChamado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "chamados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamadoEntity {

    @Id
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "memorando", nullable = false)
    private Integer memorando;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categorias categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado statusChamado;

    @ManyToOne
    @JoinColumn(name = "unidade_Id", nullable = false)
    private UnidadeEntity unidade;

    @OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioEntity> comentarios;

}
