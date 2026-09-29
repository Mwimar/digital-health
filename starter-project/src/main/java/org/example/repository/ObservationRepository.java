package org.example.repository;

import org.example.entity.Observation;
import org.example.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findObservationByPatientId(Long patientId);

    List<Observation> findByPatientIdOrderByEffectiveDateTimeDesc(
            Long patientId
    );

    List<Observation> findByEncounterIdOrderByEffectiveDateTimeDesc(
            Long encounterId
    );
}
