package com.sust.bup_project.Calculator;

import java.util.ArrayList;

public enum SeedPerArea {
    POTATO(0.02),
    TOMATO(0.05),
    RICE(0.023),
    CORN(0.01);

    public double getSeedsInGrams() {
        return seedsInGrams;
    }

    SeedPerArea(double seedsInGrams) {
        this.seedsInGrams = seedsInGrams;
    }

    private final double seedsInGrams;
}
