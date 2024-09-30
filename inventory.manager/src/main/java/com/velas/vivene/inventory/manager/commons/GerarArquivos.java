package com.velas.vivene.inventory.manager.commons;

import com.velas.vivene.inventory.manager.entity.view.ClientesMaisCompras;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GerarArquivos {

    public static byte[] gerarArquivoTxt(List<ClientesMaisCompras> clientes) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

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

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
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
