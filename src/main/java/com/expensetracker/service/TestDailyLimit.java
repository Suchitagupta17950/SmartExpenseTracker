package com.expensetracker.service;

import com.expensetracker.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDailyLimit {

    public static void main(String[] args) {

        Budget budget = new Budget(20000);

        BudgetService budgetService =
                new BudgetService(budget);

        List<Expense> expenses = new ArrayList<>();

        // Already spent ₹10,000
        expenses.add(new Expense(
                1,
                6000,
                ExpenseCategory.BILLS,
                ExpenseType.NEED,
                LocalDate.now(),
                "Monthly bills"
        ));

        expenses.add(new Expense(
                2,
                4000,
                ExpenseCategory.FOOD,
                ExpenseType.NEED,
                LocalDate.now(),
                "Groceries"
        ));

        int daysRemaining = 10;

        double dailyLimit =
                budgetService.calculateDailySpendingLimit(
                        expenses,
                        daysRemaining
                );

        System.out.println("Monthly Budget: ₹" +
                budget.getMonthlyLimit());

        System.out.println("Current Spending: ₹" +
                budgetService.calculateTotalSpent(expenses));

        System.out.println("Remaining Budget: ₹" +
                budgetService.getRemainingBudget(expenses));

        System.out.println("Days Remaining: " +
                daysRemaining);

        System.out.println("Recommended Daily Limit: ₹" +
                dailyLimit);
    }
}