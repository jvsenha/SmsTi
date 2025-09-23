package com.br.SmsTi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamadoHistoricoResponse {
    private Long id;
    private Long chamadoId;
    private String tipoAcao;
    private String usuarioAcao;
    private LocalDateTime dataAcao;
    private String detalhes;
}
