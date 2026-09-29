package org.example.service.impl;

import org.example.dto.PatientDTO;
import org.example.service.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PatientServiceImpl implements PatientService {
    @Override
    public List<PatientDTO> findAllPatients() {
        return List.of();
    }

    @Override
    public PatientDTO savePatient(PatientDTO patientDTO) {
        return null;
    }

    @Override
    public PatientDTO updatePatientById(Long id) {
        return null;
    }

    @Override
    public PatientDTO deletePatientById(Long id) {
        return null;
    }

    @Override
    public List<PatientDTO> search(String familyName, String givenName, UUID identifier, LocalDate birthDate) {
        return List.of();
    }
}
