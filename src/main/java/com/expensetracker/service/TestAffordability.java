package com.expensetracker.service;

import com.expensetracker.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestAffordability {

    public static void main(String[] args) {

        Budget budget = new Budget(20000);

        BudgetService budgetService =
                new BudgetService(budget);

        List<Expense> expenses = new ArrayList<>();

        // Already spent ₹15,000
        expenses.add(new Expense(
                1,
                10000,
                ExpenseCategory.BILLS,
                ExpenseType.NEED,
                LocalDate.now(),
                "Monthly bills"
        ));

        expenses.add(new Expense(
                2,
                5000,
                ExpenseCategory.FOOD,
                ExpenseType.NEED,
                LocalDate.now(),
                "Groceries"
        ));

        System.out.println("Monthly Budget: ₹" +
                budget.getMonthlyLimit());

        System.out.println("Current Spending: ₹" +
                budgetService.calculateTotalSpent(expenses));

        System.out.println("Remaining Budget: ₹" +
                budgetService.getRemainingBudget(expenses));

        // Check ₹3,000
        double amount1 = 3000;

        System.out.println("\nCan I afford ₹" +
                amount1 + "? " +
                budgetService.canAfford(amount1, expenses));

        // Check ₹6,000
        double amount2 = 6000;

        System.out.println("Can I afford ₹" +
                amount2 + "? " +
                budgetService.canAfford(amount2, expenses));
    }
}