package org.example.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Encounter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObservationDTO {
    private Long id;
    private Long patientId;
    private Long encounterId;
    private String code;
    private String value;
    private LocalDateTime effectiveDateTime;

}
