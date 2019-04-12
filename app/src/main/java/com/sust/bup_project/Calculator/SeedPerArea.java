package com.sust.bup_project.Calculator;

import java.util.ArrayList;

public enum SeedPerArea {
    POTATO(728744.939271/FieldMeasurements.ACRE.getValue()),
    TOMATO(28.3495/FieldMeasurements.ACRE.getValue()),
    RICE(13607.8/FieldMeasurements.ACRE.getValue()),
    CORN(68038/FieldMeasurements.ACRE.getValue());

    public double getSeedsInGrams() {
        return seedsInGrams;
    }

    SeedPerArea(double seedsInGrams) {
        this.seedsInGrams = seedsInGrams;
    }

    private final double seedsInGrams;
}
