package dev.giovannemendonca.springs3storage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.giovannemendonca.springs3storage.dtos.FileDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@Entity
@Table(name = "ATTACHMENT")
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "URL")
    private String url;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    private Candidate candidate;


    public static Attachment create(FileDTO dto, String url) {
        return Attachment.builder()
                .url(url)
                .documentType(dto.getContentType())
                .fileName(dto.getFileName())
                .build();
    }

}
