package com.medical.dosage.pediatric_dosage_calculator.repository;

import com.medical.dosage.pediatric_dosage_calculator.model.DoseCalculation;
import com.medical.dosage.pediatric_dosage_calculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoseCalculationRepository extends JpaRepository<DoseCalculation, Long> {
    List<DoseCalculation> findByUserOrderByCreatedAtDesc(User user);
}

