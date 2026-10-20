package com.expensetracker.service;

import com.expensetracker.model.SavingsGoal;

public class SavingsGoalService {

    public double calculateRemainingAmount(
            SavingsGoal goal) {

        return Math.max(
                0,
                goal.getTargetAmount()
                        - goal.getSavedAmount()
        );
    }

    public double calculateProgressPercentage(
            SavingsGoal goal) {

        if (goal.getTargetAmount() <= 0) {
            return 0;
        }

        return Math.min(
                100,
                (goal.getSavedAmount()
                        / goal.getTargetAmount()) * 100
        );
    }

    public double calculateMonthlySavingRequired(
            SavingsGoal goal,
            int months) {

        if (months <= 0) {
            return 0;
        }

        double remaining =
                calculateRemainingAmount(goal);

        return remaining / months;
    }

    public boolean isGoalCompleted(
            SavingsGoal goal) {

        return goal.getSavedAmount()
                >= goal.getTargetAmount();
    }
}