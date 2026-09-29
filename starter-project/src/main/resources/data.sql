-- Patients
INSERT INTO patients
(identifier, given_name, family_name, birth_date, gender)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000',
     'Mike',
     'Kevin',
     '1992-06-15',
     'MALE');

INSERT INTO patients
(identifier, given_name, family_name, birth_date, gender)
VALUES
    ('6ba7b810-9dad-11d1-80b4-00c04fd430c8',
     'Jane',
     'Doe',
     '1988-11-23',
     'FEMALE');


-- Encounters
INSERT INTO encounters
(patient_id, start_time, end_time, encounter_class)
VALUES
    (1,
     '2026-09-29 09:30:00',
     '2026-09-29 10:15:00',
     'OUTPATIENT');

INSERT INTO encounters
(patient_id, start_time, end_time, encounter_class)
VALUES
    (2,
     '2026-09-29 14:00:00',
     '2026-09-29 14:45:00',
     'VIRTUAL');


-- Observations
INSERT INTO observations
(patient_id, encounter_id, code, observation_value, effective_date_time)
VALUES
    (1,
     1,
     'blood-pressure',
     '120/80 mmHg',
     '2026-09-29 09:45:00');

INSERT INTO observations
(patient_id, encounter_id, code, observation_value, effective_date_time)
VALUES
    (1,
     1,
     'heart-rate',
     '72 bpm',
     '2026-09-29 09:50:00');

INSERT INTO observations
(patient_id, encounter_id, code, observation_value, effective_date_time)
VALUES
    (2,
     2,
     'body-temperature',
     '36.8 C',
     '2026-09-29 14:15:00');