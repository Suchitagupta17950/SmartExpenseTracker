package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import com.expensetracker.model.ExpenseType;

import java.time.LocalDate;
import java.util.List;

public class TestExpenseRepository {

    public static void main(String[] args) {

        ExpenseRepository repository = new ExpenseFileRepository();

        // ADD
        Expense expense = new Expense(
                1,
                500.0,
                ExpenseCategory.FOOD,
                ExpenseType.WANT,
                LocalDate.now(),
                "Dinner with friends"
        );

        repository.addExpense(expense);

        System.out.println("Expense added successfully!");

        // READ
        List<Expense> expenses = repository.getAllExpenses();

        System.out.println("\nAll Expenses:");

        for (Expense e : expenses) {
            System.out.println(
                    e.getId() + " | ₹" +
                    e.getAmount() + " | " +
                    e.getCategory() + " | " +
                    e.getType() + " | " +
                    e.getDate() + " | " +
                    e.getDescription()
            );
        }

        // FIND BY ID
        Expense foundExpense = repository.findById(1);

        System.out.println("\nFind By ID:");

        if (foundExpense != null) {
            System.out.println(
                    "Found: " + foundExpense.getDescription()
            );
        } else {
            System.out.println("Expense not found.");
        }

        // UPDATE
        expense.setAmount(750.0);
        expense.setDescription("Dinner with friends and dessert");

        repository.updateExpense(expense);

        System.out.println("\nExpense updated successfully!");

        // DELETE
        repository.deleteExpense(1);

        System.out.println("Expense deleted successfully!");
    }
}