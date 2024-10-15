package dev.giovannemendonca.springs3storage.controllers;

import dev.giovannemendonca.springs3storage.dtos.CreateCandidateDTO;
import dev.giovannemendonca.springs3storage.models.Candidate;
import dev.giovannemendonca.springs3storage.services.CandidateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    @PostMapping("/create")
    public Candidate createCandidate(@ModelAttribute CreateCandidateDTO candidateDTO){
        return candidateService.createCandidate(candidateDTO);
    }



}
