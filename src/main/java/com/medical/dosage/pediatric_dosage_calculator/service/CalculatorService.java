package com.medical.dosage.pediatric_dosage_calculator.service;

import com.medical.dosage.pediatric_dosage_calculator.dto.DoseResponse;
import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalculatorService {

    private final MedicineRepository medicineRepository;

    public CalculatorService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public DoseResponse calculateDose(String medicineName, double weightKg, Double userConcentrationMg, Double userConcentrationMl) throws Exception {
        if (weightKg <= 0) throw new Exception("El peso debe ser mayor que 0");

        Optional<Medicine> medicineOpt = medicineRepository.findByName(medicineName);
        if (medicineOpt.isEmpty()) throw new Exception("Medicine not found");

        Medicine m = medicineOpt.get();

        // Concentración final a usar
        double concentrationMg = (userConcentrationMg != null) ? userConcentrationMg : m.getConcentrationMg();
        double concentrationMl = (userConcentrationMl != null) ? userConcentrationMl : m.getConcentrationMl();

        // Cálculo mg/día = mg/kg/día * peso
        double mgPerDay = m.getMgKgDay() * weightKg;

        // mg por dosis
        double mgPerDose = mgPerDay / m.getDosesPerDay();

        // ml por dosis según concentración
        double mgPerMl = concentrationMg / concentrationMl;
        double mlPerDose = mgPerDose / mgPerMl;

        // Alertas
        String alert = "";
        if (mlPerDose < m.getMinSafeMl()) alert += "Dosis por debajo del rango seguro. ";
        if (mlPerDose > m.getMaxSafeMl()) alert += "Dosis por encima del rango seguro. ";
        if (alert.isEmpty()) alert = "Dentro del rango seguro";

        String safeRange = m.getMinSafeMl() + " - " + m.getMaxSafeMl() + " ml";

        return new DoseResponse(
                medicineName,
                weightKg,
                round(mgPerDay, 2),
                m.getDosesPerDay(),
                round(mgPerDose, 2),
                round(mlPerDose, 2),
                alert,
                safeRange
        );
    }

    private static double round(double v, int places) {
        double factor = Math.pow(10, places);
        return Math.round(v * factor) / factor;
    }
}
