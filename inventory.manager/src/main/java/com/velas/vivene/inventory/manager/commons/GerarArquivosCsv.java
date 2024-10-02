package com.velas.vivene.inventory.manager.commons;

import com.velas.vivene.inventory.manager.entity.view.TopCincoVelas;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GerarArquivosCsv {

    private static String gerarHeader() {
        String tipoRegistro = "00";
        String tipoArquivo = "VELAS";
        String dataHoraGeracao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String versaoLayout = "01";

        return String.format("%-2s%-5s%-19s%-2s", tipoRegistro, tipoArquivo, dataHoraGeracao, versaoLayout);
    }


    private static String gerarCorpo(List<TopCincoVelas> velas) {
        StringBuilder corpo = new StringBuilder();
        for (TopCincoVelas vela : velas) {
            String tipoRegistro = "02";
            String id = String.format("%05d", vela.getId());
            String nome = String.format("%-30s", vela.getNomeVela());
            String totalVendido = String.format("%05d", vela.getTotalVendido());

            corpo.append(String.format("%-2s%-5s%-30s%-5s", tipoRegistro, id, nome, totalVendido)).append("\n");
        }
        return corpo.toString();
    }


    private static String gerarTrailer(int totalRegistros) {
        String tipoRegistro = "01";
        String quantidadeRegistros = String.format("%05d", totalRegistros);

        return String.format("%-2s%-5s", tipoRegistro, quantidadeRegistros);
    }


    public static byte[] gerarArquivo(List<TopCincoVelas> velas) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            writer.write(gerarHeader());
            writer.newLine();

            // Escreve o corpo
            writer.write(gerarCorpo(velas));

            // Escreve o trailer(
            writer.write(gerarTrailer(velas.size()));

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
