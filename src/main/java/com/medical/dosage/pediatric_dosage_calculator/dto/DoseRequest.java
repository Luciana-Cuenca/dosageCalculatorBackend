package com.medical.dosage.pediatric_dosage_calculator.dto;

public class DoseRequest {
    private String medicineName;
    private double weightKg;

    // Concentración opcional ingresada por el usuario
    private Double userConcentrationMg;
    private Double userConcentrationMl;

    // Getters y setters
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public Double getUserConcentrationMg() { return userConcentrationMg; }
    public void setUserConcentrationMg(Double userConcentrationMg) { this.userConcentrationMg = userConcentrationMg; }

    public Double getUserConcentrationMl() { return userConcentrationMl; }
    public void setUserConcentrationMl(Double userConcentrationMl) { this.userConcentrationMl = userConcentrationMl; }
}
