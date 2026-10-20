package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;

public class TestExpenseRepository implements ExpenseRepository {

    private final List<Expense> expenses = new ArrayList<>();

    @Override
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    @Override
    public Expense findById(int id) {

        for (Expense expense : expenses) {

            if (expense.getId() == id) {
                return expense;
            }
        }

        return null;
    }

    @Override
    public void updateExpense(Expense expense) {

        for (int i = 0; i < expenses.size(); i++) {

            if (expenses.get(i).getId() == expense.getId()) {
                expenses.set(i, expense);
                return;
            }
        }
    }

    @Override
    public void deleteExpense(int id) {

        expenses.removeIf(expense ->
                expense.getId() == id);
    }
}