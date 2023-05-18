package org.example.meditime.service.impl;

import org.example.meditime.constant.ProgressConstants;
import org.example.meditime.entity.MedicineHistory;
import org.example.meditime.entity.Prescription;
import org.example.meditime.repository.MedicineHistoryRepository;
import org.example.meditime.service.MedicineHistoryService;
import org.example.meditime.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MedicineHistoryServiceImpl implements MedicineHistoryService {

        @Autowired
        MedicineHistoryRepository medicineHistoryRepository;

        @Autowired
        PrescriptionService prescriptionService;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        public void moveToTray(Integer medicineId) throws Exception {
            try {
                MedicineHistory medicineHistory = medicineHistoryRepository.findByDoseDateAndMedicineId(sdf.parse(sdf.format(new Date())), medicineId);
                medicineHistory.setProgress(ProgressConstants.IN_TRAY);
                Calendar rightNow = Calendar.getInstance();
                medicineHistory.setInTrayHour(rightNow.get(Calendar.HOUR_OF_DAY));
                medicineHistoryRepository.save(medicineHistory);
            } catch (Exception e) {
                throw new Exception("Could not move medicineId: " + medicineId + " to tray", e);
            }
        }

        public void loadMedicineHistory() throws Exception {
            try {
               List<MedicineHistory> medicineHistoryList = medicineHistoryRepository.findByDoseDate(sdf.parse(sdf.format(new Date())));
               if(CollectionUtils.isEmpty(medicineHistoryList)) {
                   List<Prescription> prescriptionList = prescriptionService.getAllMedicinesFromPrescription();
                   List<MedicineHistory> medicineHistoryListToSave = new ArrayList<>();
                   for (Prescription prescription : prescriptionList) {
                       MedicineHistory medicineHistory = new MedicineHistory();
                       medicineHistory.setMedicineId(prescription.getMedicineId());
                       medicineHistory.setDoseDate(new Date());
                       medicineHistory.setProgress(ProgressConstants.DOSE_CREATED);
                       medicineHistoryListToSave.add(medicineHistory);
                   }
                   medicineHistoryRepository.saveAll(medicineHistoryListToSave);
               }
           } catch (Exception e) {
                throw new Exception("Could not load medicine History due to exception", e);
            }
        }

        public List<MedicineHistory> getDateMedicineHistoryBasedOnProgress(Date date, String progress) throws Exception {
            try {
                date = sdf.parse(sdf.format(date));
                List<MedicineHistory> medicineHistoryList = medicineHistoryRepository.findByDoseDateAndProgress(date, progress);
                if(CollectionUtils.isEmpty(medicineHistoryList)) {
                    loadMedicineHistory();
                    medicineHistoryList = medicineHistoryRepository.findByDoseDateAndProgress(date, progress);
                }
                return medicineHistoryList;
            } catch (Exception e) {
                throw new Exception("Could not get medicines due to exception", e);
            }
        }


}
