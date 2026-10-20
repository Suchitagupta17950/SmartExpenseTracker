package com.expensetracker.ui;

import com.expensetracker.model.SavingsGoal;
import com.expensetracker.service.SavingsGoalService;

public class TestSavingsGoal {

    public static void main(String[] args) {

        SavingsGoal goal =
                new SavingsGoal(
                        "New Laptop",
                        60000,
                        20000
                );

        SavingsGoalService service =
                new SavingsGoalService();

        double remaining =
                service.calculateRemainingAmount(goal);

        double progress =
                service.calculateProgressPercentage(goal);

        double monthlySaving =
                service.calculateMonthlySavingRequired(
                        goal,
                        4
                );

        boolean completed =
                service.isGoalCompleted(goal);

        System.out.println(
                "Goal: " + goal.getGoalName()
        );

        System.out.println(
                "Target Amount: ₹"
                        + goal.getTargetAmount()
        );

        System.out.println(
                "Saved Amount: ₹"
                        + goal.getSavedAmount()
        );

        System.out.println(
                "Remaining Amount: ₹"
                        + remaining
        );

        System.out.println(
                "Progress: "
                        + String.format(
                                "%.2f",
                                progress
                        )
                        + "%"
        );

        System.out.println(
                "Monthly Saving Required: ₹"
                        + String.format(
                                "%.2f",
                                monthlySaving
                        )
        );

        System.out.println(
                "Goal Completed: "
                        + completed
        );
    }
}