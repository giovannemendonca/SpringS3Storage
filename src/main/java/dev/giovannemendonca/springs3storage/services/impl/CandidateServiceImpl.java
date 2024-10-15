package dev.giovannemendonca.springs3storage.services.impl;

import dev.giovannemendonca.springs3storage.dtos.CreateCandidateDTO;
import dev.giovannemendonca.springs3storage.dtos.FileDTO;
import dev.giovannemendonca.springs3storage.exceptions.DatabaseException;
import dev.giovannemendonca.springs3storage.exceptions.FileProcessingException;
import dev.giovannemendonca.springs3storage.integrations.FileStorageService;
import dev.giovannemendonca.springs3storage.models.Attachment;
import dev.giovannemendonca.springs3storage.models.Candidate;
import dev.giovannemendonca.springs3storage.repositories.CandidateRepository;
import dev.giovannemendonca.springs3storage.services.CandidateService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final FileStorageService fileStorageService;

    private final CandidateRepository candidateRepository;


    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpeg", ".jpg", ".png", ".pdf");

    private static final long MAX_FILE_SIZE = 20L * 1024L * 1024L; // 20 MB

    public CandidateServiceImpl(FileStorageService fileStorageService, CandidateRepository candidateRepository) {
        this.fileStorageService = fileStorageService;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate createCandidate(CreateCandidateDTO candidateDTO) {

        var fileDto = FileDTO.create(candidateDTO.getFile());

        validateFile(fileDto);

        var url = fileStorageService.uploadFile(fileDto);

        Attachment attachment = Attachment.create(fileDto, url);

        Candidate candidate = Candidate.create(candidateDTO);
        candidate.addAttachment(attachment);
        try {
            return candidateRepository.save(candidate);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Erro ao salvar o candidato no banco de dados");
        }
    }

    private void validateFile(FileDTO fileDTO) {
        if (fileDTO == null) {
            throw new FileProcessingException("O arquivo não pode ser nulo.");
        }
        if (fileDTO.getSize() > MAX_FILE_SIZE) {
            throw new FileProcessingException("O arquivo não pode exceder 20 MB.");
        }
        String fileExtension = getFileExtensionWithDot(fileDTO.getFileName());
        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new FileProcessingException("Formato de arquivo não suportado. Apenas arquivos "
                    + ALLOWED_EXTENSIONS + " são permitidos.");
        }

    }


    private String getFileExtensionWithDot(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }


}
