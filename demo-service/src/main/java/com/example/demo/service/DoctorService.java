package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.modell.Doctor;
import com.example.demo.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientService patientService;

    public Doctor getDoctor(int id) throws DoctorNotExists {
        Optional<Doctor> doctor = doctorRepository.findById(id);
    	System.out.println(doctor);
        if (!doctor.isPresent()) {
            System.out.println("Doctor not found for ID: " + id);
            throw new DoctorNotExists("Doctor with ID " + id + " doesn't exists.");
        }

        return doctor.get();
    }

    public List<Doctor> getDoctors() {
        List<Doctor> list = new ArrayList<>();
        for (Doctor doctor : doctorRepository.findAll()) {
            list.add(doctor);
        }
        return list;
    }

    public Doctor save(Doctor doctor) throws DoctorException, DoctorFieldInvalid {

        Optional<Doctor> oldDoctor = doctorRepository.findByNameAndSealNumberAndSalaryAndBirthDateAndPositionAndSchedule(doctor.getName(), doctor.getSealNumber(), doctor.getSalary(), doctor.getBirthDate(), doctor.getPosition(), doctor.getSchedule());
        if (oldDoctor.isPresent()) {
            throw new DoctorAlreadyExist("This doctor already present.");
        }

        StringBuilder errors = new StringBuilder();

        if (doctor.getName() == null || doctor.getName().isBlank() || doctor.getName().length() < 2) {
            errors.append("Doctor name must be at least 2 characters long.\n");
        }

        if (doctor.getBirthDate() == null || doctor.getBirthDate().isAfter(LocalDate.now())) {
            errors.append("Birth date must be a date from the past.\n");
        }

        if (!errors.isEmpty()) {
            throw new DoctorFieldInvalid(errors.toString());
        }


        return doctorRepository.save(doctor);
    }

    public void delete(int id) throws DoctorNotExists {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (!doctor.isPresent()) {
            throw new DoctorNotExists("Doctor with ID " + id + " doesn't exists.");
        }
        doctorRepository.deleteById(id);
    }

	public List<Doctor> getDoctorsByPosition(String position) {
		List<Doctor> doctors = doctorRepository.findByPosition(position);
		return doctors;
	}
}