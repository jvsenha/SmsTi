package com.br.SmsTi.DTO;

import com.br.SmsTi.Enum.Categorias;
import com.br.SmsTi.Enum.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamadaRequest {
    private String titulo;
    private String descricao;
    private Categorias categoria;
    private Prioridade prioridade;
    private Long unidadeId;
    private Integer memorando;
}
