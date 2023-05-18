package org.example.meditime.service;

import org.example.meditime.entity.MedicineHistory;

import java.util.Date;
import java.util.List;

public interface MedicineHistoryService {

    void loadMedicineHistory() throws Exception;

    List<MedicineHistory> getDateMedicineHistoryBasedOnProgress(Date date, String progress) throws Exception;

    void moveToTray(Integer medicineId) throws Exception;

}
