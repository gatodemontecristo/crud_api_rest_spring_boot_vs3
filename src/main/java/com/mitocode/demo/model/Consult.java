package com.mitocode.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idConsult;

    @ManyToOne //FK
    @JoinColumn(name="id_patient",nullable = false, foreignKey = @ForeignKey(name="FK_CONSULT_PATIENT"))
    private Patient patient;

    @ManyToOne //FK
    @JoinColumn(name="id_medic",nullable = false, foreignKey = @ForeignKey(name="FK_CONSULT_MEDIC"))
    private Medic medico;

    @ManyToOne //FK
    @JoinColumn(name="id_specialty",nullable = false, foreignKey = @ForeignKey(name="FK_CONSULT_SPECIALTY"))
    private Specialty specialty;

    @Column(length = 3,nullable = false)
    private String numConsult;

    @Column(nullable = false) //UTC, ISODate yyyy-mm-ddTHH:mm:ss
    private LocalDateTime consultDate;

    //JPQL Java Persistence Query Language
}
