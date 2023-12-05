package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.modell.Doctor;
import com.example.demo.modell.Schedule;

    public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
        Optional<Doctor> findByNameAndSealNumberAndSalaryAndBirthDateAndPositionAndSchedule(String name, int sealNumber, int salary, LocalDate birthDate, String position, Schedule schedule);
        List<Doctor> findByPosition(String position);
    }
