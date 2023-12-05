package com.example.demo.modell;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String locality;

    private LocalDate birthDate;

    @ManyToOne
    private Doctor doctor;
}
