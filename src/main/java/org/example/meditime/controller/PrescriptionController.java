package org.example.meditime.controller;

import org.example.meditime.entity.Prescription;
import org.example.meditime.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/medicines")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping(value = "/all")
    public List<Prescription> getPrescribedMedications() {
        return prescriptionService.getAllMedicinesFromPrescription();
    }

    @GetMapping(value = "/current/{progress}")
    public List<Prescription> getCurrentHourPrescribedMedications(@PathVariable("progress") String progress) throws Exception {
        return prescriptionService.getCurrentTimeMedicinesBasedOnProgress(progress);
    }


}
