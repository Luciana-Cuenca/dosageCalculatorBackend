package com.medical.dosage.pediatric_dosage_calculator.service;

import com.medical.dosage.pediatric_dosage_calculator.dto.DoseHistoryResponse;
import com.medical.dosage.pediatric_dosage_calculator.dto.DoseResponse;
import com.medical.dosage.pediatric_dosage_calculator.model.DoseCalculation;
import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;
import com.medical.dosage.pediatric_dosage_calculator.model.User;
import com.medical.dosage.pediatric_dosage_calculator.repository.DoseCalculationRepository;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;
import com.medical.dosage.pediatric_dosage_calculator.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CalculatorService {

    private static final Logger log = LoggerFactory.getLogger(CalculatorService.class);

    private final MedicineRepository medicineRepository;
    private final DoseCalculationRepository doseCalculationRepository;
    private final UserRepository userRepository;

    public CalculatorService(MedicineRepository medicineRepository,
                             DoseCalculationRepository doseCalculationRepository,
                             UserRepository userRepository) {
        this.medicineRepository = medicineRepository;
        this.doseCalculationRepository = doseCalculationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
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

        DoseResponse response = new DoseResponse(
                medicineName,
                weightKg,
                round(mgPerDay, 2),
                m.getDosesPerDay(),
                round(mgPerDose, 2),
                round(mlPerDose, 2),
                alert,
                safeRange
        );

        saveCalculation(response);

        return response;
    }

    public List<DoseHistoryResponse> getCurrentUserHistory() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // Sin usuario autenticado devolvemos lista vacía para evitar 403 en historial vacío
            return List.of();
        }

        User user = userRepository.findByUsername(authentication.getName())
                .orElse(null);

        if (user == null) {
            return List.of();
        }
        return doseCalculationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(calc -> new DoseHistoryResponse(
                        calc.getId(),
                        calc.getMedicineName(),
                        calc.getWeightKg(),
                        calc.getMgPerDay(),
                        calc.getDosesPerDay(),
                        calc.getMgPerDose(),
                        calc.getMlPerDose(),
                        calc.getAlert(),
                        calc.getSafeRange(),
                        calc.getCreatedAt()
                ))
                .toList();
    }

    private void saveCalculation(DoseResponse response) throws Exception {
        User user = getAuthenticatedUser();

        DoseCalculation calculation = new DoseCalculation();
        calculation.setUser(user);
        calculation.setMedicineName(response.getMedicine());
        calculation.setWeightKg(response.getWeightKg());
        calculation.setMgPerDay(response.getMgPerDay());
        calculation.setDosesPerDay(response.getDosesPerDay());
        calculation.setMgPerDose(response.getMgPerDose());
        calculation.setMlPerDose(response.getMlPerDose());
        calculation.setAlert(response.getAlert());
        calculation.setSafeRange(response.getSafeRange());

        doseCalculationRepository.save(calculation);
        log.info("Dose calculation saved for user {} and medicine {}", user.getUsername(), response.getMedicine());
    }

    private User getAuthenticatedUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new Exception("Usuario no autenticado");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
    }

    private static double round(double v, int places) {
        double factor = Math.pow(10, places);
        return Math.round(v * factor) / factor;
    }
}
