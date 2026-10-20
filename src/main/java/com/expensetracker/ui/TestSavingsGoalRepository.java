package com.expensetracker.ui;

import com.expensetracker.model.SavingsGoal;
import com.expensetracker.repository.SavingsGoalRepository;
import com.expensetracker.repository.SavingsGoalFileRepository;

public class TestSavingsGoalRepository {

    public static void main(String[] args) {

        SavingsGoalRepository repository =
                new SavingsGoalFileRepository();

        SavingsGoal goal =
                new SavingsGoal(
                        "New Laptop",
                        60000,
                        20000
                );

        repository.saveGoal(goal);

        SavingsGoal savedGoal =
                repository.getGoal();

        if (savedGoal != null) {

            System.out.println(
                    "Goal Name: "
                            + savedGoal.getGoalName()
            );

            System.out.println(
                    "Target Amount: ₹"
                            + savedGoal.getTargetAmount()
            );

            System.out.println(
                    "Saved Amount: ₹"
                            + savedGoal.getSavedAmount()
            );

        } else {

            System.out.println(
                    "No savings goal found."
            );
        }
    }
}