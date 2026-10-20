package com.expensetracker.model;

public class SavingsGoal {

    private String goalName;
    private double targetAmount;
    private double savedAmount;

    public SavingsGoal(String goalName,
                       double targetAmount,
                       double savedAmount) {

        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.savedAmount = savedAmount;
    }

    public String getGoalName() {
        return goalName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }
}