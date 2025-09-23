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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id", nullable = false)
    private ChamadoEntity chamado;

    @Column(nullable = false)
    private String tipoAcao; // Ex: "Arquivado", "Atualizado"

    @Column(nullable = false)
    private String usuarioAcao; // Nome ou e-mail do usuário

    @Column(nullable = false)
    private LocalDateTime dataAcao;

    @Column(length = 2000) // Exemplo de um limite de tamanho
    private String detalhes;
}