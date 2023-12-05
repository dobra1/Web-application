package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.DoctorException;
import com.example.demo.exception.DoctorFieldInvalid;
import com.example.demo.exception.DoctorNotExists;
import com.example.demo.exception.PatientException;
import com.example.demo.exception.PatientFieldInvalid;
import com.example.demo.exception.PatientNotExists;
import com.example.demo.modell.Doctor;
import com.example.demo.modell.Patient;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping("/api")
public class RestMainController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctors")
    public List<Doctor> doctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/doctors/{id}")
    public Doctor doctors(@PathVariable int id) throws DoctorNotExists {
        return doctorService.getDoctor(id);
    }

    @PostMapping("/api/doctors")
    public Doctor doctors(@RequestBody Doctor doctor) throws DoctorFieldInvalid, DoctorException {
        return doctorService.save(doctor);
    }

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> patients() {
        return patientService.getPatients();
    }

    @GetMapping("/patients/{id}")
    public Patient patients(@PathVariable int id) throws PatientNotExists {
        return patientService.getPatient(id);
    }

    @PostMapping("/patients")
    public Patient patients(@RequestBody Patient patient) throws PatientFieldInvalid, PatientException {
        return patientService.save(patient);
    }
    
    @GetMapping("/patients/locality/{locality}")
    public List<Patient> findPatientsByLocality(@PathVariable String locality) {
    	return patientService.getPatientByLocality(locality);
    }
}
