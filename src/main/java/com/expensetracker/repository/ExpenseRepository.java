package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import java.util.List;

public interface ExpenseRepository {

    void addExpense(Expense expense);

    List<Expense> getAllExpenses();

    Expense findById(int id);

    void updateExpense(Expense expense);

    void deleteExpense(int id);
}