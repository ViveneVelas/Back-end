package com.velas.vivene.inventory.manager.infraService;

import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.repository.ImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class S3BucketService {

    Region region = Region.US_EAST_1;
    S3Client s3 = S3Client.builder().region(region).build();

    private final ImagemRepository repository;

    public void salvarFoto(Long id, byte[] referenciaArquivoFoto) {
        LocalDateTime prefix = LocalDateTime.now();
        repository.updateReferenciaArquivoFoto(id, prefix + ".jpg");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("vivanevelas-s3-bucket")
                .key( prefix + ".jpg")
                .build();

        software.amazon.awssdk.core.sync.RequestBody requestBody = software.amazon.awssdk.core.sync.RequestBody.fromBytes(referenciaArquivoFoto);
        s3.putObject(putObjectRequest, requestBody);
    }

    public byte[] buscarFoto(Long id) throws IOException {
        if (!repository.existsById(id)) {
            return null;
        }
        String referenciaArquivoFoto = repository.findReferenciaArquivoFotoById(id);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("vivanevelas-s3-bucket")
                .key(referenciaArquivoFoto)
                .build();

        byte[] byteArray = s3.getObjectAsBytes(getObjectRequest).asByteArray();
        Files.write(Paths.get("./download.jpg"), byteArray);
        return byteArray;
    }

    public void save(Imagem imagem) {
        repository.save(imagem);
    }

}
