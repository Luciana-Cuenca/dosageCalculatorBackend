package com.medical.dosage.pediatric_dosage_calculator.dto;

public class DoseRequest {
    private String medicineName;
    private double weightkg;
    private int ageMonths;
    private int ageYears;

    //Getters y swetter
    public String getMedicineName() {return medicineName;}
    public void setMedicineName(String medicineName) {this.medicineName = medicineName;}

    public double getWeightKg() {return weightkg;}
    public void setWeightKg(double weightKg) {this.weightkg = weightKg;}

    public int getAgeMonths() { return ageMonths; }
    public void setAgeMonths(int ageMonths) { this.ageMonths = ageMonths;}

     public int getAgeYears() { return ageYears; }
    public void setAgeYears(int ageYears) { this.ageYears = ageYears;}
}
