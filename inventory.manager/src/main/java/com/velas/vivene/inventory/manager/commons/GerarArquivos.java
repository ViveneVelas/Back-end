package com.velas.vivene.inventory.manager.commons;

import com.velas.vivene.inventory.manager.entity.ClientesMaisCompras;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GerarArquivos {

    public static void gerarArquivoTxt(String nomeArq, List<ClientesMaisCompras> clientes) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArq));

            String header = gerarHeader();
            writer.write(header);
            writer.newLine();

            for (ClientesMaisCompras cliente : clientes) {
                String corpo = gerarCorpo(cliente);
                writer.write(corpo);
                writer.newLine();
            }

            String trailer = gerarTrailer(clientes.size());
            writer.write(trailer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String gerarHeader() {
        String tipoRegistro = "00";
        String tipoArquivo = "CLIENTES";
        String dataHoraGeracao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String versaoLayout = "01";

        return String.format("%-2s%-10s%-19s%-2s", tipoRegistro, tipoArquivo, dataHoraGeracao, versaoLayout);
    }

    private static String gerarCorpo(ClientesMaisCompras cliente) {
        String tipoRegistro = "02";
        String idCliente = String.format("%05d", cliente.getId());
        String nomeCliente = String.format("%-40s", cliente.getNomeCliente());
        String numeroPedidos = String.format("%08d", cliente.getNumPedidos());

        return String.format("%-2s%-5s%-40s%-8s", tipoRegistro, idCliente, nomeCliente, numeroPedidos);
    }

    private static String gerarTrailer(int quantidadeRegistros) {
        String tipoRegistro = "01";
        String qtdRegistros = String.format("%05d", quantidadeRegistros);

        return String.format("%-2s%-5s", tipoRegistro, qtdRegistros);
    }
}
