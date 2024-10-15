package dev.giovannemendonca.springs3storage.dtos;

import dev.giovannemendonca.springs3storage.exceptions.FileProcessingException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Getter
public class FileDTO {

    private String fileName;

    private String uniqueFileName;

    private String contentType;

    private Long size;

    private InputStream inputStream;

    public FileDTO() {
    }

    public FileDTO(String fileName, String uniqueFileName, String contentType, Long size, InputStream inputStream) {
        this.fileName = fileName;
        this.uniqueFileName = uniqueFileName;
        this.contentType = contentType;
        this.size = size;
        this.inputStream = inputStream;
    }

    public static FileDTO create(MultipartFile file) {
        try {

            InputStream inputStream = file.getInputStream();
            var filename = file.getOriginalFilename();
            var uniqueFileName = UUID.randomUUID() + filename;
            var contentType = file.getContentType();
            var size = file.getSize();
            return new FileDTO(filename, uniqueFileName, contentType, size, inputStream);
        } catch (IOException e) {
            throw new FileProcessingException("Erro ao ler o arquivo. Tente novamente.");
        }

    }

}
