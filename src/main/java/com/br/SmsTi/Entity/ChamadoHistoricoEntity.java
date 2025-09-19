package com.br.SmsTi.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chamado_historico")
public class ChamadoHistoricoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unidadeId", nullable = false)
    private ChamadoEnitity chamado;

    @Column(nullable = false)
    private String tipoAcao; // Ex: "Arquivado", "Atualizado", "Transferido"

    @Column(nullable = false)
    private String usuarioAcao; // Nome ou e-mail do usuário que realizou a ação

    @Column(nullable = false)
    private LocalDateTime dataAcao;

    @Column
    private String detalhes; // Opcional: para registrar detalhes da alteração
}