package com.velas.vivene.inventory.manager.commons;

import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.view.ClientesMaisCompras;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class LerArquivos {

    public final ClienteRepository clienteRepository;

    public void importarArquivoTxt(byte[] nomeArq) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(nomeArq), StandardCharsets.UTF_8)
            );

            List<ClientesMaisCompras> clientes = new ArrayList<>();
            String linha;

            System.out.println("\nIniciando Leitura:\n");
            while ((linha = reader.readLine()) != null) {
                String tipoRegistro = linha.substring(0, 2);

                switch (tipoRegistro) {
                    case "00":
                        // Trata o Header
                        tratarHeader(linha);
                        break;

                    case "02":
                        // Trata o Corpo (Registros de Clientes)
                        ClientesMaisCompras cliente = tratarCorpo(linha);
                        clientes.add(cliente);
                        break;

                    case "01":
                        // Trata o Trailer
                        tratarTrailer(linha, clientes.size());
                        break;

                    default:
                        throw new IllegalArgumentException("Tipo de registro desconhecido: " + tipoRegistro);
                }
            }

            reader.close();

            System.out.println("Dados importados com sucesso!");

            for (ClientesMaisCompras cliente : clientes) {
                Cliente cl = new Cliente();
                cl.setId(cliente.getId());
                cl.setNome(cliente.getNomeCliente());
                cl.setTelefone("null");
                cl.setQtdPedidos(cliente.getNumPedidos());
                clienteRepository.save(cl);
                System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNomeCliente() + ", Pedidos: " + cliente.getNumPedidos());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tratarHeader(String linha) {
        // Extrai os campos do header
        String tipoArquivo = linha.substring(2, 12).trim();
        String dataHoraGeracao = linha.substring(12, 31).trim();
        String versaoLayout = linha.substring(31, 33).trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraGeracao, formatter);

        System.out.println("Header:");
        System.out.println("Tipo de arquivo: " + tipoArquivo);
        System.out.println("Data/hora de geração: " + dataHora);
        System.out.println("Versão do layout: " + versaoLayout);
        System.out.println();
    }

    private ClientesMaisCompras tratarCorpo(String linha) {
        // Extrai os campos do corpo (registro de cliente)
        int id = Integer.parseInt(linha.substring(2, 7).trim());
        String nomeCliente = linha.substring(7, 47).trim();
        int numPedidos = Integer.parseInt(linha.substring(47, 55).trim());

        ClientesMaisCompras cliente = new ClientesMaisCompras();
        cliente.setId(id);
        cliente.setNomeCliente(nomeCliente);
        cliente.setNumPedidos(numPedidos);
        return cliente;
    }

    private void tratarTrailer(String linha, int qtdRegistrosLidos) {
        // Extrai os campos do trailer
        int qtdRegistros = Integer.parseInt(linha.substring(2, 7).trim());

        System.out.println("Trailer:");
        System.out.println("Quantidade de registros no arquivo: " + qtdRegistros);

        if (qtdRegistros != qtdRegistrosLidos) {
            System.err.println("Erro: A quantidade de registros no trailer não corresponde aos registros lidos.");
        } else {
            System.out.println("Quantidade de registros validada com sucesso.");
        }
        System.out.println();
    }
}
