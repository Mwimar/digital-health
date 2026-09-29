package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Observation;
import org.example.entity.Patient;
import org.example.entity.enums.EncounterClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncounterDTO {
    private Long id;

    private Long patientId;

    private LocalDateTime start;

    private LocalDateTime end;

    private EncounterClass encounterClass;
}
