package org.example.service;

import org.example.dto.PatientDTO;
import org.example.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientService {

    Optional<PatientDTO> findPatientById(Long id);

    PatientDTO savePatient(PatientDTO patientDTO);

    PatientDTO updatePatientById(Long id, PatientDTO patientDTO);

    PatientDTO deletePatientById(Long id);

    List<PatientDTO> search(String familyName, String givenName, UUID identifier, LocalDate birthDate);
}
