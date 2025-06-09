# Agentic Backend (Spring Boot)

This folder contains the Spring Boot microservice(s) for the Agentic AI project, focused on healthcare domain ontologies and graph reasoning using Neo4j Aura.

## Getting Started
1. Scaffold a new Spring Boot project (using Spring Initializr or your preferred method) inside this folder.
2. Add the following dependencies to your `pom.xml` or `build.gradle`:
   - `spring-boot-starter-data-neo4j`
   - `spring-boot-starter-web`
3. Configure Neo4j Aura connection in `application.properties`:
   ```properties
   spring.neo4j.uri=bolt+s://<your-neo4j-uri>
   spring.neo4j.authentication.username=<username>
   spring.neo4j.authentication.password=<password>
   ```
4. Implement ontology models and services for healthcare knowledge graphs, including:
   - Patient, Doctor, Hospital (core entities)
   - Appointment, Diagnosis, Treatment, Medication, Test, Alert (event and process entities)
   - Relationships and temporal data to track patient journeys and healthcare events.

## Sample Neo4j Configuration (application.properties)
```
spring.neo4j.uri=bolt+s://<your-neo4j-uri>
spring.neo4j.authentication.username=<username>
spring.neo4j.authentication.password=<password>
```

## Sample Entity Classes

### Patient.java
```java
package com.agentic.agentic_backend.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Patient {
    @Id
    private String id;
    private String name;
    private String gender;
    private int age;
    // Add more fields as needed
}
```

### Doctor.java
```java
package com.agentic.agentic_backend.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Doctor {
    @Id
    private String id;
    private String name;
    private String specialty;
    // Add more fields as needed
}
```

### Hospital.java
```java
package com.agentic.agentic_backend.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Hospital {
    @Id
    private String id;
    private String name;
    private String location;
    // Add more fields as needed
}
```

## Example Use Case: Healthcare Information Retrieval & Referral Agent

This backend will power an Agentic AI that helps patients and doctors find the right healthcare resources using a knowledge graph. The core entities are Patient, Doctor, and Hospital.

### Agent Goals
- Provide accurate, personalized information and referral suggestions based on patient queries and healthcare data.

### Agent Capabilities
- Understand patient queries about medical conditions, doctor specialties, hospital services, and preferences.
- Retrieve relevant doctors/hospitals by specialty, location, availability, or insurance.
- Access (simulated) patient records: demographics, history, medications.
- Provide doctor/hospital details: contact, qualifications, working hours, insurance.
- Suggest referrals and explain recommendations.

### Example Interactions
- "I need a cardiologist near Colombo."
- "Which hospitals accept Ceylinco Insurance?"
- "What are Dr. Perera's working hours at Lanka Hospital?"
- "Show me the medical history of John Doe."
- "Who is the primary care physician for Mary Jones?"

## Patient Journey Tracking & Alerts Use Case

This backend powers an Agentic AI that tracks a patient's journey through diagnosis, treatment, and recovery, identifying potential issues and triggering alerts or recommendations.

### Ontology Highlights
- **Entities:** Patient, Doctor, Hospital, Appointment, Diagnosis, Treatment, Medication, Test, Alert
- **Relationships:**
  - Patient hasAppointment Appointment
  - Appointment with Doctor
  - Appointment at Hospital
  - Appointment hasDiagnosis Diagnosis
  - Diagnosis leadsTo Treatment
  - Treatment involves Medication
  - Appointment hasTest Test
  - Alert for Patient/Doctor
- **Temporal Data:** Timestamps for events, status (scheduled, completed, missed), and event sequences

### Agent Goals
- Track appointments, treatments, and medication adherence
- Identify missing follow-ups or overdue tests
- Trigger alerts to doctors or nurses for critical events (e.g., missed medication, abnormal test results)
- Provide patients with reminders and next steps

### Example Interactions
- "Show me the upcoming appointments for John Doe."
- "Alert if a patient misses a medication dose."
- "What treatments has Mary Jones received for her diagnosis?"
- "List overdue tests for all patients."
- "Send a reminder to Dr. Perera for a follow-up appointment."

## Example: Patient Journey Sub-Graph

Below is an example of how a patient journey can be represented as a sub-graph in Neo4j:

```
(Patient:Patient {name: 'John Doe'})
  -[:HAS_DIAGNOSIS {diagnosedDate: '2024-01-15'}]-> (Diagnosis:Diagnosis {name: 'Hypertension'})
  -[:CARED_FOR_BY]-> (Doctor:Doctor {name: 'Dr. Jane Smith', specialty: 'General Physician'})
  -[:HAS_APPOINTMENT {date: '2024-01-20', type: 'Consultation', status: 'Completed'}]-> (Appointment:Appointment)
    -[:WITH_DOCTOR]-> (Doctor)
    -[:AT_HOSPITAL]-> (Hospital:Hospital {name: 'City General Hospital'})

(Doctor)-[:PRESCRIBED {date: '2024-01-20'}]-> (Medication:Medication {drugName: 'Lisinopril', dosage: '10mg', frequency: 'Once daily'})
(Patient)-[:TAKES_MEDICATION {prescribedDate: '2024-01-20', adherence: 'Compliant'}]-> (Medication)
  -[:FOR_DIAGNOSIS]-> (Diagnosis)

(Patient)-[:HAS_APPOINTMENT {date: '2024-02-20', type: 'Follow-up', status: 'Scheduled'}]-> (Appointment:Appointment)
  -[:WITH_DOCTOR]-> (Doctor)
  -[:AT_HOSPITAL]-> (Hospital)
  -[:FOLLOWS_UP]-> (Treatment:Treatment {name: 'Hypertension Management'})
```

This sub-graph demonstrates:
- How a patient's diagnosis, appointments, treatments, and medication are connected.
- How relationships can have properties (e.g., diagnosedDate, adherence, appointment status).
- How the journey is tracked over time and across different healthcare events.

## Security Configuration

The backend uses Spring Security to protect its endpoints. By default, all endpoints require authentication except for the Neo4j health check endpoint, which is publicly accessible for monitoring and testing purposes.

**Key points:**
- The `/api/neo4j/health` endpoint is open to everyone (no authentication required).
- All other endpoints require HTTP Basic authentication.
- CSRF protection is disabled for simplicity in API use cases.
- The security configuration is defined in `SecurityConfig.java`:

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/neo4j/health").permitAll()
                .anyRequest().authenticated())
            .csrf().disable()
            .httpBasic();
        return http.build();
    }
}
```

**How it works:**  
When the application starts (by default on port 8080), you can access the health check endpoint at `http://localhost:8080/api/neo4j/health` without authentication. Any other API requests will prompt for a username and password, using Spring Boot's default security settings unless you configure custom users.

## Neo4j Connection Testing

To verify that your Spring Boot backend can connect to Neo4j Aura, a health check endpoint is provided:

- The `/api/neo4j/health` endpoint runs a simple Cypher query (`RETURN 1 AS result`) against your Neo4j instance.
- If the connection is successful, it returns `Neo4j connection OK.`
- If the connection fails, it returns an error message.

**How to use:**
1. Start your backend application (by default on port 8080).
2. Visit [http://localhost:8080/api/neo4j/health](http://localhost:8080/api/neo4j/health) in your browser or Postman.
3. You should see `Neo4j connection OK.` if the connection is working.

This endpoint is publicly accessible for monitoring and testing, as configured in the security settings.

---

You can now expand your ontology and implement the logic for patient journey tracking and alerts in your Spring Boot microservice.
