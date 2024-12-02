package com.velas.vivene.inventory.manager.infraService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.repository.ImagemRepository;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3BucketService {

    Region region = Region.US_EAST_1;
    S3Client s3 = S3Client.builder().region(region).build();

    private final ImagemRepository repository;

    public void salvarFoto(Integer id, byte[] referenciaArquivoFoto) {
        String prefix = LocalDateTime.now().toString();
        repository.updateReferenciaArquivoFoto(id, prefix + ".jpg");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("vivene-velas-s3-bucket")
                .key( prefix + ".jpg")
                .build();

        software.amazon.awssdk.core.sync.RequestBody requestBody = software.amazon.awssdk.core.sync.RequestBody.fromBytes(referenciaArquivoFoto);
        s3.putObject(putObjectRequest, requestBody);
    }

    public byte[] buscarFoto(Integer id) throws IOException {
        if (!repository.existsById(id)) {
            return null;
        }
        String referenciaArquivoFoto = repository.findReferenciaArquivoFotoById(id);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("vivene-velas-s3-bucket")
                .key(referenciaArquivoFoto)
                .build();

        byte[] byteArray = s3.getObjectAsBytes(getObjectRequest).asByteArray();
        Files.write(Paths.get("./download.jpg"), byteArray);
        return byteArray;
    }

    public void deletarFoto(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Imagem com o ID " + id + " não existe.");
        }

        String referenciaArquivoFoto = repository.findReferenciaArquivoFotoById(id);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket("vivene-velas-s3-bucket")
                .key(referenciaArquivoFoto)
                .build();

        s3.deleteObject(deleteObjectRequest);
    }

    public void save(Imagem imagem) {
        repository.save(imagem);
    }

}
