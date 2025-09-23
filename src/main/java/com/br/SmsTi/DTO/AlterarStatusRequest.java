package com.br.SmsTi.DTO;

import com.br.SmsTi.Enum.StatusChamado;
import lombok.Data;

@Data
public class AlterarStatusRequest {
    private StatusChamado novoStatus;
    private String acao;
    private String mensagem;
}
