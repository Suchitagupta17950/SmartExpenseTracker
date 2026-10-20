package com.expensetracker.service;

import com.expensetracker.model.*;
import com.expensetracker.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestExpenseService {

    public static void main(String[] args) {

        // Monthly budget = ₹20,000
        Budget budget = new Budget(20000);

        BudgetService budgetService =
                new BudgetService(budget);

        // In-memory repository for testing
        ExpenseRepository repository =
                new TestExpenseRepository();

        ExpenseService expenseService =
                new ExpenseService(repository, budgetService);

        // Existing expense = ₹18,000
        expenseService.addExpense(new Expense(
                1,
                10000,
                ExpenseCategory.FOOD,
                ExpenseType.NEED,
                LocalDate.now(),
                "Groceries"
        ));

        expenseService.addExpense(new Expense(
                2,
                5000,
                ExpenseCategory.BILLS,
                ExpenseType.NEED,
                LocalDate.now(),
                "Electricity"
        ));

        expenseService.addExpense(new Expense(
                3,
                3000,
                ExpenseCategory.TRAVEL,
                ExpenseType.NEED,
                LocalDate.now(),
                "Travel"
        ));

        System.out.println("\nCurrent Spending: ₹" +
                budgetService.calculateTotalSpent(
                        expenseService.getAllExpenses()
                ));

        // Try ₹3,000 more
        System.out.println("\nTrying to add ₹3,000...");

        boolean result = expenseService.addExpense(
                new Expense(
                        4,
                        3000,
                        ExpenseCategory.SHOPPING,
                        ExpenseType.WANT,
                        LocalDate.now(),
                        "Shopping"
                )
        );

        System.out.println("Added: " + result);

        System.out.println("\nFinal Spending: ₹" +
                budgetService.calculateTotalSpent(
                        expenseService.getAllExpenses()
                ));
    }
}