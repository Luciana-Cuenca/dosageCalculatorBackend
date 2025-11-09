package com.medical.dosage.pediatric_dosage_calculator.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.dosage.pediatric_dosage_calculator.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

    // busca al usuario por su nombre de usuario
    Optional<User>findByUsername(String username);

    //busca al usuario por su email
    Optional<User>findByEmail(String email);

    //mira si ya existe un usuario con cierto correo
    boolean existsByEmail(String email);

}
