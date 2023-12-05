package com.example.demo.controller;

import java.util.List;

import com.example.demo.exception.PatientException;
import com.example.demo.exception.PatientNotExists;
import com.example.demo.modell.Doctor;
import com.example.demo.modell.Patient;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/patient")
    public String getPatient(@RequestParam(name="id", required=true) int id, Model model) throws PatientNotExists {
        Patient patient = patientService.getPatient(id);

        model.addAttribute("patient", patient);

        return "patients";
    }

    @GetMapping("/patients")
    public String getPatients(Model model, @RequestParam(name = "locality", required = false) String locality, @RequestParam(name="error", required=false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        List<Patient> list;
        if(locality == null || locality.equals("All")) {
            list = patientService.getPatients();
        } else {
            list =patientService.getPatientsByLocality(locality);
        }
        model.addAttribute("patients", list);
        return "patients";
    }

    @GetMapping("/addPatient")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    @GetMapping("/delPatientList")
    public String showDelPatientForm(Model model) {
        List<Patient> patients = patientService.getPatients();
        model.addAttribute("patients", patients);
        model.addAttribute("action", "delPatient");
        model.addAttribute("mode", "delete");
        return "selectPatient";
    }

    @GetMapping("/modPatientList")
    public String showModPatientForm(Model model) {
        List<Patient> patients = patientService.getPatients();
        model.addAttribute("patients", patients);
        model.addAttribute("action", "delPatient");
        model.addAttribute("mode", "modify");
        return "selectPatient";
    }

    @PostMapping("/delPatient")
    public String deletePatient(@RequestParam(name = "id", required = true) int id, Model model) throws PatientNotExists {
        patientService.delete(id);
        return "redirect:/patients";
    }

    @PostMapping("/modPatientForm")
    public String showModifyPatientForm(@RequestParam(name = "id", required = true) int id, Model model) throws PatientNotExists {
        Patient patient = patientService.getPatient(id);
        List<Doctor> doctors = doctorService.getDoctors();
        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctors);
        return "modPatientForm";
    }

    @PostMapping("/modPatient")
    public String modPatient(@Valid Patient patient, BindingResult bindingResult) throws PatientException {
    	
    	System.out.println(patient);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "redirect:/patients";
        }
        patientService.update(patient);
        return "redirect:/patients";
    }    
    

    @PostMapping("/addPatient")
    public String addNewPatient(@Valid Patient patient, BindingResult bindingResult) throws PatientException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "redirect:/patients";
        }
        patientService.save(patient);
        return "redirect:/patients";
    }

    @ExceptionHandler(PatientException.class)
    public String handleIOException(PatientException ex) {
        return "redirect:/patients?error=" + ex.getMessage();
    }

}

