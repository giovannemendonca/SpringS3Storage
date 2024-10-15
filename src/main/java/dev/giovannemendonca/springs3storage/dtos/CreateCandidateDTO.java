package dev.giovannemendonca.springs3storage.dtos;

import dev.giovannemendonca.springs3storage.models.AcademicQualification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateDTO {

    private String name;

    private String cpf;

    private String email;

    private Integer ege;

    private AcademicQualification academicQualification;

    private MultipartFile file;

}
