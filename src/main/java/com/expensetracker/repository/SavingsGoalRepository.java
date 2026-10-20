package com.expensetracker.repository;

import com.expensetracker.model.SavingsGoal;

public interface SavingsGoalRepository {

    void saveGoal(SavingsGoal goal);

    SavingsGoal getGoal();
}