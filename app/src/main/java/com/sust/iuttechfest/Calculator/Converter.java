package com.sust.iuttechfest.Calculator;

public class Converter {
    private double result = 0;
    public String type;

    public Converter(double result, String type) {
        this.result = result;
        this.type = type;
    }

    double getConversion() {
        switch (type) {
            case "acre" :
                result = FieldMeasurements.ACRE.getValue() * result;
                break;
            case "bigha" :
                result = FieldMeasurements.BIGHA.getValue() * result;
                break;
            case "hectare" :
                result = FieldMeasurements.HECTARE.getValue() * result;
                break;
            case "katha" :
                result = FieldMeasurements.KATHA.getValue() * result;
                break;
            case "shotok" :
                result = FieldMeasurements.SHOTOK.getValue() * result;
                break;
            default:
                result = result;
        }
        return result;
    }
}
