package com.smorzhok.common;

/**
 * Default agent model parameters
 *
 * @author Dmitry Smorzhok
 */
public final class DefaultModelParams implements ModelParams {

    private static final int touristAmount = 10;

    private static final int operatorAmount = 1;

    private static final double averageSalary = 35000.00;

    @Override
    public int getTouristAmount() {
        return touristAmount;
    }

    @Override
    public int getOperatorAmount() {
        return operatorAmount;
    }

    @Override
    public double getAverageSalary() {
        return averageSalary;
    }
}
