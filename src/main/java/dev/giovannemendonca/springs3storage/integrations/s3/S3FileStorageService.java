package dev.giovannemendonca.springs3storage.integrations.s3;

import dev.giovannemendonca.springs3storage.dtos.FileDTO;
import dev.giovannemendonca.springs3storage.exceptions.UploadException;
import dev.giovannemendonca.springs3storage.integrations.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3FileStorageService implements FileStorageService {


    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    public S3FileStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(FileDTO fileDTO) {

        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileDTO.getUniqueFileName())
                    .contentType(fileDTO.getContentType())
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(fileDTO.getInputStream(), fileDTO.getSize());

            s3Client.putObject(objectRequest, requestBody);
            return getFileUrl(bucketName, fileDTO.getUniqueFileName());
        } catch (S3Exception e) {
            throw new UploadException("Erro ao fazer upload para S3:");
        } catch (Exception e) {
            throw new UploadException("Erro inesperado. Tente novamente.");
        }
    }

    private String getFileUrl(String bucketName, String uniqueFileName) {
        try {
            return new URI("https://" + bucketName + ".s3.amazonaws.com/" + uniqueFileName).toString();
        } catch (URISyntaxException e) {
            return "";
        }
    }
}
