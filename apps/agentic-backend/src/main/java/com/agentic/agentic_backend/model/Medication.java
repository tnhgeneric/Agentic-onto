package com.agentic.agentic_backend.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Medication {
    @Id
    private String medicationId;
    private String drugName;
    private String dosage;
    private String frequency;
    private String prescribedDate;
    private String adherence;

    @Relationship(type = "FOR_DIAGNOSIS")
    private Diagnosis diagnosis;

    // getters and setters
}
