package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.EncounterDTO;
import org.example.dto.ObservationDTO;
import org.example.dto.PatientDTO;
import org.example.entity.Patient;
import org.example.service.EncounterService;
import org.example.service.ObservationService;
import org.example.service.PatientService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@Slf4j
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final EncounterService encounterService;
    private final ObservationService observationService;

    //PATIENTS CRUD OPERATIONS

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PatientDTO>> getPatient(@PathVariable Long id) {
        Optional<PatientDTO> patient = patientService.findPatientById(id);
        return ResponseEntity.ok(patient);

    }

    @PostMapping()
    public ResponseEntity<PatientDTO> addPatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO savedPatient = patientService.savePatient(patientDTO);
        return ResponseEntity.ok(savedPatient);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatientById(id, patientDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientDTO> deletePatient(@PathVariable Long id) {
        PatientDTO deletedPatient = patientService.deletePatientById(id);
        return ResponseEntity.ok(deletedPatient);
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> search(
            @RequestParam(required = false) String familyName,
            @RequestParam(required = false) String givenName,
            @RequestParam(required = false) UUID identifier,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate birthDate
    ) {
        return ResponseEntity.ok(
                patientService.search(
                        familyName,
                        givenName,
                        identifier,
                        birthDate
                )
        );
    }

    //ENCOUNTERS

    @GetMapping("/{id}/encounters")
    public ResponseEntity<List<EncounterDTO>> getPatientEncounters(@PathVariable Long id){
        List<EncounterDTO> patientEncounters = encounterService.findByPatientId(id);
        return ResponseEntity.ok(patientEncounters);
    }

    @PostMapping("/{id}/encounters")
    public ResponseEntity<EncounterDTO> addPatientEncounter(
            @PathVariable Long id,
            @RequestBody EncounterDTO encounterDTO) {

        EncounterDTO patientEncounter =
                encounterService.createEncounter(id, encounterDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(patientEncounter);
    }


    @GetMapping("/{id}/observations")
    public ResponseEntity<List<ObservationDTO>> getPatientObservations(
            @PathVariable Long id) {

        List<ObservationDTO> observations =
                observationService.findByPatientId(id);

        return ResponseEntity.ok(observations);
    }

    @PostMapping("/{id}/observations")
    public ResponseEntity<ObservationDTO> addPatientObservation(
            @PathVariable Long id,
            @RequestBody ObservationDTO observationDTO) {

        ObservationDTO savedObservation =
                observationService.addObservation(
                        id,
                        observationDTO
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedObservation);
    }

}
