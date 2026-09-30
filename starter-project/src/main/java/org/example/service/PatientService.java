package org.example.service;

import org.example.dto.PatientDTO;
import org.example.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientService {

    Optional<PatientDTO> findPatientById(Long id);

    Page<PatientDTO> searchPatients(
            String family,
            String given,
            String identifier,
            LocalDate birthDate,
            Pageable pageable
    );

    PatientDTO savePatient(PatientDTO patientDTO);

    PatientDTO updatePatientById(Long id, PatientDTO patientDTO);

    PatientDTO deletePatientById(Long id);

    List<PatientDTO> search(String familyName, String givenName, UUID identifier, LocalDate birthDate);
}
