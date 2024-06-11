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
//@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentID;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Employee supervisor;

    @ManyToOne
    private Practice practice;

    @ManyToOne
    private Project oldProjectCode;

    @ManyToOne
    private Project newProjectCode;

    @Column(name = "RDG Remarks")
    private String RDGRemarks;

    @Column(name = "allocation %")
    private String allocation;

    @Column(name = "allocation start date", nullable = false)
    private String allocationStartDate;

    @Column(name = "allocation end date", nullable = false)
    private String allocationEndDate;


}
