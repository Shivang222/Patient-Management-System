package com.patient.managment.patientservice.service.impl;

import com.patient.managment.patientservice.dto.PatientRequestDTO;
import com.patient.managment.patientservice.dto.PatientResponseDTO;
import com.patient.managment.patientservice.entity.Patient;
import com.patient.managment.patientservice.exception.EmailAlreadyExistsException;
import com.patient.managment.patientservice.exception.PatientNotFoundException;
import com.patient.managment.patientservice.grpc.BillingServiceGrpcClient;
import com.patient.managment.patientservice.kafka.KafkaProducer;
import com.patient.managment.patientservice.repository.PatientRepository;
import com.patient.managment.patientservice.service.IPatientService;
import com.patient.managment.patientservice.transformer.PatientTransformer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    private final BillingServiceGrpcClient billingServiceGrpcClient;

    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientTransformer::toDTO).collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email aldready exists " + patientRequestDTO.getEmail());
        }

        Patient patient = patientRepository.save(PatientTransformer.toEntity(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());

        kafkaProducer.sendEvent(patient);

        //An email address must be unique
        return PatientTransformer.toDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with Id: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email aldready exists " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientTransformer.toDTO(updatedPatient);
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
