package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.modell.Patient;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    Optional<Patient> findByNameAndLocalityAndBirthDate(String name, String locality, LocalDate birthDate);
    List<Patient> findByLocality(String locality);

    List<Patient> findByDoctorId(int doctorId);
}
