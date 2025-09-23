package com.br.SmsTi.Entity;

import com.br.SmsTi.Enum.Categorias;
import com.br.SmsTi.Enum.Prioridade;
import com.br.SmsTi.Enum.StatusChamado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chamados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "memorando", nullable = false)
    private Integer memorando;

    @Enumerated(EnumType.STRING) // Armazenará a String (ex: "HARDWARE") no banco de dados
    @Column(name = "categoria", nullable = false) // Nome da coluna no banco
    private Categorias categoria;

    @Enumerated(EnumType.STRING)
    @Column(name ="prioridade", nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(name ="statusChamado", nullable = false)
    private StatusChamado statusChamado;

    @ManyToOne(fetch = FetchType.LAZY) // Adicionado fetch = FetchType.LAZY
    @JoinColumn(name = "unidade_Id", nullable = false)
    private UnidadeEntity unidade;

    @OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // Adicionado fetch = FetchType.LAZY
    private List<ComentarioEntity> comentarios;

    @Column( name = "dataCriacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @Column( name = "dataConclusao")
    private LocalDateTime dataConclusao;
}