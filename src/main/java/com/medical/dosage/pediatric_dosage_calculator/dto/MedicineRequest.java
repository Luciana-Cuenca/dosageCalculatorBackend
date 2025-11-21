package com.medical.dosage.pediatric_dosage_calculator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicineRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull @Min(1)
    private Double mgKgDay;

    @NotNull @Min(1)
    private Integer dosesPerDay;

    @NotNull @Min(1)
    private Double concentrationMg;

    @NotNull @Min(1)
    private Double concentrationMl;

    @NotNull @Min(0)
    private Double minSafeMl;

    @NotNull @Min(0)
    private Double maxSafeMl;

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Double getMgKgDay() {return mgKgDay;}
    public void setMgKgDay(Double mgKgDay) {this.mgKgDay = mgKgDay;}

    public Integer getDosesPerDay() {return dosesPerDay;}
    public void setDosesPerDay(Integer dosesPerDay) {this.dosesPerDay = dosesPerDay;}

    public Double getConcentrationMg() {return concentrationMg;}
    public void setConcentrationMg(Double concentrationMg) {this.concentrationMg = concentrationMg;}

    public Double getConcentrationMl() {return concentrationMl;}
    public void setConcentrationMl(Double concentrationMl) {this.concentrationMl = concentrationMl;}

    public Double getMinSafeMl() {return minSafeMl;}
    public void setMinSafeMl(Double minSafeMl) {this.minSafeMl = minSafeMl;}

    public Double getMaxSafeMl() {return maxSafeMl;}
    public void setMaxSafeMl(Double maxSafeMl) {this.maxSafeMl = maxSafeMl;}
}

