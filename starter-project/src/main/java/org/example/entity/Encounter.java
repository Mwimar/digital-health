package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.EncounterClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@Table(name = "encounters")
@AllArgsConstructor
@NoArgsConstructor
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private Patient patient;

    @Column(name="start_time", nullable = false)
    private LocalDateTime start;

    @Column(name="end_time", nullable = false)
    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EncounterClass encounterClass;

    @OneToMany(mappedBy = "encounter")
    @Builder.Default
    private List<Observation> observations = new ArrayList<>();

}
