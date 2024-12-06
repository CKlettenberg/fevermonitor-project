package org.example.fevermonitorproject.controller;

import jakarta.validation.Valid;
import org.example.fevermonitorproject.model.Patient;
import org.example.fevermonitorproject.model.User;
import org.example.fevermonitorproject.service.PatientService;
import org.example.fevermonitorproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {
    private final PatientService service;
    private final UserService userService;

    public PatientController(PatientService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        User currentUser = userService.getUserById((patient.getUserId())); // Replace with your logic
        if (currentUser != null) {
            return ResponseEntity.ok(service.addPatient(patient));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return service.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient updatedPatient) {
        try {
            Patient patient = service.updatePatient(id, updatedPatient);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePatient(@PathVariable Long id) {
        try {
            service.removePatient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all-patients/by-user-id/{id}")
    public ResponseEntity<List<Patient>> getUserPatients(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPatientsByUser(id));
    }

}
