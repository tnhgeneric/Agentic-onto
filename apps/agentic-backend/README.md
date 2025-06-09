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

## Example: Appointment Ontology Entity

The `Appointment` class is a core ontology entity in this project, modeled as a Neo4j node using Spring Data Neo4j. This class demonstrates how both nodes and edges (relationships) are represented in the code and persisted in the graph database.

```java
@Node
public class Appointment {
    @Id
    private String appointmentId;
    private String date;
    private String type;
    private String status;

    @Relationship(type = "WITH_DOCTOR")
    private Doctor doctor;

    @Relationship(type = "AT_HOSPITAL")
    private Hospital hospital;

    @Relationship(type = "HAS_DIAGNOSIS")
    private List<Diagnosis> diagnoses;

    @Relationship(type = "HAS_TEST")
    private List<Test> tests;

    // getters and setters
}
```

**Explanation:**  
- The `@Node` annotation makes `Appointment` a node in the Neo4j graph.  
- Fields like `date`, `type`, and `status` store appointment details.  
- The `@Relationship` annotations define edges from the `Appointment` node to other nodes (`Doctor`, `Hospital`, `Diagnosis`, `Test`).  
- When an `Appointment` object is created and saved with related entities, Spring Data Neo4j will create both the node and the corresponding relationships (edges) in the database.  
- This approach allows you to model complex healthcare journeys, connecting appointments to doctors, hospitals, diagnoses, and tests, enabling advanced queries and reasoning in your knowledge graph.

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

# Ontology Entities and Microservices Architecture Documentation

## Ontology Entities

In this project, the following classes are considered **ontology entities**:
- Patient
- Doctor
- Hospital
- Appointment
- Diagnosis
- Treatment
- Medication
- Test

These entities represent real-world healthcare concepts and are modeled as nodes in the Neo4j graph database. Each entity is annotated with `@Node` and may have relationships to other entities, reflecting the semantic structure of the healthcare domain. This approach enables advanced queries and reasoning about patient journeys, diagnoses, treatments, and more.

### Example: Patient Entity
```java
@Node
public class Patient {
    @Id
    private String patientId;
    private String name;
    private String dob;
    private String gender;
    // ...other fields...
    @Relationship(type = "HAS_APPOINTMENT")
    private List<Appointment> appointments;
    @Relationship(type = "HAS_DIAGNOSIS")
    private List<Diagnosis> diagnoses;
    // ...
}
```

## Microservices Architecture and Design Patterns

- The backend is structured as a Spring Boot microservice, following microservices architecture principles.
- Each major domain concept is modeled as a separate entity, supporting domain-driven design.
- Repository interfaces are created for each entity, following the repository pattern for clean separation of data access logic.
- Security is managed centrally using Spring Security, with public and protected endpoints.
- The project is organized using NX monorepo best practices, with all backend code under `apps/agentic-backend/` and shared code in `libs/` (if needed).

## Important Note: How Data is Created in Neo4j vs Relational Databases

In traditional Spring Boot applications with relational databases (using JPA):
- Defining an `@Entity` class and a repository sets up the table structure.
- If `spring.jpa.hibernate.ddl-auto` is set to `update` or `create`, tables are auto-created.
- **However, rows (data) are only created when you save entities using the repository.**

In Spring Data Neo4j (for graph databases):
- Defining a `@Node` class and a repository sets up the structure for nodes and relationships.
- **Neo4j does NOT auto-create nodes or relationships just because the class exists.**
- **Nodes and relationships are only created when you save objects using the repository (e.g., `appointmentRepository.save(...)`).**
- Neo4j is schema-optional: it does not require or create a schema for nodes/relationships up front, and it does not create any data until you explicitly save it.

### Why is this?
- Neo4j is about modeling and querying relationships, not just storing isolated records.
- You must create and save objects that reference each other (e.g., an `Appointment` with a `Doctor`, `Patient`, etc.) to create both nodes and their relationships in the graph.
- Neo4j will only show nodes and relationships that have actually been persisted via your repositories.

**Summary:**
- In both JPA and Neo4j, the entity/repo setup only defines the structure.
- **Actual data (rows in SQL, nodes in Neo4j) is only created when you save objects using the repository.**
- Neo4j does not create "empty" nodes for you; you must save data to see nodes in the database.

**To see nodes and relationships in Neo4j:**
- You need to save objects with their relationships set (e.g., `appointment.setDoctor(doctor)`), then call `appointmentRepository.save(appointment)`.

## Neo4j Deprecation Warning: `id()` vs `elementId()`

When running the backend and initializing data, you may see a warning like this in the logs:

```
The query used a deprecated function. ('id' has been replaced by 'elementId or an application-generated id')
Neo.ClientNotification.Statement.FeatureDeprecationWarning: This feature is deprecated and will be removed in future versions.
... WHERE id(endNode) = relationship.toId ...
```

**What does this mean?**
- This warning is generated by Neo4j and Spring Data Neo4j because the framework's generated Cypher queries use the deprecated `id()` function to reference node IDs.
- Neo4j now recommends using `elementId()` or your own application-generated IDs for referencing nodes.
- Your data is still being created and relationships are working as expected.

**What should you do?**
- You do not need to change your code right now. This is a framework-level issue, not a problem with your entity or repository code.
- Always use your own unique IDs (like `patientId`, `doctorId`, etc.) for lookups and relationships, not the internal Neo4j `id()`.

## Data Initializer: Sample Data Creation on Startup

This project includes a `DataInitializer` class that automatically creates and saves sample nodes and relationships in Neo4j when the backend application starts. This is useful for onboarding, testing, and demonstration purposes.

**How it works:**
- The `DataInitializer` implements `CommandLineRunner` and is annotated with `@Component`, so it runs at application startup.
- It creates and saves:
  - A `Doctor` node
  - A `Hospital` node
  - A `Diagnosis` node
  - An `Appointment` node (linked to the doctor, hospital, and diagnosis)
  - A `Patient` node (linked to the appointment and diagnosis)
  - A `Treatment` node (linked to diagnosis and/or appointment)
  - A `Medication` node (linked to treatment and/or patient)
  - A `Test` node (linked to appointment and/or patient)
- All relationships (edges) are set in Java before saving, so Neo4j will persist both the nodes and their connections.
- After initialization, a log message is printed: `Sample data initialized: Patient, Doctor, Hospital, Diagnosis, Appointment, Treatment, Medication, Test nodes and relationships created in Neo4j.`

**How to use:**
1. Start the backend application with `./mvnw spring-boot:run` (or `mvnw.cmd spring-boot:run` on Windows).
2. On startup, the sample data will be created automatically if it does not already exist.
3. You can view the created nodes and relationships in your Neo4j instance.

**Example (simplified):**
```java
@Component
public class DataInitializer implements CommandLineRunner {
    @Override
    public void run(String... args) {
        // ... create Doctor, Hospital, Diagnosis ...
        // ... create Appointment and link to Doctor, Hospital, Diagnosis ...
        // ... create Patient and link to Appointment, Diagnosis ...
        // ... create Treatment, Medication, Test and link appropriately ...
        // ... save all using repositories ...
        System.out.println("Sample data initialized: Patient, Doctor, Hospital, Diagnosis, Appointment, Treatment, Medication, Test nodes and relationships created in Neo4j.");
    }
}
```

This ensures that your Neo4j database is populated with a realistic mini-graph for immediate exploration and development, including all 8 ontology entities: Patient, Doctor, Hospital, Appointment, Diagnosis, Treatment, Medication, and Test.

## Neo4j Aura User Roles & Permissions

**Important:** To allow the backend to create and view nodes/relationships in Neo4j Aura, your database user must have at least the `editor` or `admin` role. The default `PUBLIC` or `reader` roles are read-only and will result in errors such as:

```
Failed to check Neo4j version. Application supports Neo4j versions >= 4.4.0. Connecting to an unsupported version may lead to incompatibilities, reduced functionality, unexpected bugs, and other issues. Error: Executing procedure is not allowed for user ... with roles [PUBLIC] overridden by READ.
```

### How to Fix
1. Log in to the [Neo4j Aura Console](https://console.neo4j.io/).
2. Go to your database > Users tab.
3. Ensure your user has the `editor` or `admin` role.
4. Update your `application.yml` with the correct username and password.
5. Restart the backend application.

**Security Note:** For development, `editor` is sufficient. Never use the `admin` user in production code.

### Troubleshooting
- If you cannot see nodes in Neo4j or get permission errors, check your user role and credentials.
- After updating credentials, you should be able to see all nodes and relationships created by the backend.

---
