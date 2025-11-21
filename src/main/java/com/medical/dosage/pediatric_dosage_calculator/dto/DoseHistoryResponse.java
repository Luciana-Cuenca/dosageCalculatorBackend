package com.medical.dosage.pediatric_dosage_calculator.dto;

import java.time.LocalDateTime;

public class DoseHistoryResponse {
    private final Long id;
    private final String medicine;
    private final double weightKg;
    private final double mgPerDay;
    private final int dosesPerDay;
    private final double mgPerDose;
    private final double mlPerDose;
    private final String alert;
    private final String safeRange;
    private final LocalDateTime createdAt;

    public DoseHistoryResponse(Long id, String medicine, double weightKg, double mgPerDay, int dosesPerDay,
                               double mgPerDose, double mlPerDose, String alert, String safeRange,
                               LocalDateTime createdAt) {
        this.id = id;
        this.medicine = medicine;
        this.weightKg = weightKg;
        this.mgPerDay = mgPerDay;
        this.dosesPerDay = dosesPerDay;
        this.mgPerDose = mgPerDose;
        this.mlPerDose = mlPerDose;
        this.alert = alert;
        this.safeRange = safeRange;
        this.createdAt = createdAt;
    }

    // Getters

    public Long getId() {return id;}
    public String getMedicine() {return medicine;}
    public double getWeightKg() {return weightKg;}
    public double getMgPerDay() {return mgPerDay;}
    public int getDosesPerDay() {return dosesPerDay;}
    public double getMgPerDose() {return mgPerDose;}
    public double getMlPerDose() {return mlPerDose;}
    public String getAlert() {return alert;}
    public String getSafeRange() {return safeRange;}
    public LocalDateTime getCreatedAt() {return createdAt;}
}

