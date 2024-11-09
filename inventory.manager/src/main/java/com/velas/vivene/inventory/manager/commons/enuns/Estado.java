package com.velas.vivene.inventory.manager.commons.enuns;

public enum Estado {
    ANDAMENTO("Andamento"),
    CONCLUIDO("Concluido"),
    CANCELADO("Cancelado");

    private final String descricao;

    Estado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
