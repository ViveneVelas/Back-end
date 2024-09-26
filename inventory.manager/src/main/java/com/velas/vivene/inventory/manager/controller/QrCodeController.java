package com.velas.vivene.inventory.manager.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class QrCodeController {

    @GetMapping("/generate-qr")
    public ResponseEntity<byte[]> generateQRCode(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam(required = false) String dataDeCriacao
    ) throws WriterException, IOException {

        // Formatar a data se for fornecida, caso contrário usar a data atual
        if (dataDeCriacao == null || dataDeCriacao.isEmpty()) {
            dataDeCriacao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        // Formatar o texto para ser codificado no QR Code
        String qrText = String.format(
                "Titulo: %s\nDescricao: %s\nData De Criacao: %s",
                titulo, descricao, dataDeCriacao
        );

        // Gerar o QR Code
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

        // Converter o QR Code para uma imagem PNG
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pngData);
    }
}