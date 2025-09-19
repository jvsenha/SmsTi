package com.br.SmsTi.Enum;

public enum StatusChamado {

    PENDENTE("Pendente"),
    ARQUIVADO("Arquivado");

    private final String label;

    StatusChamado(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
