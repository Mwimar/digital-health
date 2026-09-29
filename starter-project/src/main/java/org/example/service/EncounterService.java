package org.example.service;

import org.example.dto.EncounterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EncounterService {
    List<EncounterDTO> findBy(Long id);
}
