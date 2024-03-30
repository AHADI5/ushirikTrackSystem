package com.ushirikeduc.disciplineservice.model;

import jakarta.persistence.ManyToOne;

public class Remarks {
    @ManyToOne
    private Discipline discipline;
}
