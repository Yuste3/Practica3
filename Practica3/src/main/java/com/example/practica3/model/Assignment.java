package com.example.practica3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentID;

    @ManyToOne
//    @Column(name = "employee", nullable = false)
    private Employee employee;

    @ManyToOne
//    @Column(name = "practice", nullable = false)
    private Practice practice;

    @ManyToOne
//    @Column(name = "proyect", nullable = false)
    private Project project;

    @Column(name = "RDG Remakrs", nullable = false)
    private String RDGRemakrs;

    @Column(name = "allocation %", nullable = false)
    private String allocation;

    @ManyToOne
//    @Column(name = "practice", nullable = false)
    private Employee supervisor;

    @Column(name = "allocation start date", nullable = false)
    private String allocationStartDate;

    @Column(name = "allocation end date", nullable = false)
    private String allocationEndDate;

    @Column(name = "travel date", nullable = false)
    private String travelDate;

    @Column(name = "remarks", nullable = false)
    private String remarks;

}
