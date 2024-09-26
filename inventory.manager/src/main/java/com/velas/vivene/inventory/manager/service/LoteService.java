package com.velas.vivene.inventory.manager.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximoDoVencimentoResponse;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximosDoVencimentoMapper;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.LotesProximoDoVencimento;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.LotesProximoDoVencimentoRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoteService {

//    private final String bucketName = "terraform-20240911223610647100000001";
//    private final S3Client s3 = S3Client.builder().region(Region.US_EAST_1).build();

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final VelaRepository velaRepository;
    private final LotesProximoDoVencimentoRepository lotesProximoDoVencimentoRepository;
    private final LotesProximosDoVencimentoMapper lotesProximosDoVencimentoMapper;

    public LoteResponseDto criarLote(LoteRequestDto loteRequestDTO) throws IOException, WriterException {
        Vela vela = velaRepository.findById(loteRequestDTO.getFkVela()).orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + loteRequestDTO.getFkVela()));

        Lote lote = loteMapper.toEntity(loteRequestDTO);
        lote.setVela(vela);
        lote = loteRepository.save(lote);

        String nomeArq = "qrcode-id-" + lote.getId() + ".png";

        byte[] qrcode = gerarQrCode(nomeArq, vela.getNome(), vela.getDescricao(), LocalDate.now());

        lote.setNomeQrCode(nomeArq);
        lote = loteRepository.save(lote);

        return loteMapper.toResponseDTO(lote, qrcode);
    }

    private byte[] gerarQrCode(String nomeArq, String titulo, String descricao, LocalDate dataDeCriacao) throws WriterException, IOException {

        // Formatar o texto
        String qrText = String.format("Titulo: %s\nDescricao: %s\nData De Criacao: %s", titulo, descricao, dataDeCriacao);

        // Crio o Qr Code
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

        // Converter o QR Code para uma imagem PNG
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        // Salvar PNG no S3
        /*
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nomeArq)
                    .contentType("image/jpeg")
                    .build();

            s3.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(pngData));

            String caminhoArquivoS3 = nomeArq;

        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        return pngData;
    }

    public List<LoteResponseDto> listarLotes() {
        return loteRepository.findAll().stream().map(loteMapper::toResponseDTO).toList();
    }


    public LoteResponseDto obterLotePorId(Integer id) {
        Lote lote = loteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));
        return loteMapper.toResponseDTO(lote
        );
    }

    public LoteResponseDto updateLote(Integer id, LoteRequestDto loteRequestDTO) {
        Lote lote = loteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));

        lote.setQuantidade(loteRequestDTO.getQuantidade());
        lote.setDataFabricacao(loteRequestDTO.getDataFabricacao());
        lote.setDataValidade(loteRequestDTO.getDataValidade());
        lote.setLocalizacao(loteRequestDTO.getLocalizacao());

        Vela vela = velaRepository.findById(loteRequestDTO.getFkVela()).orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + loteRequestDTO.getFkVela()));
        lote.setVela(vela);

        lote = loteRepository.save(lote);
        return loteMapper.toResponseDTO(lote);
    }

    public void excluirLote(Integer id) {
        loteRepository.deleteById(id);
    }

    public List<LotesProximoDoVencimentoResponse> getLotesVencimento() {
        List<LotesProximoDoVencimento> lotes = lotesProximoDoVencimentoRepository.findAll();
        List<LotesProximoDoVencimentoResponse> lotesResponse = new ArrayList<>();

        for (LotesProximoDoVencimento l : lotes) {
            LotesProximoDoVencimentoResponse lotesR = new LotesProximoDoVencimentoResponse();
            lotesR = lotesProximosDoVencimentoMapper.toResponseDto(l);
            lotesResponse.add(lotesR);
        }

        return lotesResponse;
    }
}