package dev.giovannemendonca.springs3storage.services;

import dev.giovannemendonca.springs3storage.dtos.CreateCandidateDTO;
import dev.giovannemendonca.springs3storage.models.Candidate;

public interface CandidateService {

    Candidate createCandidate(CreateCandidateDTO candidateDTO);
}
