package com.medical.dosage.pediatric_dosage_calculator.strategy;

import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;

public class WeightBasedDoseCalculator implements DoseCalculatorStrategy {

    @Override
    public double calculateMgPerDay(Medicine medicine, double weightKg) {
        return medicine.getMgKgDay() * weightKg;
    }
}
