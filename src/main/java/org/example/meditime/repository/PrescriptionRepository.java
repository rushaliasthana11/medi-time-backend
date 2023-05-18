package org.example.meditime.repository;

import org.example.meditime.entity.Prescription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PrescriptionRepository extends CrudRepository<Prescription, Integer> {

    List<Prescription> findByMedicineIdIn(List<Integer> medicineIds);
}
