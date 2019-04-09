package com.sust.bup_project.Calculator;

public enum FieldMeasurements {
    BIGHA(2529.3),
    ACRE(4046.86),
    KATHA(67),
    HECTARE(10000),
    SHOTOK(40.47);

    private final double value;

    FieldMeasurements(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
