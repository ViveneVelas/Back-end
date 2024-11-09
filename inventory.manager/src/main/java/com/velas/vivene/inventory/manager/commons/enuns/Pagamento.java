package com.velas.vivene.inventory.manager.commons.enuns;

public enum Pagamento {
    ABERTO("Aberto"),
    FINALIADO("Finalizado");

    private final String descricao;

    Pagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
