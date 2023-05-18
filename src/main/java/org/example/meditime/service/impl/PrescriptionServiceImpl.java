package org.example.meditime.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.meditime.constant.ProgressConstants;
import org.example.meditime.constant.TimeConstant;
import org.example.meditime.entity.MedicineHistory;
import org.example.meditime.entity.Prescription;
import org.example.meditime.repository.PrescriptionRepository;
import org.example.meditime.service.MedicineHistoryService;
import org.example.meditime.service.PrescriptionService;
import org.example.meditime.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    MedicineHistoryService medicineHistoryService;


    public List<Prescription> getAllMedicinesFromPrescription() {
        try {
            log.info("[getAllMedicinesFromPrescription] entered");
            List<Prescription> prescriptions = (List<Prescription>) prescriptionRepository.findAll();
            return prescriptions;
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return null;
        }
    }

    public List<Prescription> getCurrentTimeMedicinesBasedOnProgress(String progress) throws Exception {
        progress = mapProgressConstants(progress);
        List<MedicineHistory> medicineHistoryList = medicineHistoryService.getDateMedicineHistoryBasedOnProgress(new Date(), progress);
        if(CollectionUtils.isEmpty(medicineHistoryList))
            throw new Exception("No medicine found for current time and progress:" + progress);
        List<Integer> medicineIds = medicineHistoryList.stream().map(MedicineHistory::getMedicineId).collect(Collectors.toList());
        List<Prescription> prescriptionList = prescriptionRepository.findByMedicineIdIn(medicineIds);
        Integer currentHour = 21;
        List<Prescription> currentList = new ArrayList<>();
        for(Prescription prescription: prescriptionList) {
            if(StringUtils.isEmpty(prescription.getMedicationHour()) && ( (prescription.getMedicationDose() == 2 && checkTimeRangeForMedsTwice(currentHour)) || (prescription.getMedicationDose() == 3 && checkTimeRangeForMedsThrice(currentHour)) ))
                currentList.add(prescription);
            if(StringUtils.isNotEmpty(prescription.getMedicationHour()) && checkCurrentHourInMedicationHours(currentHour, prescription.getMedicationHour()))
                currentList.add(prescription);
        }
        return currentList;
    }

    private String mapProgressConstants(String progress) {
        if(progress.equalsIgnoreCase(ProgressConstants.CREATED))
            return ProgressConstants.DOSE_CREATED;
        else if(progress.equalsIgnoreCase(ProgressConstants.MOVE_TO_TRAY))
            return ProgressConstants.IN_TRAY;
        else
            return ProgressConstants.DONE;
    }


    private boolean checkTimeRangeForMedsTwice(Integer currentHour) {
        if((currentHour >= TimeConstant.MORNING_MEDS_MIN_HOUR && currentHour <= TimeConstant.MORNING_MEDS_MAX_HOUR) || (currentHour >= TimeConstant.EVENING_MEDS_MIN_HOUR && currentHour <= TimeConstant.EVENING_MEDS_MAX_HOUR))
            return true;
        return false;
    }

    private boolean checkTimeRangeForMedsThrice(Integer currentHour) {
        if((currentHour >= TimeConstant.MORNING_MEDS_MIN_HOUR && currentHour <= TimeConstant.MORNING_MEDS_MAX_HOUR) || (currentHour >= TimeConstant.AFTERNOON_MEDS_MIN_HOUR && currentHour <= TimeConstant.AFTERNOON_MEDS_MAX_HOUR) || (currentHour >= TimeConstant.EVENING_MEDS_MIN_HOUR && currentHour <= TimeConstant.EVENING_MEDS_MAX_HOUR))
            return true;
        return false;
    }

    private boolean checkCurrentHourInMedicationHours(Integer currentHour, String hours) {
        String hoursArray[] = hours.split(",");
        if(hoursArray.length == 1 ) {
            if (currentHour >= Integer.parseInt(hoursArray[0]) && currentHour <= maxHourLimitForMeds(Integer.parseInt(hoursArray[0])))
                return true;
            return false;
        }
        for(int i = 0; i < hoursArray.length; i++) {
            if(currentHour >= Integer.parseInt(hoursArray[i]) && currentHour <= maxHourLimitForMeds(Integer.parseInt(hoursArray[i])))
                return true;
        }
        return false;
    }

    private Integer maxHourLimitForMeds(int hour) {
        if(hour + 3 >= 23)
            return 23;
        else
            return hour + 2;
    }

}

