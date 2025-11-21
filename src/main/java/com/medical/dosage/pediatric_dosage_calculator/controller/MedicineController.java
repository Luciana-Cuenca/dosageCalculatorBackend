package com.medical.dosage.pediatric_dosage_calculator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dosage.pediatric_dosage_calculator.dto.MedicineRequest;
import com.medical.dosage.pediatric_dosage_calculator.model.Medicine;
import com.medical.dosage.pediatric_dosage_calculator.repository.MedicineRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;

    public MedicineController(MedicineRepository medicineRepository){
        this.medicineRepository = medicineRepository;
    }

    @GetMapping
    public List<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable Long id){
        return medicineRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Medicine create(@Valid @RequestBody MedicineRequest request){
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setDescription(request.getDescription());
        medicine.setMgKgDay(request.getMgKgDay());
        medicine.setDosesPerDay(request.getDosesPerDay());
        medicine.setConcentrationMg(request.getConcentrationMg());
        medicine.setConcentrationMl(request.getConcentrationMl());
        medicine.setMinSafeMl(request.getMinSafeMl());
        medicine.setMaxSafeMl(request.getMaxSafeMl());
        return medicineRepository.save(medicine);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Medicine> update(@PathVariable Long id, @Valid @RequestBody MedicineRequest request){
        return medicineRepository.findById(id).map(m -> {
            m.setName(request.getName());
            m.setDescription(request.getDescription());
            m.setMgKgDay(request.getMgKgDay());
            m.setDosesPerDay(request.getDosesPerDay());
            m.setConcentrationMg(request.getConcentrationMg());
            m.setConcentrationMl(request.getConcentrationMl());
            m.setMinSafeMl(request.getMinSafeMl());
            m.setMaxSafeMl(request.getMaxSafeMl());
            return ResponseEntity.ok(medicineRepository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!medicineRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        medicineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
