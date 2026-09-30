package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.dto.PatientDTO;
import org.example.entity.Patient;
import org.example.repository.PatientRepository;
import org.example.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;




    @Override
    public Optional<PatientDTO> findPatientById(Long id) {
        return patientRepository.findById(id)
                .map(this::mapToPatientDTO);
    }

    @Override
    public Page<PatientDTO> searchPatients(
            String family,
            String given,
            String identifier,
            LocalDate birthDate,
            Pageable pageable) {

        UUID identifierUuid = null;

        if (identifier != null && !identifier.isBlank()) {
            identifierUuid = UUID.fromString(identifier);
        }

        return patientRepository.searchPatient(
                family,
                given,
                identifierUuid,
                birthDate,
                pageable
        ).map(this::mapToPatientDTO);
    }

    @Override
    public PatientDTO savePatient(PatientDTO patientDTO) {
        Patient patient = new Patient();

        patient.setGivenName(patientDTO.getGivenName());
        patient.setFamilyName(patientDTO.getFamilyName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setGender(patientDTO.getGender());
        patient.setIdentifier(UUID.randomUUID());

        Patient savedPatient = patientRepository.save(patient);

        return mapToPatientDTO(savedPatient);
    }
    private PatientDTO mapToPatientDTO(Patient patient) {

        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setId(patient.getId());
        patientDTO.setIdentifier(patient.getIdentifier());
        patientDTO.setGivenName(patient.getGivenName());
        patientDTO.setFamilyName(patient.getFamilyName());
        patientDTO.setGender(patient.getGender());
        patientDTO.setBirthDate(patient.getBirthDate());

        return patientDTO;
    }


    @Override
    public PatientDTO updatePatientById(Long id, PatientDTO patientDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient does not exist with id: " + id)
                );

        patient.setGivenName(patientDTO.getGivenName());
        patient.setFamilyName(patientDTO.getFamilyName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setGender(patientDTO.getGender());

        Patient updatedPatient = patientRepository.save(patient);

        return mapToPatientDTO(updatedPatient);
    }

    @Override
    public PatientDTO deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient does not exist with id: " + id)
                );

        PatientDTO deletedPatient = mapToPatientDTO(patient);

        patientRepository.deleteById(id);
        return deletedPatient;
    }

    @Override
    public List<PatientDTO> search(String familyName, String givenName, UUID identifier, LocalDate birthDate) {
        return List.of();
    }
}
