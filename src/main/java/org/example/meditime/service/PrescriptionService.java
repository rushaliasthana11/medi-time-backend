package org.example.meditime.service;

import org.example.meditime.entity.Prescription;

import java.util.List;

public interface PrescriptionService {

    List<Prescription> getAllMedicinesFromPrescription();

    public List<Prescription> getCurrentTimeMedicinesBasedOnProgress(String progress) throws Exception;

}
