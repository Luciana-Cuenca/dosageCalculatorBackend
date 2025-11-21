package com.medical.dosage.pediatric_dosage_calculator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dose_calculations")
public class DoseCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String medicineName;

    @Column(nullable = false)
    private double weightKg;

    @Column(nullable = false)
    private double mgPerDay;

    @Column(nullable = false)
    private int dosesPerDay;

    @Column(nullable = false)
    private double mgPerDose;

    @Column(nullable = false)
    private double mlPerDose;

    @Column(nullable = false)
    private String alert;

    @Column(nullable = false)
    private String safeRange;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {return id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public String getMedicineName() {return medicineName;}
    public void setMedicineName(String medicineName) {this.medicineName = medicineName;}

    public double getWeightKg() {return weightKg;}
    public void setWeightKg(double weightKg) {this.weightKg = weightKg;}

    public double getMgPerDay() {return mgPerDay;}
    public void setMgPerDay(double mgPerDay) {this.mgPerDay = mgPerDay;}
    
    public int getDosesPerDay() {return dosesPerDay;}
    public void setDosesPerDay(int dosesPerDay) {this.dosesPerDay = dosesPerDay;}

    public double getMgPerDose() {return mgPerDose;}
    public void setMgPerDose(double mgPerDose) {this.mgPerDose = mgPerDose;}

    public double getMlPerDose() {return mlPerDose;}
    public void setMlPerDose(double mlPerDose) {this.mlPerDose = mlPerDose;}

    public String getAlert() {return alert;}
    public void setAlert(String alert) {this.alert = alert;}

    public String getSafeRange() {return safeRange;}
    public void setSafeRange(String safeRange) {this.safeRange = safeRange;}

    public LocalDateTime getCreatedAt() {return createdAt;}
}

