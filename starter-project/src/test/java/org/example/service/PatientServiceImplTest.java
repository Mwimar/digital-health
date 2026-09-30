package org.example.service;



import org.example.dto.PatientDTO;
import org.example.entity.Patient;
import org.example.entity.enums.Gender;
import org.example.repository.PatientRepository;
import org.example.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientDTO patientDTO;
    private Patient savedPatient;

    @BeforeEach
    void setUp() {

        patientDTO = new PatientDTO();

        patientDTO.setGivenName("John");
        patientDTO.setFamilyName("Mwangi");
        patientDTO.setBirthDate(
                LocalDate.of(1992, 6, 15)
        );
        patientDTO.setGender(Gender.MALE);

        savedPatient = new Patient();

        savedPatient.setId(1L);
        savedPatient.setIdentifier(UUID.randomUUID());
        savedPatient.setGivenName("John");
        savedPatient.setFamilyName("Mwangi");
        savedPatient.setBirthDate(
                LocalDate.of(1992, 6, 15)
        );
        savedPatient.setGender(Gender.MALE);
    }

    @Test
    void shouldCreatePatient() {

        when(patientRepository.save(any(Patient.class)))
                .thenReturn(savedPatient);

        PatientDTO result =
                patientService.savePatient(patientDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getGivenName()).isEqualTo("John");
        assertThat(result.getFamilyName()).isEqualTo("Mwangi");
        assertThat(result.getBirthDate())
                .isEqualTo(LocalDate.of(1992, 6, 15));
        assertThat(result.getGender())
                .isEqualTo(Gender.MALE);
        assertThat(result.getIdentifier())
                .isNotNull();
    }
}