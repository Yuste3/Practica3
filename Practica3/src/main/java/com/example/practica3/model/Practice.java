package com.example.practica3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Practice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Practice {
    @Id
    @Column(name = "name", nullable = false)
    private String name;
}
