package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "patient_id", nullable = false )
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "encounter_id" )
    private Encounter encounter;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(name = "observation_value", nullable = false, length = 255)
    private String value;

    @Column(nullable = false)
    private LocalDateTime effectiveDateTime;
}
