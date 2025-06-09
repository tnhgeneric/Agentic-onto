package com.agentic.agentic_backend.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Test {
    @Id
    private String testId;
    private String name;
    private String result;
    private String date;
    private String status;

    // getters and setters
}
