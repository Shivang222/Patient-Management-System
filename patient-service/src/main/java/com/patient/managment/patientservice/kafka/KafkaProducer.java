package com.patient.managment.patientservice.kafka;

import com.patient.managment.patientservice.entity.Patient;
import com.patient.managment.patientservice.grpc.BillingServiceGrpcClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent patientEvent = PatientEvent.newBuilder().setPatientId(patient.getId().toString()).setName(patient.getName()).setEmail(patient.getEmail()).setEventType("PATIENT_CREATED").build();

        try {
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", patientEvent);
        }
    }
}
