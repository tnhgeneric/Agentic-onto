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
    @Autowired
    private TreatmentRepository treatmentRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private AlertRepository alertRepository;

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

        // Create Treatment
        Treatment treatment = new Treatment();
        treatment.setTreatmentId("treat1");
        treatment.setName("Hypertension Management");
        treatment.setStartDate("2024-01-21");
        treatment.setEndDate("2024-06-21");
        treatment.setStatus("Ongoing");
        treatmentRepository.save(treatment);

        // Create Medication
        Medication medication = new Medication();
        medication.setMedicationId("med1");
        medication.setDrugName("Lisinopril");
        medication.setDosage("10mg");
        medication.setFrequency("Once daily");
        medication.setPrescribedDate("2024-01-20");
        medication.setAdherence("Compliant");
        medication.setDiagnosis(diagnosis);
        medicationRepository.save(medication);

        // Create Test
        Test test = new Test();
        test.setTestId("test1");
        test.setName("Blood Pressure Test");
        test.setResult("140/90");
        test.setDate("2024-01-20");
        test.setStatus("Completed");
        testRepository.save(test);

        // Create Alert
        Alert alert = new Alert();
        alert.setAlertId("alert1");
        alert.setMessage("Patient missed medication dose");
        alert.setType("MissedMedication");
        alert.setTimestamp("2024-01-22T09:00:00Z");
        alert.setResolved(false);
        alert.setPatient(patient);
        alert.setDoctor(doctor);
        alertRepository.save(alert);

        // Link Treatment to Medication
        treatment.setMedications(Collections.singletonList(medication));
        treatmentRepository.save(treatment);

        // Link Diagnosis to Treatment
        diagnosis.setTreatments(Collections.singletonList(treatment));
        diagnosisRepository.save(diagnosis);

        // Link Appointment to Test
        appointment.setTests(Collections.singletonList(test));
        appointmentRepository.save(appointment);

        // Log a message to indicate data initialization is complete
        System.out
                .println(
                        "Sample data initialized: All 9 ontology entities (including Alert) and their relationships created in Neo4j.");
    }
}
