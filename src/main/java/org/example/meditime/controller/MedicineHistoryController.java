package org.example.meditime.controller;


import org.example.meditime.service.MedicineHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/medicine-history")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicineHistoryController {

    @Autowired
    MedicineHistoryService medicineHistoryService;

    @PostMapping("/load")
    public void loadMedicineHistory() throws Exception {
        medicineHistoryService.loadMedicineHistory();
    }

    @PostMapping("/move-to-tray/{medicineId}")
    public void moveToTray(@PathVariable("medicineId") Integer medicineId) throws Exception {
        medicineHistoryService.moveToTray(medicineId);
    }


}
