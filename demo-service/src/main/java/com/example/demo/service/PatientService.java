package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.*;
import com.example.demo.modell.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatient(int id) throws PatientNotExists {
        Optional<Patient> patient = patientRepository.findById(id);
        System.out.println(patient);
        if (!patient.isPresent()) {
            System.out.println("Patient not found for ID: " + id);
            throw new PatientNotExists("Patient with ID " + id + " doesn't exists.");
        }

        return patient.get();
    }

    public List<Patient> getPatients() {
        List<Patient> list = new ArrayList<>();
        for (Patient patient : patientRepository.findAll()) {
            list.add(patient);
        }
        return list;
    }

    public Patient save(Patient patient) throws PatientException, PatientFieldInvalid {
        Optional<Patient> oldPatient = patientRepository.findByNameAndLocalityAndBirthDate(patient.getName(), patient.getLocality(), patient.getBirthDate());
        if (oldPatient.isPresent()) {
            throw new PatientAlreadyExist("This patient already present.");
        }

        StringBuilder errors = new StringBuilder();

        if (patient.getName() == null || patient.getName().isBlank() || patient.getName().length() < 2) {
            errors.append("Patient name must be at least 2 characters long.\n");
        }

        if (patient.getBirthDate() == null || patient.getBirthDate().isAfter(LocalDate.now())) {
            errors.append("Birth date must be a date from the past.\n");
        }

        if (!errors.isEmpty()) {
            throw new PatientFieldInvalid(errors.toString());
        }
        return patientRepository.save(patient);
    }
    
    public Patient update(Patient patient) throws PatientException, PatientFieldInvalid {
        Optional<Patient> oldPatient = patientRepository.findById(patient.getId());
        if (!oldPatient.isPresent()) {
            throw new PatientNotExists("This patient doesn't exists.");
        }

        StringBuilder errors = new StringBuilder();

        if (patient.getName() == null || patient.getName().isBlank() || patient.getName().length() < 2) {
            errors.append("Patient name must be at least 2 characters long.\n");
        }

        if (patient.getBirthDate() == null || patient.getBirthDate().isAfter(LocalDate.now())) {
            errors.append("Birth date must be a date from the past.\n");
        }

        if (!errors.isEmpty()) {
            throw new PatientFieldInvalid(errors.toString());
        }
        return patientRepository.save(patient);
    }   

    public void delete(int id) throws PatientNotExists {
        Optional<Patient> patient = patientRepository.findById(id);
        if (!patient.isPresent()) {
            throw new PatientNotExists("Patient with ID " + id + " doesn't exists.");
        }
        patientRepository.deleteById(id);
    }

    public List<Patient> getPatientsByLocality(String locality) {
        List<Patient> patients = patientRepository.findByLocality(locality);
        return patients;
    }

    public List<Patient> getPatientByDoctorId(int doctorId) {
        // Megkeressük az összes pácienset, akiknek az orvos azonosítója megegyezik a paraméterrel
        return patientRepository.findByDoctorId(doctorId);
    }
    
    public List<Patient> getPatientByLocality(String locality) {
    	List<Patient> patients = patientRepository.findByLocality(locality);
    	return patients;
    }
}



