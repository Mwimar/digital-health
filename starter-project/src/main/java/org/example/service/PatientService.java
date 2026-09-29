package org.example.service;

import org.example.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface PatientService {
    List<PatientDTO> findAllPatients();

    PatientDTO savePatient(PatientDTO patientDTO);

    PatientDTO updatePatientById(Long id);

    PatientDTO deletePatientById(Long id);

    List<PatientDTO> search(String familyName, String givenName, UUID identifier, LocalDate birthDate);
}
