package com.br.SmsTi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioRequest {
    @NotBlank(message = "A descrição do comentário é obrigatória.")
    @Size(max = 1000, message = "A descrição do comentário não pode exceder 1000 caracteres.")
    private String descricao;
}