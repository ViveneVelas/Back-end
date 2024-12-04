package com.velas.vivene.inventory.manager.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velas.vivene.inventory.manager.commons.exceptions.*;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximoDoVencimentoResponse;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximosDoVencimentoMapper;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.entity.view.LotesProximoDoVencimento;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.LotesProximoDoVencimentoRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoteService {

    Region region = Region.US_EAST_1;
    S3Client s3 = S3Client.builder().region(region).build();

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final VelaRepository velaRepository;
    private final LotesProximoDoVencimentoRepository lotesProximoDoVencimentoRepository;
    private final LotesProximosDoVencimentoMapper lotesProximosDoVencimentoMapper;

    public LoteResponseDto criarLote(LoteRequestDto loteRequestDTO) throws IOException {
        Vela vela = velaRepository.findById(loteRequestDTO.getFkVela())
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + loteRequestDTO.getFkVela()));

        Lote lote = loteMapper.toEntity(loteRequestDTO);
        lote.setVela(vela);
        lote = loteRepository.save(lote);

        String nomeArq = "qrcode-id-" + lote.getId() + ".png";

        String referenciaDoQrCode = lambdaConnection(nomeArq, vela.getNome(), vela.getDescricao(), LocalDate.now(), lote.getId());

        lote.setCodigoQrCode(referenciaDoQrCode);
        lote = loteRepository.save(lote);

        return loteMapper.toResponseDTO(lote);
    }


    public String lambdaConnection(String titulo, String descricao, String velaDescricao, LocalDate dataDeCriacao, Integer id) {
        String funcao = "gerador-de-qrcode";
        Region region = Region.US_EAST_1;
        String value = null;

        LambdaClient awsLambda = LambdaClient.builder().region(region).build();

        ObjectMapper objectMapper = new ObjectMapper();

        InvokeResponse res = null;
        try {
            Map<String, String> dadosDoLote = new HashMap<>();
            if (titulo != null) dadosDoLote.put("titulo", titulo);
            if (descricao != null) dadosDoLote.put("descricao", descricao);
            if (dataDeCriacao != null) dadosDoLote.put("dataDeCriacao", dataDeCriacao.toString());
            if (id != null) dadosDoLote.put("id", id.toString());

            SdkBytes payload = SdkBytes.fromUtf8String(objectMapper.writeValueAsString(dadosDoLote));

            InvokeRequest request = InvokeRequest.builder()
                    .functionName(funcao)
                    .payload(payload)
                    .build();

            res = awsLambda.invoke(request);

            value = res.payload().asUtf8String();
            value = value.replace("\"", "");

        } catch (LambdaException | JsonProcessingException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        awsLambda.close();
        return value;
    }

    public List<LoteResponseDto> listarLotes() {
        List<LoteResponseDto> lotes = loteRepository.findAll().stream().map(vela -> {
                    try {
                        return loteMapper.toResponseDTO(vela);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .toList();

        if (lotes.isEmpty()) {
            throw new NoContentException("Não existe nenhum lote no banco de dados");
        }

        return lotes;
    }

    public List<LoteResponseDto> listarLoteCasa() {
        List<LoteResponseDto> lotes = loteRepository.findAllByLocalizacao(0)
                .stream()
                .map(lote -> {
                    try {
                        return loteMapper.toResponseDTO(lote);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .toList();

        if (lotes.isEmpty()) {
            throw new NoContentException("Não existe nenhum lote no banco de dados");
        }

        return lotes;

    }

    public List<LoteResponseDto> listarLoteEstudio() {
        List<LoteResponseDto> lotes = loteRepository.findAllByLocalizacao(1)
                .stream()
                .map(lote -> {
                    try {
                        return loteMapper.toResponseDTO(lote);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .toList();
        if (lotes.isEmpty()) {
            throw new NoContentException("Não existe nenhum lote no banco de dados");
        }

        return lotes;
    }

    public List<List<LoteResponseDto>> getNomeBusca(String nome) {
        List<LoteResponseDto> lotesCasa = loteRepository.findByNameIgnoreCaseCasa(nome)
                .stream()
                .map(lote -> {
                    try {
                        return loteMapper.toResponseDTO(lote);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .toList();

        List<LoteResponseDto> lotesEstudio = loteRepository.findByNameIgnoreCaseEstudio(nome)
                .stream()
                .map(lote -> {
                    try {
                        return loteMapper.toResponseDTO(lote);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .toList();

        List<List<LoteResponseDto>> returnList = new ArrayList<>();
        returnList.add(lotesCasa);
        returnList.add(lotesEstudio);

        return returnList;
    }

    public LoteResponseDto obterLotePorId(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));

        try {
            return loteMapper.toResponseDTO(lote);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
        }
    }

    public LoteResponseDto updateLote(Integer id, LoteRequestDto loteRequestDTO) {
return null;
//        if (loteRequestDTO == null) {
//            throw new ValidationException("Os dados do lote são obrigatórios.");
//        }
//
//        try {
//            Lote lote = loteRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));
//
//            lote.setQuantidade(loteRequestDTO.getQuantidade());
//            lote.setDataFabricacao(loteRequestDTO.getDataFabricacao());
//            lote.setDataValidade(loteRequestDTO.getDataValidade());
//            lote.setLocalizacao(loteRequestDTO.getLocalizacao());
//
//            Vela vela = velaRepository.findById(loteRequestDTO.getFkVela())
//                    .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + loteRequestDTO.getFkVela()));
//            lote.setVela(vela);
//
//            lote = loteRepository.save(lote);
//            return loteMapper.toResponseDTO(lote);
//
//        } catch (DataIntegrityViolationException ex) {
//            throw new CustomDataIntegrityViolationException("Erro de integridade ao atualizar o lote: " + ex.getMessage());
//        } catch (Exception ex) {
//            if (!(ex instanceof ResourceNotFoundException)) {
//                throw new UnexpectedServerErrorException("Erro inesperado ao atualizar o lote: " + ex.getMessage());
//            }
//            throw ex;
//        }
    }
    

    public void excluirLote(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));
        loteRepository.delete(lote);
    }

    public List<LotesProximoDoVencimentoResponse> getLotesVencimento() {
        try {
            List<LotesProximoDoVencimento> lotes = lotesProximoDoVencimentoRepository.findAll();
            List<LotesProximoDoVencimentoResponse> lotesResponse = new ArrayList<>();

            if (lotes.isEmpty()) {
                throw new NoContentException("Não existe nenhum lote no banco de dados");
            }

            for (LotesProximoDoVencimento l : lotes) {
                LotesProximoDoVencimentoResponse lotesR = new LotesProximoDoVencimentoResponse();
                lotesR = lotesProximosDoVencimentoMapper.toResponseDto(l);
                lotesResponse.add(lotesR);
            }

            return lotesResponse;
        } catch (NoContentException ex) {
            throw new NoContentException("Nenhum lote próximo do vencimento foi encontrado.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao buscar os lotes próximos do vencimento.");
        }
    }

    public byte[] getQrCode(Integer id) throws IOException  {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("s3lab06")
                .key("qr_codes/qrcode-id-" + id + ".jpg")
                .build();

        byte[] byteArray = s3.getObjectAsBytes(getObjectRequest).asByteArray();
        Files.write(Paths.get("./download.jpg"), byteArray);
        return byteArray;
    }

    public void vendaLote(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));

        Integer velasDisponivies = lote.getQuantidade();
        Integer novaDisponibilidade = velasDisponivies - 1;

        loteRepository.updateQuantidade(id, novaDisponibilidade);
    }
}