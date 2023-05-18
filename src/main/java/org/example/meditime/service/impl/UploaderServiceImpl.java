package org.example.meditime.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.example.meditime.entity.Prescription;
import org.example.meditime.repository.PrescriptionRepository;
import org.example.meditime.service.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UploaderServiceImpl implements UploaderService {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    public void uploadCsvFile(MultipartFile file) throws Exception {
        if(!file.getContentType().equals("text/csv"))
            return;
        List<Prescription> list = readCsvFileToObject(file.getInputStream());
        try {
            prescriptionRepository.saveAll(list);
            log.info("Uploaded successfully");
        } catch (Exception e) {
            log.error("Exception:{}",e);
            throw e;
        }
    }

    private List<Prescription> readCsvFileToObject(InputStream inputStream) throws Exception {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<Prescription> list = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord: csvRecords) {
                if(StringUtils.isEmpty(csvRecord.get("medicine_name")) || StringUtils.isEmpty(csvRecord.get("medicine_composition")) || StringUtils.isEmpty(csvRecord.get("stock")) || StringUtils.isEmpty(csvRecord.get("medicine_dose"))) {
                    throw new Exception("medicine_name/medicine_composition/stock/medicine_dose cannot be empty or null!");
                }
                if(Integer.parseInt(csvRecord.get("medicine_dose").trim()) == 1 && StringUtils.isEmpty(csvRecord.get("medication_hour").trim()))
                    throw new Exception("For medicine dose 1 medication hour cannot be null/empty");
                Prescription prescription = new Prescription();
                prescription.setMedicineName(csvRecord.get("medicine_name").trim());
                prescription.setMedicineComposition(csvRecord.get("medicine_composition").trim());
                prescription.setStockCount(StringUtils.isNotEmpty(csvRecord.get("stock").trim()) ? Integer.parseInt(csvRecord.get("stock").trim()) : null);
                prescription.setMedicationDose(Integer.parseInt(csvRecord.get("medicine_dose").trim()));
                prescription.setMedicationHour(StringUtils.isNotEmpty(csvRecord.get("medication_hour").trim()) ? setMedicationHourString(csvRecord.get("medication_hour").trim()) : null);
                prescription.setMedicationDuration(StringUtils.isNotEmpty(csvRecord.get("medication_duration").trim()) ? Integer.parseInt(csvRecord.get("medication_duration").trim()) : null);
                prescription.setFoodIntake(StringUtils.isNotEmpty(csvRecord.get("food_intake").trim()) ? csvRecord.get("food_intake").trim() : null);
                list.add(prescription);
            }
            return list;
            } catch (Exception e) {
            log.error("Exception : {}", e);
            throw e;
        }
    }

    private String setMedicationHourString(String medicationHours) {
        String strhour="";
        List<String> delimitedHour = Arrays.asList(medicationHours.split("_"));
        if(delimitedHour.size() >1) {
            strhour = delimitedHour.get(0);
            for (int i =1; i<delimitedHour.size();i++)
                strhour += "," + delimitedHour.get(i);
        }
        else
            strhour = delimitedHour.get(0);
        return strhour;
    }


}

