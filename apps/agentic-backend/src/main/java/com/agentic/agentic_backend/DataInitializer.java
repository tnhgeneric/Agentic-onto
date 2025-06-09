package com.agentic.agentic_backend;

import com.agentic.agentic_backend.model.*;
import com.agentic.agentic_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Doctor
        Doctor doctor = new Doctor();
        doctor.setDoctorId("doc1");
        doctor.setName("Dr. Jane Smith");
        doctor.setSpecialty("General Physician");
        doctor.setContactNumber("1234567890");
        doctor.setQualifications("MBBS, MD");
        doctor.setEmail("jane.smith@example.com");
        doctorRepository.save(doctor);

        // Create Hospital
        Hospital hospital = new Hospital();
        hospital.setHospitalId("hosp1");
        hospital.setName("City General Hospital");
        hospital.setLocation("Colombo");
        hospital.setContactNumber("011-1234567");
        hospital.setType("General");
        hospitalRepository.save(hospital);

        // Create Diagnosis
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId("diag1");
        diagnosis.setName("Hypertension");
        diagnosis.setDescription("High blood pressure");
        diagnosis.setDiagnosedDate("2024-01-15");
        diagnosisRepository.save(diagnosis);

        // Create Appointment
        Appointment appointment = new Appointment();
        appointment.setAppointmentId("appt1");
        appointment.setDate("2024-01-20");
        appointment.setType("Consultation");
        appointment.setStatus("Completed");
        appointment.setDoctor(doctor);
        appointment.setHospital(hospital);
        appointment.setDiagnoses(Collections.singletonList(diagnosis));
        appointmentRepository.save(appointment);

        // Create Patient
        Patient patient = new Patient();
        patient.setPatientId("pat1");
        patient.setName("John Doe");
        patient.setDob("1980-05-10");
        patient.setGender("Male");
        patient.setContactNumber("077-1234567");
        patient.setAddress("123 Main St, Colombo");
        patient.setBloodGroup("A+");
        patient.setInsuranceProvider("Ceylinco");
        patient.setCurrentStatus("Active");
        patient.setAppointments(Collections.singletonList(appointment));
        patient.setDiagnoses(Collections.singletonList(diagnosis));
        patientRepository.save(patient);
    }
}
