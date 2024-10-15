package dev.giovannemendonca.springs3storage.repositories;

import dev.giovannemendonca.springs3storage.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
}
