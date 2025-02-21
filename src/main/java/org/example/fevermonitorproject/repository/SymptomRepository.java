package org.example.fevermonitorproject.repository;

import org.example.fevermonitorproject.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByPatientIdOrderByTimestampDesc(Long patientId);

    @Modifying
    @Transactional
    @Query("UPDATE Symptom tr SET tr.status = 'CLOSED', tr.closeDate = :closeDate WHERE tr.id = :id")
    void markAsClosed(@Param("id") Long id, @Param("closeDate") LocalDateTime closeDate);

    @Query("SELECT f FROM Symptom f WHERE f.patientId = :id AND f.closeDate IS NULL")
    List<Symptom> findOpenById(@Param("id") Long id);
}