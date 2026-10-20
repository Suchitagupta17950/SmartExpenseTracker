package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;

import java.util.List;

public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;

    public ExpenseService(ExpenseRepository expenseRepository,
                           BudgetService budgetService) {

        this.expenseRepository = expenseRepository;
        this.budgetService = budgetService;
    }

    public boolean addExpense(Expense expense) {
if (expense == null) {
    return false;
}

if (expense.getAmount() <= 0) {
    System.out.println("Expense amount must be greater than zero.");
    return false;
}

        List<Expense> existingExpenses =
                expenseRepository.getAllExpenses();

        
        double remainingBudget =
                budgetService.getRemainingBudget(existingExpenses);

        if (expense.getAmount() > remainingBudget) {

            System.out.println(
                    "Expense cannot be added!"
            );

            System.out.println(
                    "Remaining Budget: ₹" + remainingBudget
            );

            System.out.println(
                    "Requested Expense: ₹" + expense.getAmount()
            );

            return false;
        }

        expenseRepository.addExpense(expense);

        System.out.println("Expense added successfully!");

        return true;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.getAllExpenses();
    }
    public List<Expense> searchExpenses(String keyword) {

    List<Expense> expenses =
            expenseRepository.getAllExpenses();

    List<Expense> results =
            new java.util.ArrayList<>();

    for (Expense expense : expenses) {

        if (expense.getDescription()
                .toLowerCase()
                .contains(keyword.toLowerCase())) {

            results.add(expense);
        }
    }

    return results;
}

    public Expense findExpense(int id) {
        return expenseRepository.findById(id);
    }

    public boolean updateExpense(Expense expense) {

    if (expense == null) {
        return false;
    }

    if (expense.getAmount() <= 0) {
        System.out.println(
                "Expense amount must be greater than zero."
        );
        return false;
    }

    Expense existingExpense =
            expenseRepository.findById(
                    expense.getId()
            );

    if (existingExpense == null) {
        System.out.println(
                "Expense not found."
        );
        return false;
    }

    expenseRepository.updateExpense(expense);

    System.out.println(
            "Expense updated successfully!"
    );

    return true;
}

    public boolean deleteExpense(int id) {

    Expense existingExpense =
            expenseRepository.findById(id);

    if (existingExpense == null) {

        System.out.println(
                "Expense not found."
        );

        return false;
    }

    expenseRepository.deleteExpense(id);

    System.out.println(
            "Expense deleted successfully!"
    );

    return true;
}
}