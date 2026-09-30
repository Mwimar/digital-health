package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.common.exception.ResourceNotFoundException;
import org.example.dto.EncounterDTO;
import org.example.entity.Encounter;
import org.example.entity.Patient;
import org.example.repository.EncounterRepository;
import org.example.repository.PatientRepository;
import org.example.service.EncounterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<EncounterDTO> findByPatientId(Long patientId) {

        // First verify that the patient exists
        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + patientId
                        )
                );

        // Patient exists, so an empty list is a valid response
        return encounterRepository.findEncounterByPatientId(patientId)
                .stream()
                .map(this::mapToEncounterDTO)
                .toList();
    }

    @Override
    public EncounterDTO createEncounter(
            Long patientId,
            EncounterDTO encounterDTO) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + patientId
                        )
                );

        Encounter encounter = new Encounter();

        encounter.setPatient(patient);
        encounter.setStart(encounterDTO.getStart());
        encounter.setEnd(encounterDTO.getEnd());
        encounter.setEncounterClass(encounterDTO.getEncounterClass());

        Encounter savedEncounter = encounterRepository.save(encounter);

        return mapToEncounterDTO(savedEncounter);
    }

    @Override
    public EncounterDTO updateEncounterById(
            Long id,
            EncounterDTO encounterDTO) {

        Encounter encounter = encounterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Encounter not found with id: " + id
                        )
                );

        encounter.setStart(encounterDTO.getStart());
        encounter.setEnd(encounterDTO.getEnd());
        encounter.setEncounterClass(encounterDTO.getEncounterClass());

        Encounter updatedEncounter = encounterRepository.save(encounter);

        return mapToEncounterDTO(updatedEncounter);
    }

    @Override
    public EncounterDTO deleteEncounterById(Long id) {

        Encounter encounter = encounterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Encounter not found with id: " + id
                        )
                );

        if (!encounter.getObservations().isEmpty()) {
            throw new IllegalStateException(
                    "Encounter cannot be deleted because it has associated observations"
            );
        }

        EncounterDTO deletedEncounter = mapToEncounterDTO(encounter);

        encounterRepository.delete(encounter);

        return deletedEncounter;
    }

    private EncounterDTO mapToEncounterDTO(Encounter encounter) {
        EncounterDTO dto = new EncounterDTO();

        dto.setId(encounter.getId());
        dto.setPatientId(encounter.getPatient().getId());
        dto.setStart(encounter.getStart());
        dto.setEnd(encounter.getEnd());
        dto.setEncounterClass(encounter.getEncounterClass());

        return dto;
    }
}
