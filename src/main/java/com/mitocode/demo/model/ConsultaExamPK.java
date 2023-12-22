package com.mitocode.demo.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode

public class ConsultaExamPK implements Serializable {
    @ManyToOne
    @JoinColumn(name="id_consult")
    private Consult consult;

    @ManyToOne
    @JoinColumn(name="id_exam")
    private Exam examen;
}
