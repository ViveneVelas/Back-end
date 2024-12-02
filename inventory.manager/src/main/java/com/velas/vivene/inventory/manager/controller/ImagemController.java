package com.velas.vivene.inventory.manager.controller;


import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.infraService.S3BucketService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/imagem")
@RequiredArgsConstructor
@Tag(name = "Imagem Controller", description = "APIs para gerenciamento de imagens")
@CrossOrigin(origins = "http://44.192.41.78:3000")
public class ImagemController {

    private final S3BucketService service;

    @GetMapping(value = "/foto/{id}", produces = "image/jpeg")
    public ResponseEntity<byte[]> getFoto(@PathVariable Integer id) throws IOException {

        byte[] imagem = service.buscarFoto(id);

        if (imagem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(imagem);
        }
    }

    @PostMapping
    public ResponseEntity<Imagem> criar(@RequestBody @Valid Imagem imagem) {
        service.save(imagem);
        return ResponseEntity.status(201).body(imagem);
    }

    @PatchMapping(value = "/foto/{id}", consumes = "image/*")
    public ResponseEntity<Void> atualizarFoto(@PathVariable Integer id, @RequestBody byte[] referenciaArquivoFoto) {
        service.salvarFoto(id, referenciaArquivoFoto);
        return ResponseEntity.ok().build();
    }

}
