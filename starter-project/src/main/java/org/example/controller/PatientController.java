
package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseController;
import org.example.common.dto.GenericApiResponse;
import org.example.dto.EncounterDTO;
import org.example.dto.ObservationDTO;
import org.example.dto.PatientDTO;
import org.example.service.EncounterService;
import org.example.service.ObservationService;
import org.example.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "apiKey")
public class PatientController extends BaseController {

    private final PatientService patientService;
    private final EncounterService encounterService;
    private final ObservationService observationService;


    // ============================================================
    // PATIENTS
    // ============================================================

    /**
     * Search / list patients.

     * GET /api/patients

     * Examples:
     * GET /api/patients
     * GET /api/patients?family=Smith
     * GET /api/patients?given=John
     * GET /api/patients?identifier=PAT-001
     * GET /api/patients?birthDate=1990-05-20

     * Pagination:
     * GET /api/patients?page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<GenericApiResponse<Page<PatientDTO>>> getPatients(
            @RequestParam(required = false) String family,

            @RequestParam(required = false) String given,

            @RequestParam(required = false) String identifier,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate birthDate,

            @PageableDefault( size = 10, sort = "familyName", direction = Sort.Direction.ASC ) Pageable pageable) {

        Page<PatientDTO> patients = patientService.searchPatients( family, given, identifier, birthDate, pageable );

        return successResponse(
                patients,
                "Patients retrieved successfully"
        );
    }


    /**
     * Get patient by ID.

     * GET /api/patients/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<PatientDTO>> getPatient(
            @PathVariable Long id) {

        PatientDTO patient = patientService.findPatientById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found with id: " + id
                        )
                );

        return successResponse(
                patient,
                "Patient retrieved successfully"
        );
    }


    /**
     * Create patient.
     * POST /api/patients
     */
    @PostMapping
    public ResponseEntity<GenericApiResponse<PatientDTO>> addPatient(
            @Valid @RequestBody PatientDTO patientDTO) {

        PatientDTO savedPatient =
                patientService.savePatient(patientDTO);

        return createdResponse(
                savedPatient,
                "Patient created successfully"
        );
    }


    /**
     * Update patient.

     * PUT /api/patients/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse<PatientDTO>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDTO patientDTO) {

        PatientDTO updatedPatient =
                patientService.updatePatientById(
                        id,
                        patientDTO
                );

        return successResponse(
                updatedPatient,
                "Patient updated successfully"
        );
    }


    /**
     * Delete patient.

     * DELETE /api/patients/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse<PatientDTO>> deletePatient(
            @PathVariable Long id) {

        PatientDTO deletedPatient =
                patientService.deletePatientById(id);

        return successResponse(
                deletedPatient,
                "Patient deleted successfully"
        );
    }


    // ============================================================
    // ENCOUNTERS
    // ============================================================

    /**
     * Get all encounters belonging to a patient.
     *
     * GET /api/patients/{patientId}/encounters
     */
    @GetMapping("/{patientId}/encounters")
    public ResponseEntity<GenericApiResponse<List<EncounterDTO>>>
    getPatientEncounters(
            @PathVariable Long patientId) {

        List<EncounterDTO> encounters =
                encounterService.findByPatientId(patientId);

        return successResponse(
                encounters,
                "Patient encounters retrieved successfully"
        );
    }


    /**
     * Create an encounter for a patient.

     * POST /api/patients/{patientId}/encounters
     */
    @PostMapping("/{patientId}/encounters")
    public ResponseEntity<GenericApiResponse<EncounterDTO>>
    addPatientEncounter(
            @PathVariable Long patientId,
            @Valid @RequestBody EncounterDTO encounterDTO) {

        EncounterDTO encounter =
                encounterService.createEncounter(
                        patientId,
                        encounterDTO
                );

        return createdResponse(
                encounter,
                "Encounter created successfully"
        );
    }


    /**
     * Update an encounter.

     * PUT /api/patients/{patientId}/encounters/{encounterId}
     */
    @PutMapping("/{patientId}/encounters/{encounterId}")
    public ResponseEntity<GenericApiResponse<EncounterDTO>>
    updateEncounter(
            @PathVariable Long patientId,
            @PathVariable Long encounterId,
            @Valid @RequestBody EncounterDTO encounterDTO) {

        EncounterDTO updatedEncounter =
                encounterService.updateEncounterById(
                        encounterId,
                        encounterDTO
                );

        return successResponse(
                updatedEncounter,
                "Encounter updated successfully"
        );
    }


    /**
     * Delete an encounter.

     * DELETE /api/patients/{patientId}/encounters/{encounterId}
     */
    @DeleteMapping("/{patientId}/encounters/{encounterId}")
    public ResponseEntity<GenericApiResponse<EncounterDTO>>
    deleteEncounter(
            @PathVariable Long patientId,
            @PathVariable Long encounterId) {

        EncounterDTO deletedEncounter =
                encounterService.deleteEncounterById(
                        encounterId
                );

        return successResponse(
                deletedEncounter,
                "Encounter deleted successfully"
        );
    }


    // ============================================================
    // OBSERVATIONS
    // ============================================================

    /**
     * Get all observations belonging to a patient.
     *
     * GET /api/patients/{patientId}/observations
     */
    @GetMapping("/{patientId}/observations")
    public ResponseEntity<GenericApiResponse<List<ObservationDTO>>>
    getPatientObservations(
            @PathVariable Long patientId) {

        List<ObservationDTO> observations =
                observationService.findByPatientId(patientId);

        return successResponse(
                observations,
                "Patient observations retrieved successfully"
        );
    }


    /**
     * Create an observation for a patient.
     *
     * POST /api/patients/{patientId}/observations
     */
    @PostMapping("/{patientId}/observations")
    public ResponseEntity<GenericApiResponse<ObservationDTO>>
    addPatientObservation(
            @PathVariable Long patientId,
            @Valid @RequestBody ObservationDTO observationDTO) {

        ObservationDTO savedObservation =
                observationService.addObservation(
                        patientId,
                        observationDTO
                );

        return createdResponse(
                savedObservation,
                "Observation created successfully"
        );
    }
}

