package org.example.fevermonitorproject.repository;

import org.example.fevermonitorproject.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByUserId(Long userId);

    @Query("SELECT f FROM Patient f WHERE f.userId = :userId AND f.closedAt IS NULL")
    List<Patient> findAllOpenByUserId(Long userId);
}