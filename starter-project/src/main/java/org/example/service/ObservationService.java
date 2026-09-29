package org.example.service;

import org.example.dto.ObservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ObservationService {


    List<ObservationDTO> findByPatientId(Long id);

    ObservationDTO addObservation(Long id, ObservationDTO observationDTO);
}
