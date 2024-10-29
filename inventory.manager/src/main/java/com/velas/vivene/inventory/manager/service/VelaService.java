package com.velas.vivene.inventory.manager.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.velas.vivene.inventory.manager.commons.GerarArquivosCsv;
import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.imagem.ImagemMapper;
import com.velas.vivene.inventory.manager.dto.imagem.ImagemRequestDto;
import com.velas.vivene.inventory.manager.dto.imagem.ImagemResponseDto;
import com.velas.vivene.inventory.manager.dto.top5velasmaisvendidas.Top5VelasMaisVendidasMapper;
import com.velas.vivene.inventory.manager.dto.top5velasmaisvendidas.Top5VelasMaisVendidasResponse;
import com.velas.vivene.inventory.manager.dto.vela.VelaMapper;
import com.velas.vivene.inventory.manager.dto.vela.VelaRequestDto;
import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaMapper;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaResponse;
import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.entity.view.TopCincoVelas;
import com.velas.vivene.inventory.manager.entity.view.VelaMaisVendida;
import com.velas.vivene.inventory.manager.repository.ImagemRepository;
import com.velas.vivene.inventory.manager.repository.Top5VelasMaisVendidasRepository;
import com.velas.vivene.inventory.manager.repository.VelaMaisVendidaRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VelaService {

    private final VelaRepository velaRepository;
    private final VelaMapper velaMapper;
    private final VelaMaisVendidaRepository velaMaisVendidaRepository;
    private final VelaMaisVendidaMapper velaMaisVendidaMapper;
    private final Top5VelasMaisVendidasRepository top5VelasMaisVendidasRepository;
    private final Top5VelasMaisVendidasMapper top5VelasMaisVendidasMapper;
    private final HashTableService hashTableService;
    private final ImagemService imagemService;
    private final ImagemMapper imagemMapper;
    private final ImagemRepository imagemRepository;

    @Transactional
    public VelaResponseDto createVela(String nome, String tamanho, Double preco, String descricao, MultipartFile imagem) {
        try {
            byte[] imagemBytes = imagem.getBytes(); 
    
            String prefix = LocalDateTime.now().toString();
    
            ImagemRequestDto dto = new ImagemRequestDto();
            dto.setImagem(imagemBytes); 
            dto.setReferencia(prefix + ".jpg"); 
    
            ImagemResponseDto imagemResponse = imagemService.createImagem(dto);
            Imagem imagemSalva = imagemMapper.toEntityFromResponse(imagemResponse);
    
            VelaRequestDto velaRequestDTO = new VelaRequestDto(); 
            velaRequestDTO.setNome(nome);
            velaRequestDTO.setTamanho(tamanho);
            velaRequestDTO.setPreco(preco);
            velaRequestDTO.setDescricao(descricao);
    
            Vela vela = velaMapper.toEntity(velaRequestDTO);
            vela.setFkImagem(imagemSalva.getId()); 
            Vela velas = velaRepository.save(vela);

            hashTableService.addName(velas.getNome());

            return velaMapper.toResponseDTO(velas);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar a vela.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar vela: " + ex.getMessage());
        }
    }
    

    public VelaResponseDto updateVela(Integer id, String nome, String tamanho, Double preco, String descricao, MultipartFile imagem) {
        Vela vela = velaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));
    
        if (nome == null || tamanho == null || preco == null || descricao == null) {
            throw new ValidationException("Os dados da vela são obrigatórios.");
        }
    
        try {
            Integer idImagem = vela.getFkImagem();

            Imagem imagemBuscada = imagemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));
    
            vela.setNome(nome); 
            vela.setTamanho(tamanho);
            vela.setPreco(preco);
            vela.setDescricao(descricao); 
    
            byte[] imagemBytes = imagem != null ? imagem.getBytes() : null; 
    
            if (imagemBytes != null) {
                String prefix = LocalDateTime.now().toString();
                ImagemRequestDto dto = new ImagemRequestDto();
                dto.setImagem(imagemBytes); 
                dto.setReferencia(prefix + ".jpg"); 
    
                ImagemResponseDto imagemResponse = imagemService.createImagem(dto);
                Imagem novaImagem = imagemMapper.toEntityFromResponse(imagemResponse);
            }
    
            vela = velaRepository.save(vela);
    
            return velaMapper.toResponseDTO(vela);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar a vela.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar vela: " + ex.getMessage());
        }
    }
    
    

    public void deleteVela(Integer id) {
        Vela vela = velaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));
    

        Imagem imagem = imagemRepository.findById(vela.getFkImagem())
        .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));

        imagemService.deleteImagem(imagem.getId());
        
        velaRepository.delete(vela);
    }
    

    public List<VelaResponseDto> getAllVelas() {
        List<VelaResponseDto> velas = velaRepository.findAll()
                .stream()
                .map(vela -> {
                    try {
                        return velaMapper.toResponseDTO(vela);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .collect(Collectors.toList());
    
        if (velas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma vela no banco de dados");
        }
        return velas;
    }
    

    public VelaResponseDto getVelaById(Integer id) throws IOException {
        Vela vela = velaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));
        return velaMapper.toResponseDTO(vela);
    }

    public List<VelaMaisVendidaResponse> getVelaVendida() {
        List<VelaMaisVendida> velas = velaMaisVendidaRepository.findAll();

        if (velas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma vela no banco de dados");
        }
        
        List<VelaMaisVendidaResponse> velasResponse = new ArrayList<>();

        for (VelaMaisVendida v : velas) {
            VelaMaisVendidaResponse velaR = new VelaMaisVendidaResponse();
            velaR = velaMaisVendidaMapper.toResponseDTO(v);
            velasResponse.add(velaR);
        }

        return velasResponse;
    }

    public List<Top5VelasMaisVendidasResponse> getTop5VelasVendidas() {
        List<TopCincoVelas> velas = top5VelasMaisVendidasRepository.findAll();

        if (velas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma vela no banco de dados");
        }

        List<Top5VelasMaisVendidasResponse> velasResponse = new ArrayList<>();

        for (TopCincoVelas v : velas) {
            Top5VelasMaisVendidasResponse velaR = new Top5VelasMaisVendidasResponse();
            velaR = top5VelasMaisVendidasMapper.toDto(v);
            velasResponse.add(velaR);
        }

        return velasResponse;
    }

    public List<VelaResponseDto> getVelasByName(String nome) {
        List<String> nomes = hashTableService.searchNames(nome);
    
        List<Vela> velas = velaRepository.findAllByNomeIn(nomes);
    
        if (velas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma vela no banco de dados");
        }
        return velas.stream()
                .map(vela -> {
                    try {
                        return velaMapper.toResponseDTO(vela);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao mapear Vela para VelaResponseDto", e);
                    }
                })
                .collect(Collectors.toList());
    }
    

    public byte[] criarArqCsv() throws IOException {
        List<TopCincoVelas> velas = top5VelasMaisVendidasRepository.findAll();

        return GerarArquivosCsv.gerarArquivo(velas);
    }

}
