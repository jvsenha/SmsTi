package com.br.SmsTi.DTO;

import com.br.SmsTi.Enum.Categorias;
import com.br.SmsTi.Enum.Prioridade;
import com.br.SmsTi.Enum.StatusChamado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamadoResponse {
    private Long Id;
    private String titulo;
    private String descricao;
    private Categorias categoria;
    private Prioridade prioridade;
    private Long unidadeId;
    private String unidadeName;
    private Integer memorando;
    private StatusChamado statusChamado;
}
