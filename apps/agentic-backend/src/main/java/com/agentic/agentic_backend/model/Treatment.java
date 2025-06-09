package com.agentic.agentic_backend.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.List;

@Node
public class Treatment {
    @Id
    private String treatmentId;
    private String name;
    private String startDate;
    private String endDate;
    private String status;

    @Relationship(type = "INVOLVES_MEDICATION")
    private List<Medication> medications;

    // getters and setters
}
