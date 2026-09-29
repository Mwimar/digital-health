package org.example.service;

import org.example.dto.EncounterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EncounterService {
    List<EncounterDTO> findByPatientId(Long id);

    EncounterDTO createEncounter(Long id, EncounterDTO encounterDTO);
}
