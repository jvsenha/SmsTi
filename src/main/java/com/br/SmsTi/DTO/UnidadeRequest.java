package com.br.SmsTi.DTO;

import com.br.SmsTi.Entity.EnderecoEmbeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeRequest {

    @NotBlank(message = "O nome da unidade é obrigatório")
    private String nome;

    @Valid // Para validar os campos dentro do EnderecoEmbeddable
    @NotNull(message = "O endereço é obrigatório")
    private EnderecoEmbeddable endereco;

    @NotBlank(message = "O telefone da unidade é obrigatório")
    @Pattern(regexp = "^\\([0-9]{2}\\)[0-9]{4,5}-[0-9]{4}$", message = "Formato de telefone inválido. Use (XX)XXXXX-XXXX ou (XX)XXXX-XXXX")
    private String telefone;

    @NotBlank(message = "O nome do responsável é obrigatório")
    private String nomeResponsavel;

    @NotBlank(message = "O telefone do responsável é obrigatório")
    @Pattern(regexp = "^\\([0-9]{2}\\)[0-9]{4,5}-[0-9]{4}$", message = "Formato de telefone inválido para o responsável. Use (XX)XXXXX-XXXX ou (XX)XXXX-XXXX")
    private String telefoneResponsavel;
}