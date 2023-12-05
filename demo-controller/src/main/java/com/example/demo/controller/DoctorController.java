package com.example.demo.controller;

import com.example.demo.exception.DoctorException;
import com.example.demo.exception.DoctorNotExists;
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
import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/doctor")
    public String getDoctor(@RequestParam(name="id", required=true) int id, Model model) throws  DoctorNotExists {
        Doctor doctor = doctorService.getDoctor(id);
        List<Patient> patients = patientService.getPatientByDoctorId(id);

        model.addAttribute("doctor", doctor);
        model.addAttribute("patients", patients);

        return "doctors";
    }

    @GetMapping("/doctors")
    public String getDoctors(Model model, @RequestParam(name = "position", required = false) String position, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        List<Doctor> list;
        if (position == null || position.equals("All")) {
            list = doctorService.getDoctors();
        } else {
            list = doctorService.getDoctorsByPosition(position);
        }

        model.addAttribute("doctors", list);
        return "doctors";
    }

    @GetMapping("/addDoctor")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("mode", "new");
        return "addDoctor";
    }

    @GetMapping("/delDoctorList")
    public String showDelDoctorForm(Model model) {
        List<Doctor> doctors = doctorService.getDoctors();
        model.addAttribute("doctors", doctors);
        model.addAttribute("action", "delDoctor");
        model.addAttribute("mode", "delete");
        return "selectDoctor";
    }

    @GetMapping("/modDoctorList")
    public String showModDoctorForm(Model model) {
        List<Doctor> doctors = doctorService.getDoctors();
        model.addAttribute("doctors", doctors);
        model.addAttribute("action", "modDoctor");
        model.addAttribute("mode", "modify");
        return "selectDoctor";
    }

    @PostMapping("/delDoctor")
    public String deleteDoctor(@RequestParam(name = "id", required = true) int id, Model model) throws DoctorNotExists {
        doctorService.delete(id);
        return "redirect:/doctors";
    }

    @PostMapping("/modDoctor")
    public String showModifyDoctorForm(@RequestParam(name = "id", required = true) int id, Model model) throws DoctorNotExists {
        Doctor doctor = doctorService.getDoctor(id);
        model.addAttribute("doctor", doctor);
        model.addAttribute("mode", "modify");
        return "addDoctor";
    }

    @PostMapping("/addDoctor")
    public String addNewDoctor(@Valid Doctor doctor, BindingResult bindingResult) throws DoctorException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "redirect:/doctors";
        }
        doctorService.save(doctor);
        return "redirect:/doctors";
    }

    @ExceptionHandler(DoctorException.class)
    public String handleIOException(DoctorException ex) {
        return "redirect:/doctors?error=" + ex.getMessage();
    }

}