package com.medical.dosage.pediatric_dosage_calculator.config;

import com.medical.dosage.pediatric_dosage_calculator.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medical.dosage.pediatric_dosage_calculator.model.Rol;
import com.medical.dosage.pediatric_dosage_calculator.repository.UserRepository;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner createAdmi(UserRepository userRepository, PasswordEncoder passwordEncoder){
         return arg -> {
            if (!userRepository.existsByEmail("admin@admin.com")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("vivalagrasa"));
                admin.setRol(Rol.ADMIN);
                userRepository.save(admin);

                System.out.println("admin create");

                
            }
            else {
                System.out.println("admin already exist");
            }
            
         };
    }

}
