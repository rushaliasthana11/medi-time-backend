package org.example.meditime.repository;

import org.example.meditime.entity.MedicineHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface MedicineHistoryRepository extends CrudRepository<MedicineHistory, Integer> {

    List<MedicineHistory> findByDoseDate(Date currentDate);

    List<MedicineHistory> findByDoseDateAndProgress(Date currentDate, String progress);

    MedicineHistory findByDoseDateAndMedicineId(Date currentDate, Integer medicineId);

}
