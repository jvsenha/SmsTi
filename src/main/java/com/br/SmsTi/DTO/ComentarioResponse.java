package com.br.SmsTi.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioResponse {
    private Long id;
    private String descricao;
    private String nomeUsuario;
    private LocalDate dataComentario;
}