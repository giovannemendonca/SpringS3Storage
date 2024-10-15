package dev.giovannemendonca.springs3storage.models;

import dev.giovannemendonca.springs3storage.dtos.CreateCandidateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "CANDIDATE")
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "AGE")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACADEMIC_QUALIFICATION")
    private AcademicQualification academicQualification;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<>();


    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
        attachment.setCandidate(this);
    }

    public static Candidate create(CreateCandidateDTO candidateDTO) {
        return Candidate.builder()
                .name(candidateDTO.getName())
                .cpf(candidateDTO.getCpf())
                .email(candidateDTO.getEmail())
                .age(candidateDTO.getEge())
                .academicQualification(candidateDTO.getAcademicQualification())
                .attachments(new ArrayList<>())
                .build();
    }

}

