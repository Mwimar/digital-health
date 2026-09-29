package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.dto.ObservationDTO;
import org.example.entity.Encounter;
import org.example.entity.Observation;
import org.example.entity.Patient;
import org.example.repository.EncounterRepository;
import org.example.repository.ObservationRepository;
import org.example.repository.PatientRepository;
import org.example.service.ObservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObservationServiceImpl implements ObservationService {



    private final ObservationRepository observationRepository;
    private final PatientRepository patientRepository;
    private final EncounterRepository encounterRepository;

    @Override
    public List<ObservationDTO> findByPatientId(Long patientId) {

        return observationRepository
                .findByPatientIdOrderByEffectiveDateTimeDesc(patientId)
                .stream()
                .map(this::mapToObservationDTO)
                .toList();
    }

    @Override
    public ObservationDTO addObservation(
            Long patientId,
            ObservationDTO observationDTO) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found with id: " + patientId
                        )
                );

        Observation observation = new Observation();

        observation.setPatient(patient);
        observation.setCode(observationDTO.getCode());
        observation.setValue(observationDTO.getValue());
        observation.setEffectiveDateTime(
                observationDTO.getEffectiveDateTime()
        );

        // Encounter is optional
        if (observationDTO.getEncounterId() != null) {

            Encounter encounter = encounterRepository
                    .findById(observationDTO.getEncounterId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Encounter not found with id: "
                                            + observationDTO.getEncounterId()
                            )
                    );

            // Make sure the encounter belongs to this patient
            if (!encounter.getPatient().getId().equals(patientId)) {
                throw new IllegalArgumentException(
                        "Encounter does not belong to this patient"
                );
            }

            observation.setEncounter(encounter);
        }

        Observation savedObservation =
                observationRepository.save(observation);

        return mapToObservationDTO(savedObservation);
    }

    private ObservationDTO mapToObservationDTO(
            Observation observation) {

        ObservationDTO dto = new ObservationDTO();

        dto.setId(observation.getId());
        dto.setPatientId(observation.getPatient().getId());

        dto.setEncounterId(
                observation.getEncounter() != null
                        ? observation.getEncounter().getId()
                        : null
        );

        dto.setCode(observation.getCode());
        dto.setValue(observation.getValue());
        dto.setEffectiveDateTime(
                observation.getEffectiveDateTime()
        );

        return dto;
    }
}
