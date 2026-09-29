package org.example.service;

import org.example.dto.ObservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObservationService {
    List<ObservationDTO> findById(Long id);
}
