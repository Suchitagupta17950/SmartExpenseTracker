package com.expensetracker.repository;

import com.expensetracker.model.Budget;

public interface BudgetRepository {

    void saveBudget(Budget budget);

    Budget getBudget();
}