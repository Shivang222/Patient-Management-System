package com.patient.managment.patientservice.service;

import com.patient.managment.patientservice.dto.PatientRequestDTO;
import com.patient.managment.patientservice.dto.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IPatientService {

    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO);

    void deletePatient(UUID id);
}
