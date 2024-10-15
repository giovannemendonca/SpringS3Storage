package dev.giovannemendonca.springs3storage.integrations;

import dev.giovannemendonca.springs3storage.dtos.FileDTO;

public interface FileStorageService {

    String uploadFile(FileDTO fileDTO);
}
