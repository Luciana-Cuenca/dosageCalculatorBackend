package com.medical.dosage.pediatric_dosage_calculator.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;

import java.util.*;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin("*")
public class CalculatorController {

    private final MedicineRepository medicineRepository;

    public CalculatorController(MedicineRepository medicineRepository){
        this.medicineRepository = medicineRepository;
    }

    @PostMapping("/dose")
    public ResponseEntity<?> calculateDose(@RequestBody Map<String, Object> payload) {

        try {
            String medicineName = payload.get("medicineName").toString();
            Double weightKg = Double.valueOf(payload.get("weightKg").toString());

            var medicineOpt = medicineRepository.findByName(medicineName);
            if (medicineOpt.isEmpty()) return ResponseEntity.badRequest().body("Medicine not found");

            var medicine = medicineOpt.get();

            // max mg por dia
            Double maxMgDay = medicine.getDosageMax() * weightKg;

            // extraer concentración -> "250mg/5ml"
            String c = medicine.getConcentration().toLowerCase().replace(" ", "");
            String[] parts = c.split("mg/");
            double mg = Double.valueOf(parts[0]);
            double ml = Double.valueOf(parts[1].replace("ml", ""));
            double mgPerMl = mg / ml;

            double maxPerDose = medicine.getMaxDosePerDose(); // max mg por dosis

            List<Map<String, Object>> options = new ArrayList<>();

            int[] intervals = {12, 8, 6};

            for (int hours : intervals) {
                int dosesPerDay = 24 / hours;
                double mgPerDose = maxMgDay / dosesPerDay;

                if (mgPerDose <= maxPerDose) {
                    double mlPerDose = mgPerDose / mgPerMl;

                    options.add(Map.of(
                            "everyHours", hours,
                            "dosesPerDay", dosesPerDay,
                            "mgPerDose", mgPerDose,
                            "mlPerDose", mlPerDose
                    ));
                }
            }

            return ResponseEntity.ok(Map.of(
                    "medicine", medicineName,
                    "weightKg", weightKg,
                    "maxMgDay", maxMgDay,
                    "safeOptions", options
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
