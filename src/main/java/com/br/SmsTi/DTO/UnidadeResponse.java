package com.br.SmsTi.DTO;

import com.br.SmsTi.Entity.EnderecoEmbeddable;
import com.br.SmsTi.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeResponse {
    private Long id;
    private String nome;
    private EnderecoEmbeddable endereco;
    private String telefone;
    private String nomeResponsavel;
    private String telefoneResponsavel;
    private Status status;
}